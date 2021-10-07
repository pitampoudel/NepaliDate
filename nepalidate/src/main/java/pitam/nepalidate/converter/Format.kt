package pitam.nepalidate.converter

import android.content.Context
import pitam.nepalidate.R


fun DateAD.formatAsDayMonthYear(ctx: Context) = StringBuilder()
    .append(ddAD.toString())
    .append(" ")
    .append(ctx.resources.getStringArray(R.array.ad_month_names)[mmAD])
    .append(" ")
    .append(yyAD)
    .toString()


fun DateAD.formatAsMonthYear(context: Context) = StringBuilder()
    .append(context.resources.getStringArray(R.array.ad_month_names)[mmAD])
    .append(" ")
    .append(yyAD).toString()

fun DateBS.formatAsDayMonthYear(ctx: Context) = StringBuilder()
    .append(ddBS.toString())
    .append(" ")
    .append(ctx.resources.getStringArray(R.array.bs_month_names)[mmBS])
    .append(" ")
    .append(yyBS)
    .toString()


fun DateBS.formatAsMonthYear(context: Context) = StringBuilder()
    .append(context.resources.getStringArray(R.array.bs_month_names)[mmBS])
    .append(" ")
    .append(yyBS).toString()

