package pitam.nepalidate.utils

import android.content.Context
import pitam.nepalidate.R

const val ONE_SECOND_MILLIS = 1000L
const val ONE_MINUTE_MILLIS = 60 * ONE_SECOND_MILLIS
const val ONE_HOUR_MILLIS = 60 * ONE_MINUTE_MILLIS
const val ONE_DAY_MILLIS = 24 * ONE_HOUR_MILLIS
const val ONE_MONTH_MILLIS = 30 * ONE_DAY_MILLIS
const val ONE_YEAR_MILLIS = 12 * ONE_MONTH_MILLIS


private fun StringBuilder.appendIfIsBlank(string: String): StringBuilder {
    if (isBlank()) {
        append(string)
    }
    return this
}

private fun StringBuilder.appendAgo(ctx: Context): String {
    if (isNotBlank()) {
        append(ctx.resources.getString(R.string.ago))
    }
    return toString()
}


fun Context.getAgo(timeInMills: Long, shortFormat: Boolean = false): String? {
    var diff = System.currentTimeMillis() - timeInMills
    if (diff <= 0) {
        return null
    } else {

        val years = diff.div(ONE_YEAR_MILLIS).toInt()
        diff -= years * ONE_YEAR_MILLIS

        val months = diff.div(ONE_MONTH_MILLIS).toInt()
        diff -= months * ONE_MONTH_MILLIS

        val days = diff.div(ONE_DAY_MILLIS).toInt()
        diff -= days * ONE_DAY_MILLIS

        val hours = diff.div(ONE_HOUR_MILLIS).toInt()
        diff -= hours * ONE_HOUR_MILLIS

        val minutes = diff.div(ONE_MINUTE_MILLIS).toInt()
        diff -= minutes * ONE_MINUTE_MILLIS

        val seconds = diff.div(ONE_SECOND_MILLIS).toInt()
        diff -= seconds * ONE_SECOND_MILLIS

        if (shortFormat) {

            val dayString = if (days != 0) resources.getString(R.string._d, days) + " " else ""
            val hourString = if (hours != 0) resources.getString(R.string._h, hours) + " " else ""
            val minuteString =
                if (minutes != 0) resources.getString(R.string._m, minutes) + " " else ""
            val secondString =
                if (seconds != 0) resources.getString(R.string._s, seconds) + " " else ""
            return StringBuilder(dayString).appendIfIsBlank(hourString)
                .appendIfIsBlank(minuteString).appendIfIsBlank(secondString).appendIfIsBlank(
                    getString(
                        R.string.now
                    )
                ).toString()
        } else {
            val yearString = if (years != 0)
                resources.getQuantityString(
                    R.plurals._year, years, years
                ) + " "
            else ""

            val monthString = if (months != 0)
                resources.getQuantityString(
                    R.plurals._month, months, months
                ) + " "
            else ""

            val dayString = if (days != 0)
                resources.getQuantityString(
                    R.plurals._day, days, days
                ) + " "
            else ""

            val hourString = if (hours != 0)
                resources.getQuantityString(
                    R.plurals._hour, hours, hours
                ) + " "
            else ""

            val minuteString = if (minutes != 0)
                resources.getQuantityString(
                    R.plurals._minute, minutes, minutes
                ) + " "
            else ""

            val secondString = if (seconds != 0)
                resources.getQuantityString(
                    R.plurals._second, seconds, seconds
                ) + " "
            else ""

            return StringBuilder(yearString)
                .appendIfIsBlank(monthString)
                .appendIfIsBlank(dayString)
                .appendIfIsBlank(hourString)
                .appendIfIsBlank(minuteString)
                .appendIfIsBlank(secondString)
                .appendIfIsBlank(getString(R.string.just_now))
                .appendAgo(this)
        }

    }

}











