package database;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class DbConnection {
	String DbName;
	public static DB db;
	public static MongoClient mongoClient;

	public static void makeConnection() {
		/*mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDB("Test");*/
		mongoClient = new MongoClient( "localhost" , 27017 );  
		db = mongoClient.getDB("TEST");

	}

	public	static DB getDb() {
		return db;
	}

	public static MongoClient getMongoClient() {
		return mongoClient;
	}

	public static boolean stopMongoClient() {
		if (mongoClient != null)
			mongoClient.close();
		return true;

	}
}
