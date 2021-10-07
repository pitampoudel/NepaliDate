package pitam.nepalidate.utils

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.*
import pitam.nepalidate.converter.Constants.endingAdDate
import pitam.nepalidate.converter.Constants.startingAdDate
import pitam.nepalidate.converter.DateAD
import java.util.*

fun FragmentManager.showMaterialDatePicker(
    tag: String = "TAG",
    start: DateAD = startingAdDate,
    end: DateAD = endingAdDate,
    selection: DateAD,
    onPicked: (DateAD) -> Unit
) {
    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setCalendarConstraints(
            CalendarConstraints.Builder()
                .setStart(start.materialDatePickerMillis())
                .setEnd(end.materialDatePickerMillis())
                .setValidator(CompositeDateValidator.allOf(ArrayList<CalendarConstraints.DateValidator>().apply {
                    add(DateValidatorPointForward.from(start.materialDatePickerMillis()))
                    add(DateValidatorPointBackward.before(end.materialDatePickerMillis()))
                }))
                .build()
        ).setSelection(selection.materialDatePickerMillis())
        .build()

    datePicker.addOnPositiveButtonClickListener {
        onPicked(DateAD(GregorianCalendar().apply { timeInMillis = it }))
    }
    datePicker.show(this, tag)
}

private fun DateAD.materialDatePickerMillis() = GregorianCalendar().apply {
    timeInMillis = MaterialDatePicker.todayInUtcMilliseconds()
}.apply {
    this[Calendar.YEAR] = yyAD
    this[Calendar.MONTH] = mmAD - 1
    this[Calendar.DAY_OF_MONTH] = ddAD
}.timeInMillis

