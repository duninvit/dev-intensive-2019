package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    var diff = (date.time - this.time)
    val past = diff >= 0
    diff = abs(diff)
    return when (diff) {
        in 0 * SECOND..1 * SECOND -> "только что"
        in 1 * SECOND..45 * SECOND -> if (past) "несколько секунд назад" else "через несколько секунд"
        in 45 * SECOND..75 * SECOND -> if (past) "минуту назад" else "через минуту"
        in 75 * SECOND..45 * MINUTE -> if (past)
            "${diff / MINUTE} ${TimeUnits.MINUTE.pluralize(diff / MINUTE)} назад"
        else "через ${diff / MINUTE} ${TimeUnits.MINUTE.pluralize(diff / MINUTE)}"
        in 45 * MINUTE..75 * MINUTE -> if (past) "час назад" else "через час"
        in 75 * MINUTE..22 * HOUR -> if (past)
            "${diff / HOUR} ${TimeUnits.HOUR.pluralize(diff / HOUR)} назад"
        else "через ${diff / HOUR} ${TimeUnits.HOUR.pluralize(diff / HOUR)}"
        in 22 * HOUR..26 * HOUR -> if (past) "день назад" else "через день"
        in 22 * HOUR..360 * DAY -> if (past)
            "${diff / DAY} ${TimeUnits.DAY.pluralize(diff / DAY)} назад"
        else "через ${diff / DAY} ${TimeUnits.DAY.pluralize(diff / DAY)}"
        else -> if (past) "более года назад" else "более чем через год"
    }
}

enum class TimeUnits {
    SECOND {
        override fun pluralize(value: Long): String {
            return ""
        }
    },
    MINUTE {
        override fun pluralize(value: Long): String {
            return commonPluralize(value.toInt(), "минут", "минуты", "минута")
        }
    },
    HOUR {
        override fun pluralize(value: Long): String {
            return commonPluralize(value.toInt(), "часов", "часа", "час")
        }
    },
    DAY {
        override fun pluralize(value: Long): String {
            return commonPluralize(value.toInt(), "дней", "дня", "день")
        }
    };

    abstract fun pluralize(value: Long): String

    fun commonPluralize(value: Int, many: String, few: String, one: String): String {
        return when (val lastValue = abs(value)) {
            0, in 5..20 -> many
            1 -> one
            in 2..4 -> few
            else -> commonPluralize(lastValue % 10, many, few, one)
        }
    }
}
