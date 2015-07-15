package fr.winbee.app

import java.io.File

import fr.winbee.controller.MainController
import fr.winbee.service.ConfigService
import fr.winbee.utils.DateUtils
import org.clapper.argot.{ArgotConverters, ArgotParser, ArgotUsageException}

object ConsoleApp {

  import ArgotConverters._

  val parser = new ArgotParser(programName = "gnuCash extractor", preUsage = Some("Version 1.0"))

  val verbose = parser.flag[Boolean](List("v", "verbose"),
    "Increment the verbosity level.")

  val nbMonths = parser.option[Int](List("m", "months"), "nb",
    "Number of month from now to consider for extract.")

  val startingDate = parser.option[Int](List("s", "startingDate"), "YYYYMMDD",
    "The date from which the export starts, format: YYYYMMDD"
  ) {
    (dateString, opt) =>
      val dateInt: Int = ArgotConverters.convertInt(dateString, opt)

      if (nbMonths.value.isDefined)
        parser.usage("The startingDate cannot be defined with a \"month\" option.")

      if (dateInt < 19000101 || 30000101 < dateInt)
        parser.usage("The date must be beetween 19000101 and 30000101 : actual:\"" + dateInt + "\".")

      dateInt
  }

  val endingDate = parser.option[Int](List("e", "endingDate"), "YYYYMMDD",
    "The date to which the export ends, format: YYYYMMDD") {
    (dateString, opt) =>
      val dateInt: Int = ArgotConverters.convertInt(dateString, opt)

      if (nbMonths.value.isDefined)
        parser.usage("The endingDate cannot be defined with a \"month\" option.")

      if (!startingDate.value.isDefined)
        parser.usage("The startingDate has to be defined if endingDate is wanted.")

      if (dateInt < 19000101 || 30000101 < dateInt)
        parser.usage("The date must be beetween 19000101 and 30000101 : actual:\"" + dateInt + "\".")

      if (dateInt < startingDate.value.get)
        parser.usage("The endingDate must be after the startingDate.")

      dateInt
  }

  val template = parser.option[File](List("t", "template"), "pathname",
    "template to use (must be an openOffice file).") {
    (pathname, opt) =>

      val file = new File(pathname)
      if (!file.exists)
        parser.usage("Open office template \"" + pathname + "\" does not exist.")

      file
  }

  val output = parser.parameter[File]("outputFile",
    "Output file to which to write.",
    false) {
    (pathname, opt) =>

      val file = new File(pathname)

      if (file.exists && !file.canRead)
        parser.usage("Output file \"" + pathname + "\" is not readable.")

      if (file.exists && !file.canWrite)
        parser.usage("Output file \"" + pathname + "\" is not writable.")

      file
  }

  val inputDatabase = parser.parameter[File]("inputDatabase",
    "Input gnuCash DataBase to read.",
    true) {
    (pathname, opt) =>

      val file = new File(pathname)
      if (!file.exists)
        parser.usage("Input gnuCash DataBase \"" + pathname + "\" does not exist.")

      file
  }

  def launchExport = {
    var startingD: Int = 0
    var endingD: Int = 0
    if (nbMonths.value.isDefined) {
      val (startingD2, endingD2) = DateUtils.calculateRange(nbMonths.value.get)
      startingD = startingD2
      endingD = endingD2
    } else {
      if (startingDate.value.isDefined) {
        startingD = startingDate.value.get
      } else {
        startingD = ConfigService.getStartingDate
      }

      if (endingDate.value.isDefined) {
        endingD = endingDate.value.get
      } else {
        endingD = ConfigService.getEndingDate
      }
    }
    
    var templateFile : File = null
    if(template.value.isDefined){
      templateFile = template.value.get
    }else{
      templateFile = ConfigService.getTemplateFile
    }

    var inputDatabaseFile : File = null
    if(inputDatabase.value.isDefined){
      inputDatabaseFile = inputDatabase.value.get
    }else{
      inputDatabaseFile = ConfigService.getDatabaseFile
    }

    val outputFile : File = output.value.get

    MainController.exportData(startingD,endingD,templateFile,inputDatabaseFile,outputFile)
  }

  // Main program
  def main(args: Array[String]) {
    try {
      parser.parse(args)
      launchExport
    }
    catch {
      case e: ArgotUsageException => println(e.message)
    }
  }
}
