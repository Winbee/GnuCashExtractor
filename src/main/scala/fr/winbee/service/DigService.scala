package fr.winbee.service

import java.io.File

import fr.winbee.domain.Record
import fr.winbee.repository.RecordRepository

object DigService {
  def readData(inputDatabaseFile: File, startingDate: Int, endindDate: Int): scala.List[_root_.fr.winbee.domain.Record] = {
    val record: List[Record] = RecordRepository.readRecord(inputDatabaseFile, startingDate, endindDate)
    val sumRecord: List[Record] = RecordRepository.calculateSum(inputDatabaseFile, startingDate)

    return record ++ sumRecord
  }

}
