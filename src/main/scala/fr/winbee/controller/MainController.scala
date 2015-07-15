package fr.winbee.controller

import java.io.File

import fr.winbee.domain.Record
import fr.winbee.service.{DigService, OdsBuilderService}

object MainController {
  def exportData(startingDate: Int, endindDate: Int, templateFile: File, inputDatabaseFile: File, outputFile: File) = {

    val data: List[Record] = DigService.readData(inputDatabaseFile, startingDate, endindDate)

    OdsBuilderService.create(data,templateFile,outputFile)
  }
}
