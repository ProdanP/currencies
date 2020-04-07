package com.prodan.currency.base.viewholders

data class ChangePayload<out T>(
    val oldData: T,
    val newData: T
)

fun <T> createCombinedPayload(payloads: List<ChangePayload<T>>): ChangePayload<T> {
    assert(payloads.isNotEmpty())
    val firstChange = payloads.first()
    val lastChange = payloads.last()

    return ChangePayload(firstChange.oldData, lastChange.newData)
}
