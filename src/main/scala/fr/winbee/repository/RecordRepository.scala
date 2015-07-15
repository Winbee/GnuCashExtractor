package fr.winbee.repository

import java.io.{InputStream, File}
import java.nio.charset.StandardCharsets
import java.sql.{Connection, DriverManager, SQLException}

import fr.winbee.domain.Record
import org.apache.commons.io.IOUtils


object RecordRepository {
  def readRecord(inputDatabaseFile: File, startingDate: Int = 19000101, endingDate: Int = 30000101): List[Record] = {
    var recordList: List[Record] = List()

    Class.forName("org.sqlite.JDBC")

    var connection: Connection = null
    try {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:" + inputDatabaseFile.getAbsolutePath)

      val stream: InputStream = getClass.getResourceAsStream("/sql/readTable.sql")
      val queryString: String = IOUtils.toString(stream, StandardCharsets.UTF_8)
      val prepareStatement = connection.prepareStatement(queryString)
      prepareStatement.setQueryTimeout(30)

      for (i <- 1 to 10 by 2) {
        prepareStatement.setInt(i, startingDate)
        prepareStatement.setInt(i + 1, endingDate)
      }

      val rs = prepareStatement.executeQuery()
      while (rs.next()) {
        // read the result set
        val description = rs.getString("description")
        val name = rs.getString("name")
        val date = rs.getString("post_date")
        val value = rs.getInt("value_num")

        System.out.println("description = " + description)
        System.out.println("name = " + name)
        System.out.println("post_date = " + date)
        System.out.println("value_num = " + value)

        recordList = recordList :+ new Record(description, name, date, value)
      }
    }
    catch {
      case e: SQLException =>
        // if the error message is "out of memory",
        // it probably means no database file is found
        System.err.println(e.getMessage());
    }
    finally {
      try {
        if (connection != null)
          connection.close();
      }
      catch {
        case e: SQLException =>
          // connection close failed.
          System.err.println(e);
      }
    }

    recordList
  }

  def calculateSum(inputDatabaseFile: File, startingDate: Int = 19000101): List[Record] = {
    var recordList: List[Record] = List()

    Class.forName("org.sqlite.JDBC")

    var connection: Connection = null
    try {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:" + inputDatabaseFile.getAbsolutePath)

      val stream: InputStream = getClass.getResourceAsStream("/sql/sumTable.sql")
      val queryString: String = IOUtils.toString(stream, StandardCharsets.UTF_8)
      val prepareStatement = connection.prepareStatement(queryString)
      prepareStatement.setQueryTimeout(30)

      for (i <- 1 to 5) {
        prepareStatement.setInt(i, startingDate)
      }

      val rs = prepareStatement.executeQuery()
      while (rs.next()) {
        // read the result set
        val description = "Initial Sum"
        val name = rs.getString("name")
        val date = startingDate.toString
        val value = rs.getInt("value_num")

        System.out.println("description = " + description)
        System.out.println("name = " + name)
        System.out.println("post_date = " + date)
        System.out.println("value_num = " + value)

        recordList = recordList :+ new Record(description, name, date, value)
      }
    }
    catch {
      case e: SQLException =>
        // if the error message is "out of memory",
        // it probably means no database file is found
        System.err.println(e.getMessage());
    }
    finally {
      try {
        if (connection != null)
          connection.close();
      }
      catch {
        case e: SQLException =>
          // connection close failed.
          System.err.println(e);
      }
    }

    recordList
  }
}
