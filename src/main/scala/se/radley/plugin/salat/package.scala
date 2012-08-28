package se.radley.plugin

import play.api.{Play, Application, PlayException}
import play.api.Play.current
import com.mongodb.casbah.MongoCollection

package object salat {

  /**
   * get the underlying salat MongoCollection
   * @param collectionName The MongoDB collection name
   * @param sourceName The configured source name
   * @return MongoCollection
   */
  def mongoCollection(collectionName: String, sourceName:String = "default")(implicit app: Application): MongoCollection = {
    app.plugin[SalatPlugin].map(_.collection(collectionName, sourceName)).getOrElse(throw PlayException("SalatPlugin is not registered.", "You need to register the plugin with \"500:se.radley.plugin.salat.SalatPlugin\" in conf/play.plugins"))
  }
  
  /**
   * get the underlying salat MongoCollection as a capped collection
   * @param collectionName The MongoDB collection name
   * @param cappedAt the size of the capped collection
   * @param sourceName The configured source name
   * @return MongoCollection
   */
  def mongoCappedCollection(collectionName: String, cappedAt: Int, sourceName:String = "default")(implicit app: Application): MongoCollection = {
    app.plugin[SalatPlugin].map(_.collectionWithOptions(collectionName, Map("capped" -> true, "size" -> cappedAt), sourceName)).getOrElse(throw PlayException("SalatPlugin is not registered.", "You need to register the plugin with \"500:se.radley.plugin.salat.SalatPlugin\" in conf/play.plugins"))
  }

}
