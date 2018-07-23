package sql

import play.db.Database

// @Singleton
class JavaApplicationDatabase(database: Database, context: DatabaseExecutionContext) {

  var db: Database = database
  var executionContext: DatabaseExecutionContext = context


}
