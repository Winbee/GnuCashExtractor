package fr.winbee.utils

import fr.winbee.UnitSpec
import org.joda.time.DateTime


class DateUtilsTest extends UnitSpec {

  "calculateRange" should "return expected value when integer is given" in {
    //Given
    val nbMonth = 3
    val now = DateTime.now()
    val expectedStop = now.getYear * 10000 + now.getMonthOfYear * 100 + now.getDayOfMonth

    val nowMinusMonth = now.minusMonths(nbMonth).withDayOfMonth(1).withTimeAtStartOfDay()
    val expectedStart = nowMinusMonth.getYear * 10000 + nowMinusMonth.getMonthOfYear * 100 + nowMinusMonth.getDayOfMonth

    //When
    val range: (Int, Int) = DateUtils.calculateRange(nbMonth)

    //Then
    range._1 shouldEqual expectedStart
    range._2 shouldEqual expectedStop
  }

}
