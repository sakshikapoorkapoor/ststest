package database;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.DBCollection;

import parser.AvlPojo;
import parser.GetGMTDateTime;

public class InsertToDB {

	public void insertpollData(AvlPojo avlpojo) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		float f = 30.78f;
		
		try {
			if (DbConnection.getMongoClient() == null || DbConnection.getDb() == null) {
				DbConnection.makeConnection();
				System.out.println("makeConnection");
			}
			DBCollection table = DbConnection.getDb().getCollection("poll_data");
			BasicDBObject document = new BasicDBObject();
			document.put("wire_locked", "");
			document.put("steel_wire_cut_off", "");
			document.put("battery_percentage", "");
			document.put("power_mode", "");

			document.put("transID", "" + System.currentTimeMillis());
			document.put("device_no", avlpojo.getImei());

			document.put("long", avlpojo.getLongitude());
			System.out.println("DB avlpojo: " + avlpojo.getLongitude());
			
			document.put("lat", avlpojo.getLatitude());
			document.put("collision", false);

			document.put("speed", avlpojo.getSpeed_kph());
			document.put("odo", avlpojo.getMile_meter());
			document.put("heading", avlpojo.getHeading());

			if (avlpojo.getSpeed_kph() > 0) {
				document.put("ignition", true); // ignition
			} else {
				document.put("ignition", false); // ignition
			}
			document.put("ac_status", false);
			document.put("driver_panic", false);
			if (avlpojo.getAlarmtype()!= null) {
				if (avlpojo.getAlarmtype().equals("SOS button pressed")) {
					document.put("sos", true);
				} else {
					document.put("sos", false);
				}
			}
	
			document.put("imobilize", false);
			document.put("buzzer", false);
			document.put("cutoff", true); // Cut Off is reverse
			document.put("fuel_level", 0);
			document.put("input2", false);
			document.put("output3", false);
			document.put("output4", false);
			document.put("output5", false);
			document.put("case_tempring", false);
			document.put("battery_tempring", false);
			document.put("harness_removel", false);
			document.put("analog2", false);
			//GetGMTDateTime device_datetime = new GetGMTDateTime(Timestamp.valueOf(dateFormat.format(avlpojo.getFormated_device_datetime())).toString());
			document.put("device_datetime", avlpojo.getFormated_device_datetime());
			System.out.println("DB: "+avlpojo.getFormated_device_datetime());

			
			Date date = new Date();
			GetGMTDateTime gmt = new GetGMTDateTime(Timestamp.valueOf(dateFormat.format(date)).toString());
			document.put("received_datetime", gmt.getDate());

			document.put("battery_status", avlpojo.getCharge_status());

			document.put("lbs", "No LBS");
			document.put("gsm_rssi", "");
			document.put("gps_satellite", "");
			document.put("is_gsm", false);
			document.put("mobile_data", "false");
			document.put("is_sim_removed", false);
			document.put("odometer_corrupt", false);
			document.put("patch_applied", false);

			document.put("accl_connection_status", "NA");
			document.put("accl_alert_source", "NA");
			document.put("accl_axis_X", "NA");
			document.put("accl_axis_Y", "NA");
			document.put("accl_axis_Z", "NA");
			document.put("firmware_version", "");
			document.put("hardware_version", "");
			document.put("packet_type", "");
			document.put("mode", "");
			document.put("vehicle_battery_level", "");
			document.put("from_speed", 0);
			document.put("to_speed", 0);
			document.put("count_speed", 0);
			document.put("device", "TVL");

			document.put("status", "PENDING");
			table.insert(document);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
