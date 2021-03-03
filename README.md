# VerticalCalendar Infinite scroll
Scroll calendar days infinitely in a vertical column in weekly view

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/emcthye/VerticalCalendar/blob/master/LICENSE)

<img src="screenshots/screenshot1.png" width="400">


## Installation

#### Import with Gradle:

```
dependencies {
    implementation 'com.github.Lipdroid:InfiniteScrollWeekCalendar:0.1.0'
}
```
## Basic Usage
```xml
<jp.co.infinitescrollweekcalendar.library.customViews.InfiniteScrollWeekCalendarView
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
#### Initialize the calendar in oncreate:
```kotlin
InfiniteScrollWeekCalendarView(this)
```
### Month Change Scroll Listener
#### Implement the month change callback:
```kotlin
InfiniteScrollWeekCalendarView(this).setCurrentMonthCallbackListener(object : OnCurrentMonthChangeCallback{
            override fun onMonthChangedCallback(date: String) {
            //change the header text view when scroll to next month or previous month
            }
        })
```
