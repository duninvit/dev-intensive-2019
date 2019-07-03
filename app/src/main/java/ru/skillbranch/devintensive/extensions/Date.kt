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
        in 75 * SECOND..45 * MINUTE -> if (past) "${TimeUnits.MINUTE.plural((diff / MINUTE).toInt())} назад"
        else "через ${TimeUnits.MINUTE.plural((diff / MINUTE).toInt())}"
        in 45 * MINUTE..75 * MINUTE -> if (past) "час назад" else "через час"
        in 75 * MINUTE..22 * HOUR -> if (past) "${TimeUnits.HOUR.plural((diff / HOUR).toInt())} назад"
        else "через ${TimeUnits.HOUR.plural((diff / HOUR).toInt())}"
        in 22 * HOUR..26 * HOUR -> if (past) "день назад" else "через день"
        in 22 * HOUR..360 * DAY -> if (past) "${TimeUnits.DAY.plural((diff / DAY).toInt())} назад"
        else "через ${TimeUnits.DAY.plural((diff / DAY).toInt())}"
        else -> if (past) "более года назад" else "более чем через год"
    }
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String {
            return "$value ${commonPluralize(value, "секунд", "секунды", "секунду")}"
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            return "$value ${commonPluralize(value, "минут", "минуты", "минуту")}"
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return "$value ${commonPluralize(value, "часов", "часа", "час")}"
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return "$value ${commonPluralize(value, "дней", "дня", "день")}"
        }
    };

    abstract fun plural(value: Int): String

    fun commonPluralize(value: Int, many: String, few: String, one: String): String {
        return when (val lastValue = abs(value)) {
            0, in 5..20 -> many
            1 -> one
            in 2..4 -> few
            else -> commonPluralize(lastValue % 10, many, few, one)
        }
    }
}
