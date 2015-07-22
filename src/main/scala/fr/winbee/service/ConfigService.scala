package fr.winbee.service

import java.io.{File, FileInputStream}
import java.util.Properties


class ConfigService {

  def getDatabaseFile: File = {
    val properties: Properties = loadPropertiesFile()
    new File(properties.getProperty("dataBasePathName"))
  }

  def getTemplateFile: File = {
    val properties: Properties = loadPropertiesFile()
    new File(properties.getProperty("templatePathName"))
  }

  def getNbMonths: Int = {
    val properties: Properties = loadPropertiesFile()
    properties.getProperty("nbMonths").toInt
  }

  def getStartingDate: Int = {
    val properties: Properties = loadPropertiesFile()
    properties.getProperty("startingDate").toInt
  }

  def getEndingDate: Int = {
    val properties: Properties = loadPropertiesFile()
    properties.getProperty("endingDate").toInt
  }

  private def loadPropertiesFile(): Properties = {
    val mainProperties = new Properties()

    val configFile = new FileInputStream("./config.properties")

    mainProperties.load(configFile)

    mainProperties
  }

}
