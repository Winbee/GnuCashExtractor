package fr.winbee.controller

import java.io.File

import fr.winbee.domain.Record
import fr.winbee.service.{ElasticSearchService, DigService, OdsBuilderService}

class MainController(val digService: DigService, val odsBuilderService: OdsBuilderService, val elasticSearchService : ElasticSearchService) {
  def exportData(startingDate: Int, endindDate: Int, templateFile: File, inputDatabaseFile: File, outputFile: File) = {

    val data: List[Record] = digService.readData(inputDatabaseFile, startingDate, endindDate)

    //odsBuilderService.create(data, templateFile, outputFile)
    elasticSearchService.create(data)
  }
}
