package rus.one.app.profile

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Period(
    private val startDate: Date,
    private val endDate: Date? = null
) {
    companion object {
        private val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        private const val CURRENT_DATE = "НВ"
    }

    fun getFormattedPeriod(): String {
        val start = dateFormat.format(startDate)
        val end = endDate?.let { dateFormat.format(it) } ?: CURRENT_DATE
        return "$start - $end"
    }
}