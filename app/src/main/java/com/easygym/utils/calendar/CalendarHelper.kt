package com.easygym.utils.calendar

import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.TextStyle
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarHelper @Inject constructor() {

    fun getMonthLabel(month: YearMonth): String {
        val name = month.month
            .getDisplayName(TextStyle.FULL, Locale.ITALIAN)
            .replaceFirstChar { it.uppercaseChar() }
        return "$name ${month.year}"
    }

    fun getDayLabel(date: LocalDate): String {
        val dow = date.dayOfWeek
            .getDisplayName(TextStyle.FULL, Locale.ITALIAN)
            .replaceFirstChar { it.uppercaseChar() }
        val mon = date.month
            .getDisplayName(TextStyle.FULL, Locale.ITALIAN)
            .replaceFirstChar { it.uppercaseChar() }
        return "$dow ${date.dayOfMonth} $mon"
    }

    fun generateCalendarRows(month: YearMonth): List<List<Triple<Int, Boolean, LocalDate>>> {
        val dayOffset = month.atDay(1).dayOfWeek.value - 1
        val daysInMonth = month.lengthOfMonth()

        val prevMonth = month.minusMonths(1)
        val prevLen = prevMonth.lengthOfMonth()

        val days = buildList {
            if (dayOffset > 0) {
                (prevLen - dayOffset + 1..prevLen).forEach { d ->
                    add(Triple(d, false, prevMonth.atDay(d)))
                }
            }

            (1..daysInMonth).forEach { d ->
                add(Triple(d, true, month.atDay(d)))
            }

            val nextMonth = month.plusMonths(1)
            repeat(42 - size) { i ->
                add(Triple(i + 1, false, nextMonth.atDay(i + 1)))
            }
        }

        return days.chunked(7)
    }

    fun defaultSelectionForMonth(month: YearMonth): LocalDate =
        if (YearMonth.from(LocalDate.now()) == month) LocalDate.now()
        else month.atDay(1)

    fun getFirstMomentOfMonth(month: YearMonth): String =
        month.atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant().toString()

    fun getLastMomentOfMonth(month: YearMonth): String =
        month.atEndOfMonth().atTime(23, 59, 59, 999_999_999).atZone(ZoneOffset.UTC).toInstant().toString()
}
