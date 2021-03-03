package jp.co.infinitescrollweekcalendar.library.customViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import jp.co.infinitescrollweekcalendar.library.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class InfiniteScrollWeekCalendarView : ConstraintLayout, InfiniteRecyclerView.OnMonthScrolled, View.OnClickListener {

    companion object {
        var tv_header_month: TextView? = null
        var rcv_calender: InfiniteRecyclerView? = null
        var btn_left_arrow: ImageButton? = null
        var btn_right_arrow: ImageButton? = null
        var listener: OnCurrentMonthChangeCallback? = null
    }


    constructor(mContext: Context) : super(mContext)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    init {
        LayoutInflater.from(context).inflate(R.layout.calendar_view, this)

        rcv_calender = findViewById(R.id.rcv_calender)
        tv_header_month = findViewById(R.id.tv_header_month_view)
        btn_left_arrow = findViewById(R.id.btn_left_arrow)
        btn_right_arrow = findViewById(R.id.btn_right_arrow)
        rcv_calender?.initialise(context)
        rcv_calender?.setMonChangeScrollListener(this)

        //set the current date on header
        val format: DateFormat = SimpleDateFormat("M-dd-yyyy")
        onMonthScrolled(format.format(Date()))
        btn_left_arrow?.setOnClickListener(this)
        btn_right_arrow?.setOnClickListener(this)
        invalidate()
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

    private fun onNextMonth() {
    }

    private fun onPreviousMonth() {
    }

    override fun onMonthScrolled(date: String) {
        val sdf = SimpleDateFormat("M-dd-yyyy")
        try {
            val convertedDate = sdf.parse(date)
            val instance = Calendar.getInstance()
            instance.time = convertedDate
            val month = instance.get(Calendar.MONTH) + 1
            val year = instance.get(Calendar.YEAR)
            listener?.onMonthChangedCallback("" + year + "年" + month + "月")
            //yyyy年mm月
//            (context as Activity).runOnUiThread {
//                tv_header_month?.text = "" + year + "年" + month + "月"
//                Log.e("Change Header:", tv_header_month?.text.toString())
//                invalidate()
//            }

        } catch (e: ParseException) {
        }
    }

    override fun onChangeMonth(date: String) {
        onMonthScrolled(date)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_left_arrow -> {
                Log.e("Onclick", "load previous month")
                onPreviousMonth()
            }
            R.id.btn_right_arrow -> {
                Log.e("Onclick", "load next month")
                onNextMonth()
            }
        }
    }
    fun setCurrentMonthCallbackListener(callback: OnCurrentMonthChangeCallback){
        listener = callback
    }

    fun updateHeader(date: String) {
        tv_header_month?.text = date
    }
}

interface OnCurrentMonthChangeCallback{
    fun onMonthChangedCallback(date: String)
}