package fr.winbee.domain

/**
 * One transaction from GnuCash
 * @param description
 * @param name
 * @param date
 * @param value
 */
case class Record(val description: String, val name: String, val date: String, val value: Int) {

}
