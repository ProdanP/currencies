package com.prodan.currency.data.models

data class CurrienciesResponse(var baseCurrency: String, var rates: HashMap<String, Float>)