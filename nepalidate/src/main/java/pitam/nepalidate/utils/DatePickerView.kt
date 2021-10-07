package pitam.nepalidate.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import pitam.nepalidate.R
import pitam.nepalidate.converter.*
import pitam.nepalidate.databinding.DatePickerViewBinding

open class DatePickerView(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    fun initWith(
        startAd: DateAD = Constants.startingAdDate,
        endAd: DateAD = Constants.endingAdDate,
        selection: DateAD
    ) {
        val start = startAd.convertToBS()
        val end = endAd.convertToBS()
        val initial = selection.convertToBS()

        with(binding) {
            spYear.apply {
                minValue = start.yyBS
                maxValue = end.yyBS
                value = initial.yyBS
                spYear.setOnValueChangedListener { _, _, _ ->
                    initMonthPicker(start, end)
                    initDayPicker(start, end)
                }
            }

            initMonthPicker(start, end)
            spMonth.value = initial.mmBS
            initDayPicker(start, end)
            spDay.value = initial.ddBS
        }
    }

    private fun DatePickerViewBinding.initMonthPicker(start: DateBS,end: DateBS) {
        spMonth.apply {
            minValue = when (spYear.value) {
                start.yyBS -> start.mmBS
                else -> 1
            }
            maxValue = when (spYear.value) {
                end.yyBS -> end.mmBS
                else -> 12
            }
            setOnValueChangedListener { _, _, _ ->
                initDayPicker(start, end)
            }

        }
    }

    private fun DatePickerViewBinding.initDayPicker(start: DateBS,end: DateBS) {
        spDay.apply {
            minValue = if (spYear.value == start.yyBS && spMonth.value == start.mmBS) start.ddBS else 1
            maxValue = if (spYear.value == end.yyBS && spMonth.value == end.mmBS) end.ddBS else Constants.daysInMonthMapBS[spYear.value][spMonth.value]
        }
    }


    private lateinit var binding: DatePickerViewBinding
    private fun loadUi(context: Context) {
        binding = DataBindingUtil.inflate(
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater),
            R.layout.date_picker_view,
            this,
            true
        )
        with(binding) {
            spMonth.setFormatter { resources.getStringArray(R.array.bs_month_names)[it] }
        }
    }
    fun getSelectedDate() = with(binding) {
        DateBS(spYear.value, spMonth.value, spDay.value).convertToAD()
    }

    init {
        loadUi(context)
    }


}