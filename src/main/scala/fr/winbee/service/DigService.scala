package fr.winbee.service

import java.io.File

import fr.winbee.domain.Record
import fr.winbee.repository.RecordRepository

class DigService(val recordRepository: RecordRepository) {
  def readData(inputDatabaseFile: File, startingDate: Int, endindDate: Int): scala.List[_root_.fr.winbee.domain.Record] = {
    val record: List[Record] = recordRepository.readRecord(inputDatabaseFile, startingDate, endindDate)
    val sumRecord: List[Record] = recordRepository.calculateSum(inputDatabaseFile, startingDate)

    record ++ sumRecord
  }

}
