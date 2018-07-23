package sql

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

class DatabaseExecutionContext(actorSystem: ActorSystem, database: String = "database.dispatcher" ) extends CustomExecutionContext(actorSystem, database) {

}
