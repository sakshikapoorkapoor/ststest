package parser;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;


import database.DbConnection;

public class TeltonikaFMB001Parse {
	public static void main(String args[]) {
		// 000000000000008c08010000013feb55ff74000f0ea850209a690000940000120000001e09010002000300040016014703f0001504c8000c0900730a00460b00501300464306d7440000b5000bb60007422e9f180000cd0386ce000107c700000000f10000601a46000001344800000bb84900000bb84a00000bb84c00000000024e0000000000000000cf00000000000000000100003fca
		String s = "000000000000008c08010000013feb55ff74000f0ea850209a690000940000120000001e09010002000300040016014703f0001504c8000c0900730a00460b00501300464306d7440000b5000bb60007422e9f180000cd0386ce000107c700000000f10000601a46000001344800000bb84900000bb84a00000bb84c00000000024e0000000000000000cf00000000000000000100003fca";
		TeltonikaFMB001Parse teltonikaFMB001Parse = new TeltonikaFMB001Parse();
		//teltonikaFMB001Parse.dataParse(s);
		teltonikaFMB001Parse.insertData(teltonikaFMB001Parse.dataParse(s));
	}

	public TeltonikaDevicePojo[] dataParse(String data) {
		String dataLength = data.substring(8, 16);
		String codecID = data.substring(16, 18);
		int number_ofData = Integer.parseInt(data.substring(18, 20));
		TeltonikaDevicePojo[] array = new TeltonikaDevicePojo[number_ofData];
		int nextDataIndex = 20;
		System.out.println("dataLength " + dataLength + " codecID " + codecID + " number_ofData " + number_ofData);
		for (int i = 0; i < number_ofData; i++) {
			try {
				TeltonikaDevicePojo teltonikaDevicePojo = new TeltonikaDevicePojo();
				
				String timeStamp = data.substring(nextDataIndex, nextDataIndex + 16);
				Date deviceDateTime = new Date(Long.parseLong(timeStamp, 16));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				teltonikaDevicePojo.setDevice_datetime(deviceDateTime);
				
				System.out.println(" timeStamp " + timeStamp);
				String priority = data.substring(nextDataIndex + 16, nextDataIndex + 16 + 2);
				String gpsData = data.substring(nextDataIndex + 16 + 2, nextDataIndex + 16 + 2 + 30);

				String lon = gpsData.substring(0, 8);
				lon = "" + Long.parseLong(lon, 16);
				if (lon.equalsIgnoreCase("0")) {
					teltonikaDevicePojo.setLbs(true);
					teltonikaDevicePojo.setLon(0.0d);
				} else {
					lon = lon.substring(0, 2) + "." + lon.substring(2);
					double longitude = Double.parseDouble(lon);
					teltonikaDevicePojo.setLon(longitude);
				}
				String lat = gpsData.substring(8, 16);
				lat = "" + Long.parseLong(lat, 16);

				if (lat.equalsIgnoreCase("0")) {
					teltonikaDevicePojo.setLbs(true);
					teltonikaDevicePojo.setLat(0.0d);
				} else {
					lat = lat.substring(0, 2) + "." + lat.substring(2);
					double latitude = Double.parseDouble(lat);
					teltonikaDevicePojo.setLat(latitude);
				}
				String altitudeStr = gpsData.substring(16, 20);
				int altitude = Integer.parseInt(altitudeStr, 16);
				
				teltonikaDevicePojo.setAltitude(altitude);

				String angle = gpsData.substring(20, 24);
				int heading = Integer.parseInt(angle, 16);
				teltonikaDevicePojo.setHeading(Double.parseDouble("" + heading));
				
				String statellite = gpsData.substring(24, 26);
				int setteliteCount = Integer.parseInt(statellite, 16);
				teltonikaDevicePojo.setVisible_satellite("" + setteliteCount);
				
				String speedStr = gpsData.substring(26, 30);
				int speed = Integer.parseInt(speedStr, 16);
				teltonikaDevicePojo.setSpeed(Double.parseDouble("" + speed));
				
				String IOelement_ID = data.substring(nextDataIndex + 16 + 2 + 30, nextDataIndex + 16 + 2 + 30 + 2);
				int totalIO = Integer.parseInt(
						data.substring(nextDataIndex + 16 + 2 + 30 + 2, nextDataIndex + 16 + 2 + 30 + 2 + 2), 16);
				int oneByteDataSize = Integer.parseInt(
						data.substring(nextDataIndex + 16 + 2 + 30 + 2 + 2, nextDataIndex + 16 + 2 + 30 + 2 + 2 + 2),
						16);
                int oneByteDataStartpoint = nextDataIndex + 16 + 2 + 30 + 2 + 2 + 2;
                int _1ByteDataStart = oneByteDataStartpoint + 2;
				for(int j=0;j<oneByteDataSize;j++)
				{
					/*int id = Integer.parseInt(data.substring(oneByteDataStartpoint,oneByteDataStartpoint+2),16);
					String value =data.substring(oneByteDataStartpoint+2,oneByteDataStartpoint+2+2);*/
					int id = Integer.parseInt(data.substring(oneByteDataStartpoint, oneByteDataStartpoint + 2), 16);
					String value =data.substring(_1ByteDataStart, _1ByteDataStart + 2);
					System.out.println("id "+id +" value "+value);
					read_1_ByteDataProperty(
							id,
							value, teltonikaDevicePojo, data);
					oneByteDataStartpoint = oneByteDataStartpoint+4;
					_1ByteDataStart = _1ByteDataStart + 4;

				}
				nextDataIndex = nextDataIndex + 16 + 2 + 30 + 2 + 2 + 2 +(oneByteDataSize*4);
				int twoByteDataSize = Integer
						.parseInt(data.substring(nextDataIndex, nextDataIndex + 2), 16);
				System.out.println("twoByteDataSize "+twoByteDataSize);
				int _2ByteStartPoint = nextDataIndex + 2;
				int _2ByteDataStart = _2ByteStartPoint + 2;
				
				for (int ii = 0; ii < twoByteDataSize; ii++) {
					int id = Integer.parseInt(data.substring(_2ByteStartPoint, _2ByteStartPoint + 2), 16);
					String value =data.substring(_2ByteDataStart, _2ByteDataStart + 4);
					System.out.println("2id "+id +" value "+value);
					read_2_ByteDataProperty(
							Integer.parseInt(data.substring(_2ByteStartPoint, _2ByteStartPoint + 2), 16),
							data.substring(_2ByteDataStart, _2ByteDataStart + 4), teltonikaDevicePojo, data);

					_2ByteStartPoint = _2ByteStartPoint + 6;
					_2ByteDataStart = _2ByteDataStart + 6;

				}
				nextDataIndex = nextDataIndex+ 2 +(twoByteDataSize*6);
				int fourByteDataSize = Integer
						.parseInt(data.substring(nextDataIndex, nextDataIndex + 2), 16);
				int _4ByteStartPoint = nextDataIndex + 2;
				int _4ByteDataStart = _4ByteStartPoint + 2;
				for (int ii = 0; ii < fourByteDataSize; ii++) {
					int id = Integer.parseInt(data.substring(_4ByteStartPoint, _4ByteStartPoint + 2), 16);
					String value = data.substring(_4ByteDataStart, _4ByteDataStart + 8);
					System.out.println("4id "+id +" value "+value);
					read_4_ByteDataProperty(
							Integer.parseInt(data.substring(_4ByteStartPoint, _4ByteStartPoint + 2), 16),
							data.substring(_4ByteDataStart, _4ByteDataStart + 8), teltonikaDevicePojo, data);

					_4ByteStartPoint = _4ByteStartPoint + 10;
					_4ByteDataStart = _4ByteDataStart + 10;

				}
				
				if (fourByteDataSize == 0) {
					nextDataIndex = (nextDataIndex + 2);
					// because all code is already made on this index
					nextDataIndex = nextDataIndex - 2;
				} else {
					nextDataIndex = (nextDataIndex + 2) + (fourByteDataSize * 10);
					// because all code is already made on this index
					nextDataIndex = nextDataIndex - 2;
				}
				array[i] = teltonikaDevicePojo;
				System.out.println(" priority " + priority + " gpsData " + gpsData + " gpsData " + gpsData
						+ " nextDataIndex " + nextDataIndex + " lon " + lon + " lat " + lat + "altitude" + altitude
						+ " speed " + speed + " totalIOelement " + totalIO +" oneByteDataSize "+oneByteDataSize);
				nextDataIndex = nextDataIndex + nextDataIndex + 16 + 2 + 30;
			} catch (Exception e) {

			}
		}

		return array;

	}
	public void read_1_ByteDataProperty(int id, String data, TeltonikaDevicePojo pojo, String packet) {
		switch (id) {
		case 1:
			//Print.logInfo(""+pojo.getDeviceNo()+", Teltonika digital Input 1: "+data+" ,"+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
		case 239:
			// Ignition

			if (data.equalsIgnoreCase("01")) {
				//Print.logInfo(""+pojo.getDeviceNo()+", Teltonika device Ignition ON, "+pojo.getDevice_datetime()+", Packet: "+packet);
				pojo.setIgnition(true);
			} else {
				pojo.setIgnition(false);
			}
			break;
		case 21:
			// GSM Strength
			pojo.setGsm_strength(Integer.parseInt(data));
			break;
		case 255:
			// OverSpeed
			//Print.logInfo(pojo.getDeviceNo()+", TeltonikaFMB001 Overspeeding: "+data+", Packet: "+packet);
			break;
		case 253:
			// Driving
			if (data.equalsIgnoreCase("01")) {
				//Print.logInfo(pojo.getDeviceNo()+", Driving Harsh Acce, "+", "+pojo.getDevice_datetime()+", Packet: "+packet);
				pojo.setHarshAcc(true);
			}if (data.equalsIgnoreCase("02")) {
				//Print.logInfo(pojo.getDeviceNo()+", TeltonikaFMB001 Driving Harsh Braking, "+", "+pojo.getDevice_datetime()+", Packet: "+packet);
//				Print.logInfo("\n\n"+pojo.getDeviceNo()+",Driving Harsh Breaking\n\n");
				pojo.setHarshBraking(true);
			}if (data.equalsIgnoreCase("03")) {
				//Print.logInfo(pojo.getDeviceNo()+", TeltonikaFMB001 Driving Zig Zag, "+", "+pojo.getDevice_datetime()+", Packet: "+packet);
//				Print.logInfo("\n\n"+pojo.getDeviceNo()+", Driving Zig Zag \n\n");
				pojo.setZigZagDriving(true);
			} else {
			}

			break;
		case 251:
			// Idling
			//Print.logInfo(pojo.getDeviceNo()+", TeltonikaFMB001 Immobilizer: "+data+", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
		case 247:
			// Collision
			//Print.logInfo(pojo.getDeviceNo()+", TeltonikaFMB001 Collision Data received: " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			if (data.equalsIgnoreCase("01")) {
//				Print.logInfo(pojo.getDeviceNo()+", Teltonika Collision Data received: " + data + ", "+pojo.getDevice_datetime());
				pojo.setCollision(true);
			}
			
//			if (data.equalsIgnoreCase("02")) {
////				pojo.setCollision(true);
//			}
			
			else {
				pojo.setCollision(false);
			}
			break;
		case 252:
			// Un plug
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Cut off: "+data+", "+pojo.getDevice_datetime()+", Packet: "+packet);
			if (data.equalsIgnoreCase("01")) {
				
				pojo.setCutoff(0);
			} else {
				pojo.setCutoff(1);
			}
			break;
		case 246:
			//Print.logInfo(pojo.getDeviceNo()+", TeltonikaFMB001 Towing: "+data+", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
		case 249:
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Jamming: "+data+", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
			
		case 155:
			// Odometer
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Geofence 1 Event : " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
			
		case 156:
			// Odometer
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Geofence 2 Event : " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
			
		case 157:
			// Odometer
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Geofence 3 Event : " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
			
		case 158:
			// Odometer
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Geofence 4 Event : " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
			
		case 159:
			// Odometer
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Geofence 5 Event : " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
			
		case 175:
			// Odometer
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 AutoGeofence Event : " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
			
		case 250:
			// Odometer
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Trip Event : " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;	
		default:
			break;
		}
	}
	public void read_2_ByteDataProperty(int id, String data, TeltonikaDevicePojo pojo, String packet) {
	switch (id) {
		
		case 67:
			// Ignition
			pojo.setInternal_batt_voltage(Float.parseFloat("" + Integer.parseInt(data, 16) + ".0"));
			break;

		case 13:
			// Fuel
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Average Fuel used: " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			
			break;
			
		case 24:
			// Speed in IO
			//Print.logInfo(pojo.getDeviceNo() + ",TeltonikaFMB001 Speed Data in IO: " + data + ", Packet: "+packet);
			break;
			
		case 9:
			//Analog input 1
			//Print.logInfo(pojo.getDeviceNo() + ",TeltonikaFMB001 Analog input 1: " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;
			
		case 205:
			// CID
			//Print.logInfo(pojo.getDeviceNo() + ",TeltonikaFMB001 CID Data received: " + data+" , "+pojo.getDevice_datetime()+", Packet: "+packet);
			pojo.setCID(data);
			break;
			
		case 206:
			// LAC
			pojo.setLAC(data);
			//Print.logInfo(pojo.getDeviceNo() + ",TeltonikaFMB001 LAC Data received: " + data+" , "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;

		default:
			break;
		}
	}
	public void read_4_ByteDataProperty(int id, String data, TeltonikaDevicePojo pojo, String packet) {

		switch (id) {
		case 241:
			pojo.setInternal_batt_voltage(Float.parseFloat("" + Integer.parseInt(data, 16) + ".0"));
			break;
			
		case 12:
			// Odometer
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Fuel used GPS: " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			
			break;

		case 16:
			// Odometer
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Total Odometer received: " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			pojo.setOdometer(Double.parseDouble("" + Integer.parseInt(data, 16)));
			break;
		case 199:
			//Print.logInfo(pojo.getDeviceNo()+",TeltonikaFMB001 Trip Odometer received: " + data + ", "+pojo.getDevice_datetime()+", Packet: "+packet);
			break;

		default:
			break;
		}
	}
	public void insertData(TeltonikaDevicePojo[] array) {
		System.out.println("array "+array.length);
		if (array != null) {
			try {
				if (DbConnection.getMongoClient() == null || DbConnection.getDb() == null) {
					DbConnection.makeConnection();
					System.out.println("makeConnection");
				}
				DBCollection table = DbConnection.getDb().getCollection("poll_data");

				for (int i = 0; i < array.length; i++) {

					TeltonikaDevicePojo tgs = array[i];
					if (tgs != null) {
//						Print.logInfo("In loop insert Teltonika time: " + tgs.getDevice_datetime());
//						if (tgs.getLat() > 0 && tgs.getLon() > 0) {

							//DBCollection table = MongoDBConnection.getDB().getCollection("tbl_poll_data");
							BasicDBObject document = new BasicDBObject();

							document.put("wire_locked", tgs.getWireLock());
							document.put("steel_wire_cut_off", "");
							document.put("battery_percentage", "");
							document.put("power_mode", "");
							document.put("imei", tgs.getImei());
							document.put("transID", "" + System.currentTimeMillis());
							document.put("device_no", tgs.getDeviceNo());
							document.put("long", tgs.getLon());
							document.put("collision", tgs.isCollision());
							document.put("lat", tgs.getLat());
							// //System.out.println("Latitute>" +
							// tgs.getLatitude());
							document.put("speed", tgs.getSpeed());
							// //System.out.println("true2");
							document.put("odo", tgs.getOdometer());

							document.put("heading", tgs.getHeading());
							document.put("ignition", tgs.isIgnition()); // ignition
							// //System.out.println("9906 insert stats 3 ");
							document.put("ac_status", tgs.isAc()); // AC
																	// on
							document.put("driver_panic", tgs.isPanic()); // panicbutton
							document.put("sos", tgs.isPanic());
							// //System.out.println("true3");
							document.put("imobilize", tgs.isImmobilize());
							// Print.logInfo("Immobilizer>"+tgs.isImmobilized());
							document.put("buzzer", false);
							// Print.logInfo("Panic>"+tgs.getInput3());
							document.put("cutoff", tgs.getCutoff());
							// Print.logInfo("Cut off
							// alarm>"+tgs.getCutOffAlarm());
							document.put("fuel_level", tgs.getFuel());
							// Print.logInfo("Fuel>"+tgs.getFuelLevel());
							document.put("input2", false);
							document.put("output3", false);
							document.put("output4", false);
							document.put("output5", false);
							document.put("case_tempring", tgs.isBox_status());
							document.put("battery_tempring", tgs.getBattery_removed());
							document.put("harness_removel", false);
							// //System.out.println("true4");
							document.put("analog2", 0);

							Calendar cal = Calendar.getInstance();
							cal.setTime(tgs.getDevice_datetime());
							cal.add(Calendar.HOUR, +5);
							cal.add(Calendar.MINUTE, +30);
							Date timeDate = cal.getTime();
							document.put("device_datetime", timeDate);

							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date = new Date();
							GetGMTDateTime gmt = new GetGMTDateTime(
									Timestamp.valueOf(dateFormat.format(date)).toString());
							document.put("received_datetime", gmt.getDate());

							document.put("battery_status", tgs.getInternal_batt_voltage());
							// document.put("device_battery_voltage",
							// tgs.getInternal_batt_voltage());

							document.put("lbs", tgs.getLbsString());
							document.put("gsm_rssi", tgs.getGsm_strength());
							document.put("gps_satellite", "");
							document.put("is_gsm", tgs.isLbs());
							document.put("mobile_data", "false");
							document.put("is_sim_removed", false);
							document.put("odometer_corrupt", false);

							document.put("patch_applied", false);

							document.put("accl_connection_status", "");
							document.put("accl_alert_source", "");
							document.put("accl_axis_X", "");
							document.put("accl_axis_Y", "");
							document.put("accl_axis_Z", "");
							document.put("firmware_version", "");
							document.put("hardware_version", "");
							document.put("packet_type", "");
							document.put("mode", "");

							document.put("vehicle_battery_level", "");
							document.put("from_speed", 0);
							document.put("to_speed", 0);
							document.put("count_speed", 0);
							document.put("device", "TeltonikaFMB001");

							document.put("status", "PENDING");
							document.put("harsh_acc", tgs.isHarshAcc());
							document.put("harsh_braking", tgs.isHarshBraking());
							document.put("zig_zag_driving", tgs.isZigZagDriving());

							table.insert(document);

						} else {
							//Print.logInfo("Data not inserted Lat Long 0 teltonika: " + tgs.getDeviceNo());
							// //System.out.println("Lat long 0 received");
						}
					}
//				}

			} catch (Exception ec) {
				ec.printStackTrace();
				//System.out.println("ex: " + ec);
			}
		} else {
			//Print.logInfo("Data received null while inserting in teltonika");
		}

	}
}
