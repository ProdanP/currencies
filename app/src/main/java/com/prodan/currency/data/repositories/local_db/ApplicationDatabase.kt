package com.prodan.currency.data.repositories.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.prodan.currency.base.extentions.readJsonFile
import com.prodan.currency.data.models.db.CurrenciesLocal
import com.prodan.currency.data.models.db.CurrencyLocal
import com.prodan.currency.data.repositories.local_db.currencies.CurrenciesDao
import java.util.concurrent.Executors

@Database(entities = [CurrencyLocal::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun currienciesDao() : CurrenciesDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ApplicationDatabase::class.java, "currencies.db"
            ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    insertInitialDataInDb(context)
                }
            }).build()


        fun insertInitialDataInDb(context: Context) {
            Executors.newSingleThreadScheduledExecutor()
                .execute {
                    val countriesJson = Gson().fromJson<CurrenciesLocal>(
                        context.readJsonFile("currencies.json"),
                        CurrenciesLocal::class.java
                    )
                    val defaultReference =  countriesJson.currencies.firstOrNull { it.code == "EUR" }
                    defaultReference?.isReference = true
                    defaultReference?.rate = 1f

                    getInstance(context).currienciesDao()
                        .insertLocalCurriencies(countriesJson.currencies)

                }
        }
    }


}