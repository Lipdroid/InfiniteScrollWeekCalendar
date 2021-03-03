package jp.co.infinitescrollweekcalendar.library.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.co.infinitescrollweekcalendar.library.utils.GlobalUtils
import jp.co.infinitescrollweekcalendar.library.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
        private val context: Context,
        private var calendar: Calendar) :
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    var dateMap: HashMap<Int, Array<String?>> = hashMapOf()
    var compareDate: String? = null

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_sunday = itemView.findViewById<TextView>(R.id.tv_sunday)
        var tv_monday = itemView.findViewById<TextView>(R.id.tv_monday)
        var tv_tuesday = itemView.findViewById<TextView>(R.id.tv_tuesday)
        var tv_wednesday = itemView.findViewById<TextView>(R.id.tv_wednesday)
        var tv_thursday = itemView.findViewById<TextView>(R.id.tv_thursday)
        var tv_friday = itemView.findViewById<TextView>(R.id.tv_friday)
        var tv_saturday = itemView.findViewById<TextView>(R.id.tv_saturday)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.item_cell_calender, parent, false)
        return ItemViewHolder(itemView = view)
    }
    //change the current highliighted month
    fun changeMonthView(date: String) {
        compareDate = date
    }

    //get the current highlighted month
    fun getViewableMonthDate(): String {
        return if (compareDate == null) {
            //send the current date
            val format: DateFormat = SimpleDateFormat("M-dd-yyyy")
            format.format(Date())
        } else {
            compareDate!!
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val midPosition: Int = Int.MAX_VALUE / 2
        val currentWeek: Int = position - midPosition

        this.calendar = Calendar.getInstance()
        this.calendar?.firstDayOfWeek = Calendar.SUNDAY
        this.calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        this.calendar.add(Calendar.DATE, (7 * currentWeek))

        val days = arrayOfNulls<String>(7)
        for (i in 0..6) {
            days[i] = GlobalUtils.convertDateObjectToString(this.calendar.time)
            this.calendar.add(Calendar.DATE, 1)
        }
        //save the dates in a hash map to get the values later
        dateMap[position] = days
        setUpCalendarCells(holder,days)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }


    private fun setUpCalendarCells(holder: ItemViewHolder, days: Array<String?>) {
        if (GlobalUtils.checkDateIsInCurrentMonth(days[0]!!,getViewableMonthDate())) {
            holder.tv_sunday.setTextColor(Color.BLACK)
        } else {
            holder.tv_sunday.setTextColor(Color.GRAY)
        }

        if (GlobalUtils.checkDateIsInCurrentMonth(days[1]!!,getViewableMonthDate())) {
            holder.tv_monday.setTextColor(Color.BLACK)
        } else {
            holder.tv_monday.setTextColor(Color.GRAY)
        }

        if (GlobalUtils.checkDateIsInCurrentMonth(days[2]!!,getViewableMonthDate())) {
            holder.tv_tuesday.setTextColor(Color.BLACK)
        } else {
            holder.tv_tuesday.setTextColor(Color.GRAY)
        }
        if (GlobalUtils.checkDateIsInCurrentMonth(days[3]!!,getViewableMonthDate())) {
            holder.tv_wednesday.setTextColor(Color.BLACK)
        } else {
            holder.tv_wednesday.setTextColor(Color.GRAY)
        }
        if (GlobalUtils.checkDateIsInCurrentMonth(days[4]!!,getViewableMonthDate())) {
            holder.tv_thursday.setTextColor(Color.BLACK)
        } else {
            holder.tv_thursday.setTextColor(Color.GRAY)
        }
        if (GlobalUtils.checkDateIsInCurrentMonth(days[5]!!,getViewableMonthDate())) {
            holder.tv_friday.setTextColor(Color.BLACK)
        } else {
            holder.tv_friday.setTextColor(Color.GRAY)
        }
        if (GlobalUtils.checkDateIsInCurrentMonth(days[6]!!,getViewableMonthDate())) {
            holder.tv_saturday.setTextColor(Color.BLACK)
        } else {
            holder.tv_saturday.setTextColor(Color.GRAY)
        }
        holder.tv_sunday.text = GlobalUtils.formatDateToDaysOfMonth(days[0]!!)
        holder.tv_monday.text = GlobalUtils.formatDateToDaysOfMonth(days[1]!!)
        holder.tv_tuesday.text = GlobalUtils.formatDateToDaysOfMonth(days[2]!!)
        holder.tv_wednesday.text = GlobalUtils.formatDateToDaysOfMonth(days[3]!!)
        holder.tv_thursday.text = GlobalUtils.formatDateToDaysOfMonth(days[4]!!)
        holder.tv_friday.text = GlobalUtils.formatDateToDaysOfMonth(days[5]!!)
        holder.tv_saturday.text = GlobalUtils.formatDateToDaysOfMonth(days[6]!!)

        holder.tv_saturday.setOnClickListener {
            Log.e("Saturday", days[6].toString())
        }
        holder.tv_sunday.setOnClickListener {
            Log.e("Sunday", days[0].toString())
        }
        holder.tv_monday.setOnClickListener {
            Log.e("Monday", days[1].toString())
        }
        holder.tv_tuesday.setOnClickListener {
            Log.e("Tuesday", days[2].toString())
        }
        holder.tv_wednesday.setOnClickListener {
            Log.e("Wednesday", days[3].toString())
        }
        holder.tv_thursday.setOnClickListener {
            Log.e("Thursday", days[4].toString())
        }
        holder.tv_friday.setOnClickListener {
            Log.e("Friday", days[5].toString())
        }

    }

}