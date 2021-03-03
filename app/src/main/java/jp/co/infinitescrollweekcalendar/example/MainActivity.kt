package jp.co.infinitescrollweekcalendar.example

import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.co.infinitescrollweekcalendar.library.customViews.InfiniteScrollWeekCalendarView
import jp.co.infinitescrollweekcalendar.library.customViews.OnCurrentMonthChangeCallback

class MainActivity : AppCompatActivity(),OnCurrentMonthChangeCallback {
    private var inifinite_scroll_calendar: InfiniteScrollWeekCalendarView? = null
    var tv_header_month_view: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_header_month_view = findViewById(R.id.tv_header_month_view)
        inifinite_scroll_calendar = InfiniteScrollWeekCalendarView(this)
        inifinite_scroll_calendar?.setCurrentMonthCallbackListener(this)
    }

    override fun onMonthChangedCallback(date: String) {
        runOnUiThread {
            tv_header_month_view?.setText(date)
        }
    }

}