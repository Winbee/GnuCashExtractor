package fr.winbee.app

import com.softwaremill.macwire.Macwire
import fr.winbee.controller.MainController
import fr.winbee.repository.RecordRepository
import fr.winbee.service.{ElasticSearchService, OdsBuilderService, DigService, ConfigService}

trait DependencyInjectionBuilder extends Macwire{
  lazy val configService   = wire[ConfigService]
  lazy val recordRepository   = wire[RecordRepository]
  lazy val digService       = wire[DigService]
  lazy val odsBuilderService = wire[OdsBuilderService]
  lazy val elasticSearchService   = wire[ElasticSearchService]
  lazy val mainController = wire[MainController]
}
