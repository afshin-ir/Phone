package org.fossify.phone.extensions

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.PersianCalendar
import android.text.format.DateUtils
import android.content.Context
import org.fossify.commons.extensions.baseConfig
import org.fossify.commons.extensions.getTimeFormat
import java.util.Date
import java.util.Locale

fun Long.formatJalaliDateOrTime(
    context: Context,
    hideTimeOnOtherDays: Boolean,
    showCurrentYear: Boolean,
    hideTodaysDate: Boolean = true,
): String {
    val locale = Locale.forLanguageTag("fa-IR")
    val calendar = PersianCalendar(locale).apply { timeInMillis = this@formatJalaliDateOrTime }
    val currentCalendar = PersianCalendar(locale).apply { timeInMillis = System.currentTimeMillis() }

    if (hideTodaysDate && DateUtils.isToday(this)) {
        return SimpleDateFormat(context.getTimeFormat(), locale)
            .apply { this.calendar = calendar }
            .format(Date(this))
    }

    var format = context.baseConfig.dateFormat
    if (!showCurrentYear && calendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)) {
        format = format.replace("y", "").trim().trim('-').trim('.').trim('/')
    }
    if (!hideTimeOnOtherDays) {
        format += ", ${context.getTimeFormat()}"
    }

    return SimpleDateFormat(format, locale)
        .apply { this.calendar = calendar }
        .format(Date(this))
}
