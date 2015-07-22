package fr.winbee.app

import com.softwaremill.macwire.Macwire
import fr.winbee.controller.MainController
import fr.winbee.repository.RecordRepository
import fr.winbee.service.{OdsBuilderService, DigService, ConfigService}

trait DependencyInjectionBuilder extends Macwire{
  lazy val configService   = wire[ConfigService]
  lazy val recordRepository   = wire[RecordRepository]
  lazy val digService       = wire[DigService]
  lazy val odsBuilderService = wire[OdsBuilderService]
  lazy val mainController = wire[MainController]
}
