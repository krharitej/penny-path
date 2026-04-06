package com.haritejkr.pennypath.data.mapper

import com.haritejkr.pennypath.data.local.entity.TransactionEntity
import com.haritejkr.pennypath.model.Transaction

fun TransactionEntity.toModel(): Transaction {
    return Transaction(
        id = id,
        amount = amount,
        type = type,
        category = category,
        date = date,
        note = note
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        amount = amount,
        type = type,
        category = category,
        date = date,
        note = note
    )
}