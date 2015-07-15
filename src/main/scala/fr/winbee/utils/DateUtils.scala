package fr.winbee.utils

import org.joda.time.DateTime

object DateUtils {

  def calculateRange(nbMonthFromNow: Int): (Int, Int) = {
    val now: DateTime = DateTime.now()
    val nowMinusMonths: DateTime = now.minusMonths(nbMonthFromNow).withDayOfMonth(1).withTimeAtStartOfDay()

    val nowInt: Int = convertToInt(now)
    val nowMinusMonthsInt: Int = convertToInt(nowMinusMonths)

    (nowMinusMonthsInt, nowInt)
  }

  def convertToInt(date: DateTime): Int = {
    date.getYear * 10000 + date.getMonthOfYear * 100 + date.getDayOfMonth
  }
}
