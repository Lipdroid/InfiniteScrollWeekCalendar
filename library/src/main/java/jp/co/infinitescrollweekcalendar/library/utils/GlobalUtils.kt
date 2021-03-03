package jp.co.infinitescrollweekcalendar.library.utils

import android.util.Log
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
class GlobalUtils {
    companion object{
        const val DATE_FORMAT_MAIN = "M-dd-yyyy"
        fun convertStringDateToDateObject(date_str: String): Date? {
            try {
                val sdf = SimpleDateFormat(DATE_FORMAT_MAIN)
                return sdf.parse(date_str)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        fun convertDateObjectToString(date: Date): String?{
            try {
                val format: DateFormat = SimpleDateFormat(DATE_FORMAT_MAIN)
                return format.format(date)
            } catch (e: ParseException) {
            }
            return null
        }

        fun formatDateToDaysOfMonth(dateStr: String): String? {
            val sdf = SimpleDateFormat("M-dd-yyyy")
            var convertedDate: Date? = null
            var formattedDate: String? = null
            try {
                convertedDate = sdf.parse(dateStr)
                formattedDate = SimpleDateFormat("dd").format(convertedDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return formattedDate
        }
        fun checkDateIsInCurrentMonth(dateStr: String,highlighted_month: String?): Boolean {
            var convertedDate: Date? = null
            try {
                convertedDate = convertStringDateToDateObject(dateStr)
                val instance = Calendar.getInstance()
                instance.time = convertedDate
                val month = instance.get(Calendar.MONTH)
                val year = instance.get(Calendar.YEAR)
                //get currently viewed month
                highlighted_month?.let {
                    Log.e("Currently Viewed", highlighted_month)
                    convertedDate = convertStringDateToDateObject(highlighted_month)
                    instance.time = convertedDate
                    val currentMonth = instance.get(Calendar.MONTH)
                    val currentYear = instance.get(Calendar.YEAR)
                    if ((month) == currentMonth && year == currentYear) {
                        return true
                    }
                }

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return false
        }

    }
}