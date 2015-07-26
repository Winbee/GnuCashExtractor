package fr.winbee.service

import java.io.File

import fr.winbee.domain.Record
import org.jopendocument.dom.OOUtils
import org.jopendocument.dom.spreadsheet.SpreadSheet

class OdsBuilderService {

  def create(recordList: List[Record], templateFile: File, outputFile: File): Unit = {
    // Load the file.
    val spreadSheet = SpreadSheet.createFromFile(templateFile)
    val sheet = spreadSheet.getFirstSheet

    val mutableTableModel = spreadSheet.getTableModel("DataTable")

    var rowIndex = 1
    for (record <- recordList) {
      mutableTableModel.setValueAt(record.date, rowIndex, 0)
      mutableTableModel.setValueAt(record.description, rowIndex, 1)
      mutableTableModel.setValueAt(record.name, rowIndex, 2)
      mutableTableModel.setValueAt(record.value.toDouble / 100, rowIndex, 3)
      rowIndex += 1
    }

    // Save to file and open it.
    OOUtils.open(sheet.getSpreadSheet.saveAs(outputFile))
  }

}
