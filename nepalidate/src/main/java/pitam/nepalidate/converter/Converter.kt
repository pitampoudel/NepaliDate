package pitam.nepalidate.converter

import pitam.nepalidate.converter.Constants.daysInMonthMapBS
import pitam.nepalidate.converter.Constants.daysInMonthOfAD
import pitam.nepalidate.converter.Constants.daysInMonthOfADLeapYear
import pitam.nepalidate.converter.Constants.endingAdDate
import pitam.nepalidate.converter.Constants.endingBsDate
import pitam.nepalidate.converter.Constants.startingAdDate
import pitam.nepalidate.converter.Constants.startingBsDate
import java.util.*

private fun DateAD.isInConversionRange() =
    yyAD in startingAdDate.yyAD..endingAdDate.yyAD && mmAD in 1..12 && ddAD in 1..31

fun DateAD.convertToBS(): DateBS = if (isInConversionRange()) {
    var days = daysBetween(startingAdDate, this)
    startingBsDate.copy().apply {
        while (days != 0) {
            val daysInCurrentMonth: Int = daysInMonthMapBS.get(yyBS)[mmBS]
            ddBS++
            if (ddBS > daysInCurrentMonth) {
                mmBS++
                ddBS = 1
            }
            if (mmBS > 12) {
                yyBS++
                mmBS = 1
            }
            days--
        }
    }
} else throw IllegalArgumentException("Out of Range: Date is out of range to Convert")



private fun DateBS.isInConversionRange() =
    yyBS in startingBsDate.yyBS..endingBsDate.yyBS && mmBS in 1..12 && ddBS in 1..32

private fun isEngLeapYear(year: Int) = Calendar.getInstance().apply {
    this[Calendar.YEAR] = year
}.getActualMaximum(Calendar.DAY_OF_YEAR) > 365

fun DateBS.convertToAD(): DateAD {
    return if (isInConversionRange()) {
        var totalNepDaysCount: Long = 0

        //count total no of days in nepali year from our starting range
        for (i in startingBsDate.yyBS until yyBS) {
            for (j in 1..12) {
                totalNepDaysCount += daysInMonthMapBS[i][j]
            }
        }
        //count total days in terms of month
        for (j in startingBsDate.mmBS until mmBS) {
            totalNepDaysCount += daysInMonthMapBS[yyBS][j]
        }

        //count total days in terms of date
        totalNepDaysCount += (ddBS - startingBsDate.ddBS).toLong()


        var endDayOfMonth: Int
        startingAdDate.copy().apply {
            while (totalNepDaysCount != 0L) {
                endDayOfMonth = if (isEngLeapYear(yyAD)) {
                    daysInMonthOfADLeapYear[mmAD]
                } else {
                    daysInMonthOfAD[mmAD]
                }
                ddAD++
                if (ddAD > endDayOfMonth) {
                    mmAD++
                    ddAD = 1
                    if (mmAD > 12) {
                        yyAD++
                        mmAD = 1
                    }
                }
                totalNepDaysCount--
            }
        }

    } else throw IllegalArgumentException("Out of Range: Date is out of range to Convert")
}
