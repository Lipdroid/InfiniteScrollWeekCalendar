package jp.co.infinitescrollweekcalendar.library.customViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.co.infinitescrollweekcalendar.library.adapters.CalendarAdapter
import jp.co.infinitescrollweekcalendar.library.helpers.SnappingLinearLayoutManager
import jp.co.infinitescrollweekcalendar.library.utils.GlobalUtils
import java.util.*
import java.util.Calendar.*
import kotlin.math.ceil
import kotlin.math.floor

class InfiniteRecyclerView : RecyclerView {
    /**
     * This interface is for getting the current month after scroll
     * */
    interface OnMonthScrolled {
        fun onMonthScrolled(date: String)
        fun onChangeMonth(date: String)
    }

    companion object {
        private var infiniteRecyclerCalendarAdapter: CalendarAdapter? = null
        private var linearLayoutManager: SnappingLinearLayoutManager? = null
        private var onMonthChanged: OnMonthScrolled? = null
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    fun initialise(context: Context) {
        this.setItemViewCacheSize(20)
        this.setHasFixedSize(true)
        val calendar = getInstance()

        infiniteRecyclerCalendarAdapter = CalendarAdapter(context, calendar)
        linearLayoutManager = SnappingLinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
        )
        this.layoutManager = linearLayoutManager
        this.adapter = infiniteRecyclerCalendarAdapter
        scrollToPosition(Int.MAX_VALUE / 2)
        invalidate()
    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun setMonChangeScrollListener(listener: OnMonthScrolled) {
        onMonthChanged = listener
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        Log.e("onFling", "true")
        return super.fling(velocityX, velocityY)
    }



    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        //chcek scoll is upside or downside
        val isScrollingDown = dy <= 0
        Log.e("isScrollingDown", isScrollingDown.toString())

        //get the first visible row of dated
        val firstVisiblePos = (this.layoutManager as SnappingLinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        Log.e("firstVisiblePos", firstVisiblePos.toString())
        if (adapter is CalendarAdapter && firstVisiblePos!! >= 0) {
            val dates = (adapter as CalendarAdapter).dateMap[firstVisiblePos]
            Log.e("firstVisiblePos", dates!!.joinToString())
            //check user scroll to different month
            //get the first cell of first row date
            var convertedDate = GlobalUtils.convertStringDateToDateObject(dates[0]!!)
            var instance = Calendar.getInstance()
            instance.time = convertedDate
            val first_cell_month = instance.get(Calendar.MONTH)
            //get the last cell of first row date
            convertedDate = GlobalUtils.convertStringDateToDateObject(dates[6]!!)
            instance = Calendar.getInstance()
            instance.time = convertedDate
            val last_cell_month = instance.get(Calendar.MONTH)

            for (date in dates) {
                //check eeach date of the first row
                val convertedDate = GlobalUtils.convertStringDateToDateObject(date!!)
                val instance = getInstance()
                instance.time = convertedDate
                val day = instance.get(DAY_OF_MONTH)
                //if there is a 1 date in the first row that means change the month
                //or if its a fling and scrolling up if first cell and last cell is same month
                //then highlight that month
                if (day == 1 || (!isScrollingDown && first_cell_month == last_cell_month)) {
                    (adapter as CalendarAdapter).changeMonthView(date!!)
                    onMonthChanged?.onMonthScrolled(date)
                    onMonthChanged?.onChangeMonth(date)
                    this.post(Runnable {
                        adapter?.notifyDataSetChanged() })
                    break
                }
            }
        }
    }

}