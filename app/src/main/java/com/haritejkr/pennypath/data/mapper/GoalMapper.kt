package com.haritejkr.pennypath.data.mapper

import com.haritejkr.pennypath.model.Goal
import com.haritejkr.pennypath.data.local.entity.Goal as GoalEntity

fun Goal.toEntity(): GoalEntity {
    return GoalEntity(
        id = id,
        name = name,
        targetAmount = targetAmount,
        savedAmount = savedAmount
    )
}

fun GoalEntity.toDomain(): Goal {
    return Goal(
        id = id,
        name = name,
        targetAmount = targetAmount,
        savedAmount = savedAmount
    )
}