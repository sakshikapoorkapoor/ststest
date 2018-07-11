package main;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import database.DbConnection;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String data = "$$My|name|is|sakshi|kapoor#";
		if (data.startsWith("$$") && data.endsWith("#")) {
			int end_index = data.indexOf("#");
			// int start_index = data.lastIndexOf("$");
			String actual_data = data.substring(2, end_index);
			System.out.println(actual_data);
			 String[] result = actual_data.split("\\|");

			   /* for (String s : result) {
			        System.out.println(s);
			    }*/
			    for (int i=0;i<result.length;i++) {
			        System.out.println(result[i]);
			    
			    }
			    Date date = formatTime("20120613024927");
			    System.out.println();
				if (DbConnection.getMongoClient() == null || DbConnection.getDb() == null) {
					DbConnection.makeConnection();
					System.out.println("makeConnection");
				}
				DBCollection collection = DbConnection.getDb().getCollection("col_data");
				BasicDBObject basicDBObject = new BasicDBObject();
				basicDBObject.put("4", "jkkk");
			List list = collection.distinct("4");
			
			System.out.println("len: "+list.size());
		}
		
	}
	public static Date formatTime(String rtc)// ( RTC, "YYYY:MM:DD:hh:mm:ss" )
	{
		try {
			String formated = rtc.substring(0, 4) + "-" + rtc.substring(4, 6) + "-" + rtc.substring(6, 8) + " "
					+ rtc.substring(8, 10) + ":" + rtc.substring(10, 12) + ":" + rtc.substring(12, 14);
			Calendar cal = Calendar.getInstance();
			cal.setTime(Timestamp.valueOf(formated));
			cal.add(Calendar.HOUR, +5);
			cal.add(Calendar.MINUTE, +30);
			Date timeDate = cal.getTime();
			return timeDate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
