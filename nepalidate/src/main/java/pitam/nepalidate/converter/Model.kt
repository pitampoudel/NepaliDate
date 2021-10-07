package pitam.nepalidate.converter

import org.joda.time.DateTime
import org.joda.time.Days
import java.util.*

data class DateAD(var yyAD: Int, var mmAD: Int, var ddAD: Int) {
    constructor(gregorianCalendar: GregorianCalendar = GregorianCalendar()) : this(
        yyAD = gregorianCalendar[GregorianCalendar.YEAR],
        mmAD = gregorianCalendar[GregorianCalendar.MONTH] + 1,
        ddAD = gregorianCalendar[GregorianCalendar.DAY_OF_MONTH]
    )

    fun toGregorianCalendar(): GregorianCalendar = GregorianCalendar(yyAD, mmAD - 1, ddAD)
    override fun toString(): String {
        return "${yyAD}-${mmAD}-${ddAD}"
    }
}

fun String.toDateAD(): DateAD {
    val parts = split("-")
    return DateAD(yyAD = parts[0].toInt(), mmAD = parts[1].toInt(), ddAD = parts[2].toInt())
}

private fun DateAD.toDateTime() = DateTime(yyAD, mmAD, ddAD, 0, 0)
fun daysBetween(start: DateAD, end: DateAD) = Days.daysBetween(
    start.toDateTime(), end.toDateTime()
).days

data class DateBS(var yyBS: Int, var mmBS: Int, var ddBS: Int)

