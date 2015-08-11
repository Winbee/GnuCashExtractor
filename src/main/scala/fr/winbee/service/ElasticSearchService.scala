package fr.winbee.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import fr.winbee.domain.Record
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress

class ElasticSearchService {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def create(recordList: List[Record]): Unit = {
    // on startup
    val client: TransportClient = new TransportClient()
      .addTransportAddress(new InetSocketTransportAddress("192.168.0.13", 9300))

    try {
      for (record <- recordList) {
        val jsonAsBytes: Array[Byte] = mapper.writeValueAsBytes(record)
        val response: IndexResponse = client.prepareIndex("gnucash", "transaction")
          .setSource(jsonAsBytes)
          .execute()
          .actionGet()
      }
    } finally {
      // on shutdow
      client.close()
    }
  }

}
