package main;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkUpdateRequestBuilder;
import com.mongodb.BulkWriteOperation;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.model.BulkWriteOptions;

import database.DbConnection;

public class MyTimer extends TimerTask {

	@Override
	public void run() {
		
		try{
			if (DbConnection.getMongoClient() == null || DbConnection.getDb() == null) {
				DbConnection.makeConnection();
			}
			// TODO Auto-generated method stub
			System.out.println("Timer task started at:" + new Date());

			DBCollection dbCollection = DbConnection.getDb().getCollection("col_data");
			DBCursor cursor = dbCollection.find();
			BasicDBObject searchResult = null;
			while (cursor.hasNext()) {
				searchResult = (BasicDBObject) cursor.next();
				System.out.println(
						searchResult.getString("1") + " " + searchResult.getString("2") + " " + searchResult.getString("3")
								+ " " + searchResult.getString("4") + " " + searchResult.getString("5"));
			}
			
			updateData();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void updateData() {
		
		if (DbConnection.getMongoClient() == null || DbConnection.getDb() == null) {
			DbConnection.makeConnection();
		}
		
		DBCollection dbCollection = DbConnection.getDb().getCollection("col_data");

		// bulk update--
		BulkWriteOperation bulkWriteOperation = dbCollection.initializeOrderedBulkOperation();
		
		BasicDBObject querySearch = new BasicDBObject();
		querySearch.put("4","sakshi");
		
		BasicDBObject updateQuery = new BasicDBObject();
		querySearch.put("4","era");
		
		
		//bulkWriteOperation.find(querySearch).update(new BasicDBObject("$set", updateQuery));

		bulkWriteOperation.find(new BasicDBObject("4", "pal"))
				.update(new BasicDBObject(new BasicDBObject("$set", new BasicDBObject("4", "sakshi"))));

		bulkWriteOperation.execute();

		/*
		 * BasicDBObject prev = new BasicDBObject(); prev.put("4", "sakshi");
		 * BasicDBObject newvalue = new BasicDBObject(); newvalue.put("4",
		 * "era"); BasicDBObject udatequery = new BasicDBObject();
		 * udatequery.put("$set", newvalue); dbCollection.update(prev,
		 * udatequery);
		 * 
		 * // bulkWriteOperation.insert(udatequery); //
		 * bulkWriteOperation.execute();
		 * 
		 * DBCursor cursor = dbCollection.find(prev);
		 */

		/*
		 * BasicDBObject prev2 = new BasicDBObject(); prev.put("4", "acch");
		 * BasicDBObject newvalue2 = new BasicDBObject(); newvalue.put("5",
		 * "sharma"); BasicDBObject udatequery2 = new BasicDBObject();
		 * udatequery.put("$set", newvalue2); dbCollection.update(prev2,
		 * udatequery2);
		 * 
		 * bulkWriteOperation.find(prev2).update(udatequery2);
		 */

		/*
		 * while (cursor.hasNext()) { BasicDBObject result = (BasicDBObject)
		 * cursor.next(); BasicDBObject udatequery = new BasicDBObject();
		 * udatequery.put("$set", newvalue);
		 * bulkWriteOperation.insert(udatequery); }
		 */
		/*
		 * while (cursor.hasNext()) { BasicDBObject result = (BasicDBObject)
		 * cursor.next(); System.out.println("find --"+result);
		 * 
		 * }
		 */

		/*
		 * BasicDBObject searchQuery = new BasicDBObject(); searchQuery.put("5",
		 * "kapoor");
		 * 
		 * DBCursor dbCursor = dbCollection.find(searchQuery); BasicDBObject
		 * searchResult = null;
		 * 
		 * while (dbCursor.hasNext()) { searchResult = (BasicDBObject)
		 * dbCursor.next(); System.out.println("Name after search: " +
		 * searchResult.getString("4"));
		 * 
		 * }
		 * 
		 * //bulkWriteOperation.execute(); dbCollection.updateMulti(prev,
		 * udatequery);
		 */

	}

	static void upsert() {
		DBCollection dbCollection = DbConnection.getDb().getCollection("col_data");
		BulkWriteOperation bulkWriteOperation = dbCollection.initializeOrderedBulkOperation();
		BasicDBObject basicDBObject = new BasicDBObject();
		BasicDBObject basicDBObjectNew = new BasicDBObject();
		basicDBObjectNew.put("1", "My");
		basicDBObjectNew.put("2", "name");
		basicDBObjectNew.put("3", "is");
		//basicDBObjectNew.put("4","sakshi" );
		basicDBObjectNew.put("5", "uuuu");
		basicDBObject.put("$setOnInsert", new BasicDBObject("4", "jkkk"));
		basicDBObject.put("$set", basicDBObjectNew);
		bulkWriteOperation.find(new BasicDBObject("4", "jkkk")).upsert().update(basicDBObject);

		BasicDBObject basicDBObject2 = new BasicDBObject();
		basicDBObject2.append("1", "My").append("2", "name").append("3", "is").append("4", "sakshi").append("5", "fhg");
		bulkWriteOperation.find(new BasicDBObject("4", "sakshi")).upsert().replaceOne(basicDBObject2);

		bulkWriteOperation.execute();
	}

	static void Delete() {
		DBCollection dbCollection = DbConnection.getDb().getCollection("col_data");
		BulkWriteOperation bulkWriteOperation = dbCollection.initializeOrderedBulkOperation();
		BasicDBObject basicDBObject1 = new BasicDBObject();
		basicDBObject1.put("4", "era");
		bulkWriteOperation.find(basicDBObject1).remove();
		BasicDBObject basicDBObject2 = new BasicDBObject();
		basicDBObject2.put("4", "rak");
		bulkWriteOperation.find(basicDBObject2).remove();
		bulkWriteOperation.execute();

	}

}