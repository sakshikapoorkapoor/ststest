package parser;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.transform.sax.SAXTransformerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;


import database.DbConnection;


public class GpsCardParser {

	/*public static void main(String srgs[])
	{
		GpsCardParser parser = new GpsCardParser();
		parser.parsedata("*357653050463418*IWAP01171130A2830.2304N07704.1311E029.4083810118.7110000509900000,404,10,520,18366# ");
		parser.insertaData(parser.parsedata("*357653050463418*IWAP01171130A2830.2304N07704.1311E029.4083810118.7110000509900000,404,10,520,18366# "));
	}*/

	
	
	public GpsCardPojo parsedata(String data) {
		GpsCardPojo gpscardPojo = new GpsCardPojo();
		// String commandword = data.substring(2, 6);
		String commandword = data.substring(data.indexOf("AP"), data.indexOf("AP") + 4);
		System.out.println("data-----------------------" + commandword);
		switch (commandword) {
		case "AP00":
			// data = "IWAP00353456789012345";
			// String imei = data.substring(6);
			String imei = data.substring(1, data.indexOf("IWAP") - 1);
			gpscardPojo.setDeviceNo(imei);
			break;
		case "AP01":
			locatingPackage(data, gpscardPojo);
			break;
		case "AP02":
			/*String imei1 = data.substring(1, data.indexOf("IWAP") - 1);
			gpscardPojo.setDeviceNo(imei1);*/
			multipleBasesLoctingPackage(data, gpscardPojo);
			break;

		case "AP10":
			alarmAndReturnAddressPackage(data, gpscardPojo);
			break;
		case "AP03":
			heartbeatPackage(data, gpscardPojo);
			break;
		case "AP04":
			String battery_level = data.substring(data.indexOf("IW"));
			System.out.println("battery_level " + battery_level);
			gpscardPojo.setBattery_level(battery_level);
			break;
		case "AP53":
			String str[] = data.split(",");
			String MCC = str[1];
			String MNC = str[2];
			String LAC = str[3];
			String CID = str[4];
			System.out.println("MCC  " + MCC + " MNC " + MNC + " CID " + CID);
			gpscardPojo.setMCC(MCC);
			gpscardPojo.setMNC(MNC);
			gpscardPojo.setLAC(LAC);
			gpscardPojo.setCID(CID);
			break;
		default:
			break;
		}
		insertaData(gpscardPojo);
		return gpscardPojo;
	}

	void loginPackage(String data) {
		// IWAP00353456789012345
		String imei = data.substring(6);

	}

	void locatingPackage(String data, GpsCardPojo gpsCardPojo) {
		// data =
		// "IWAP01080524A2232.9806N11404.9355E000.1061830323.8706000908000102,460,0,9520,3671,Home|74-DE-2B-44-88-8C|97&
		// Home1|74-DE-2B-44-88-8C|97&Home2|74-DE-2B-44-88-8C|97&
		// Home3|74-DE-2B-44-88-8C|97";
		try {
			// System.out.println("gps_data-------------"+data);
			DecimalFormat df2 = new DecimalFormat(".####");
			String str[] = data.split(",");
			String imeiData = str[0];
			String imei = imeiData.substring(1, imeiData.indexOf("IWAP") - 1);
			gpsCardPojo.setDeviceNo(imei);

			String gps_data = str[0].substring(str[0].indexOf("IWAP"));
			System.out.println("gps_data-------------" + gps_data);
			String strDate = gps_data.substring(6, 12);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

			String dataValid = gps_data.substring(12, 13);
			Boolean getLbsData;
			if (dataValid.equals("A")) {
				getLbsData = false;
			} else {
				getLbsData = true;
			}

			String latRaw = gps_data.substring(13, 22);
			String lonRaw = gps_data.substring(23, 33);
			System.out.println("latRaw------------" + latRaw);
			if (latRaw.equals("0000.0000") || lonRaw.equals("00000.0000")) {
				gpsCardPojo.setLbs(true);
				gpsCardPojo.setLatitude(0.0d);
				gpsCardPojo.setLongitude(0.0d);
			} else {
				if (latRaw.length() > 5 && latRaw.indexOf(".") == 4 && lonRaw.length() >= 5
						&& lonRaw.indexOf(".") == 5) {
					int temp = Integer.parseInt(latRaw.substring(0, 2), 10);
					double temp1 = Double.parseDouble(latRaw.substring(2));
					double latitude = (double) temp + temp1 / 60;
					// Print.logInfo (" Latitude-GPSSTATUS: "+latitude);
					System.out.println("Lat: " + latitude);

					double lat = Double.parseDouble(df2.format(latitude));
					gpsCardPojo.setLatitude(lat);

					temp = Integer.parseInt(lonRaw.substring(0, 3), 10);
					temp1 = Double.parseDouble(lonRaw.substring(3));
					double longitude = (double) temp + temp1 / 60;
					System.out.println("lon: " + longitude);
					double lon = Double.parseDouble(df2.format(longitude));
					gpsCardPojo.setLongitude(lon);
				}
			}
			System.out.println("lonRaw------------" + lonRaw);
			String strspeed = gps_data.substring(34, 39);
			double speed = Double.valueOf(strspeed);
			gpsCardPojo.setSpeed(speed);
			String time_gmt = gps_data.substring(39, 45);
			String dateTime = strDate.substring(0, 2) + "-" + strDate.substring(2, 4) + "-" + strDate.substring(4) + " "
					+ time_gmt.substring(0, 2) + ":" + time_gmt.substring(2, 4) + ":" + time_gmt.substring(4);
			System.out.println("dateTime " + dateTime);
			Date dateTme = dateFormat.parse(dateTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateTme);
			calendar.add(Calendar.HOUR, 5);
			calendar.add(Calendar.MINUTE, 30);
			SimpleDateFormat newdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			GetGMTDateTime gmt = new GetGMTDateTime(Timestamp.valueOf(newdateFormat.format(calendar.getTime())).toString());
			// gpsCardPojo.setDevice_datetime(dateTme);
			// Date deviceDateTime = new Date(Long.parseLong(dateTime, 16));
			gpsCardPojo.setDevice_datetime(gmt.getDate());
			System.out.println("Device_datetime--------------- " + gpsCardPojo.getDevice_datetime());
			String direction_angle = gps_data.substring(45, 48);
			int headingint = Integer.parseInt(direction_angle, 16);
			double heading = Double.valueOf(headingint);
			gpsCardPojo.setHeading(heading);
			String gsm_signal = gps_data.substring(51, 54);
			gpsCardPojo.setGsm_signal(gsm_signal);
			String setteliteCountStr = gps_data.substring(54, 57);
			int setteliteCount = Integer.parseInt(setteliteCountStr, 16);
			gpsCardPojo.setSatelliteCount(setteliteCount);
			String battery_level = gps_data.substring(57, 60);
			gpsCardPojo.setBattery_level(battery_level.substring(1));
			String fortification = gps_data.substring(61, 63);
			gpsCardPojo.setFortification(fortification);
			String workingMode = gps_data.substring(63, 65);
			gpsCardPojo.setWorkingMode(workingMode);
			String MCC = str[1];
			String MNC = str[2];
			String LAC = str[3];
			String CID = str[4];
			gpsCardPojo.setMCC(MCC);
			gpsCardPojo.setMNC(MNC);
			gpsCardPojo.setLAC(LAC);
			gpsCardPojo.setCID(CID);
			/*if (gpsCardPojo.isLbs()) {

				if (gpsCardPojo.getLAC() != null && gpsCardPojo.getCID() != null) {
					String lbsData = "0~0~" + gpsCardPojo.getLAC() + "~" + gpsCardPojo.getCID();
					gpsCardPojo.setLbsString(lbsData);

					Util util = new Util();
					double latlon[] = util.getLBSToLatLong(lbsData, data, gpsCardPojo.getDeviceNo());
					if (latlon != null) {
						gpsCardPojo.setLatitude(latlon[0]);
						gpsCardPojo.setLongitude(latlon[1]);
						Print.logInfo("GPS card LBS data calculated: " + latlon[0] + " , " + latlon[1]);
					}
				} else {
					gpsCardPojo.setLbsString("");
				}
			} else {
				gpsCardPojo.setLbsString("");
			}*/
			//String wifiInformation = str[5];
			System.out.println("gsm_signal " + gsm_signal + "satellite  " + setteliteCountStr + " battery_level "
					+ battery_level + " fortification " + fortification + " workingMode " + workingMode + " heading "
					+ direction_angle);
		/*	if (wifiInformation.indexOf("&") != -1) {
				String[] arr_WifiInformation = wifiInformation.split("&");
				for (int i = 0; i < arr_WifiInformation.length; i++) {
					String[] inerWifiInformation = wifiInformation.split("\\|");
					String SSID = inerWifiInformation[0];
					String MAC_address = inerWifiInformation[1];
					String signal_strength = inerWifiInformation[2];
				}
			} else {
				String[] inerWifiInformation = wifiInformation.split("\\|");
				String SSID = inerWifiInformation[0];
				String MAC_address = inerWifiInformation[1];
				String signal_strength = inerWifiInformation[2];
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/*	void multipleBasesLoctingPackage(String data, GpsCardPojo gpsCardPojo) {
		String[] str = data.split(",");
		String language_notice = str[1];
		String reply_notice = str[2];
		String basis_sets = str[3];
		String country_code = str[4];
		gpsCardPojo.setCountry_code(country_code);
		String operator_code = str[5];
		int nextindex = 6;
		for (int i = 0; i < Integer.parseInt(basis_sets); i++) {
			String baseInformation = str[nextindex];
			String arr_baseInformation[] = baseInformation.split("\\|");
			String LAC = arr_baseInformation[0];
			String CID = arr_baseInformation[1];
			String dbm = arr_baseInformation[2];
			nextindex++;
			System.out.println("LAC  " + LAC + "CID " + CID + "dbm " + dbm);

		}
		String setOfWifi = str[nextindex];

		String wifiInfo = str[nextindex + 1];
		String arr_wifiinfo[] = wifiInfo.split("&");
		for (int i = 0; i < arr_wifiinfo.length; i++) {
			String inner = arr_wifiinfo[i];
			String arr[] = inner.split("\\|");
			String ssid = arr[0];
			String mac_address = arr[1];
			String signal_strength = arr[2];

			System.out.println("ssid  " + ssid + "mac_address " + mac_address + "signal_strength " + signal_strength);
		}
	}*/
	void multipleBasesLoctingPackage(String data, GpsCardPojo gpsCardPojo) {
		
		String[] str = data.split(",");
		String imeiData = str[0];
		String imei = imeiData.substring(1, imeiData.indexOf("IWAP") - 1);
		gpsCardPojo.setDeviceNo(imei);
		String language_notice = str[1];
		String reply_notice = str[2];
		String basis_sets = str[3];
		String MCC = str[4];
		gpsCardPojo.setMCC(MCC);
		String MNC = str[5];
		gpsCardPojo.setMNC(MNC);
		int nextindex = 6;
		for (int i = 0; i < Integer.parseInt(basis_sets); i++) {
			String baseInformation = str[nextindex];
			String arr_baseInformation[] = baseInformation.split("\\|");
			String LAC = arr_baseInformation[0];
			String CID = arr_baseInformation[1];
			String dbm = arr_baseInformation[2];
			nextindex++;
			System.out.println("getDeviceNo "+gpsCardPojo.getDeviceNo()+"MCC " + MCC + "LAC  " + LAC + "CID " + CID + "dbm " + dbm);
			gpsCardPojo.setLAC(LAC);
			gpsCardPojo.setCID(CID);
		
		}
		gpsCardPojo.setLbs(true);
		gpsCardPojo.setLatitude(0.0d);
		gpsCardPojo.setLongitude(0.0d);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		GetGMTDateTime gmt = new GetGMTDateTime(Timestamp.valueOf(dateFormat.format(date)).toString());
		gpsCardPojo.setDevice_datetime(gmt.getDate());
		if (gpsCardPojo.isLbs()) {

			if (gpsCardPojo.getLAC() != null && gpsCardPojo.getCID() != null) {
				try {/*
					String lbsData = gpsCardPojo.getMCC() + "~" + gpsCardPojo.getMNC() + "~" + gpsCardPojo.getLAC()
							+ "~" + gpsCardPojo.getCID();
					gpsCardPojo.setLbsString(lbsData);
					System.out.println("lbsData: " + lbsData);
					Util util = new Util();
					double latlon[] = util.getLBSToLatLong(lbsData, data, gpsCardPojo.getDeviceNo());
					if (latlon != null) {
						gpsCardPojo.setLatitude(latlon[0]);
						gpsCardPojo.setLongitude(latlon[1]);
						Print.logInfo("GPS card LBS data calculated: " + latlon[0] + " , " + latlon[1]);
					}
				*/} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				gpsCardPojo.setLbsString("");
			}
		} else {
			gpsCardPojo.setLbsString("");
		}
		System.out.println("getDeviceNo "+gpsCardPojo.getDeviceNo()+"MCC " + gpsCardPojo.MCC + "LAC  " + gpsCardPojo.getLAC() + "CID " + gpsCardPojo.getCID() +"tgs.getSatellite()"+ gpsCardPojo.getSatellite() +"gpsCardPojo.getSpeed() "+gpsCardPojo.getSpeed()+"gpsCardPojo.getHeading() "+gpsCardPojo.getHeading()+"getDevice_datetime "+gpsCardPojo.getDevice_datetime());
		/*
		 * String setOfWifi = str[nextindex];
		 * 
		 * String wifiInfo = str[nextindex + 1]; String arr_wifiinfo[] =
		 * wifiInfo.split("&"); for (int i = 0; i < arr_wifiinfo.length; i++) {
		 * String inner = arr_wifiinfo[i]; String arr[] = inner.split("\\|");
		 * String ssid = arr[0]; String mac_address = arr[1]; String
		 * signal_strength = arr[2];
		 * 
		 * System.out.println("ssid  " + ssid + "mac_address " + mac_address +
		 * "signal_strength " + signal_strength); }
		 */
	}

	void alarmAndReturnAddressPackage(String data, GpsCardPojo gpsCardPojo) {
		try {
			DecimalFormat df2 = new DecimalFormat(".####");
			String str[] = data.split(",");
			String imeiData = str[0];
			String imei = imeiData.substring(1, imeiData.indexOf("IWAP") - 1);
			gpsCardPojo.setDeviceNo(imei);
			String gpsData = str[0].substring(str[0].indexOf("IWAP"));
			String strDate = gpsData.substring(6, 12);
			String dataValid = gpsData.substring(12, 13);
			Boolean getLbsData;
			if (dataValid.equals("A")) {
				getLbsData = false;
			} else {
				getLbsData = true;
			}
			System.out.println("date  " + strDate + " dataValid  " + dataValid);
			String latRaw = gpsData.substring(13, 22);
			String lonRaw = gpsData.substring(23, 33);
			if (latRaw.equals("0000.0000") || lonRaw.equals("00000.0000")) {
				gpsCardPojo.setLbs(true);
				gpsCardPojo.setLatitude(0.0d);
				gpsCardPojo.setLongitude(0.0d);
			} else {
				if (latRaw.length() > 5 && latRaw.indexOf(".") == 4 && lonRaw.length() >= 5
						&& lonRaw.indexOf(".") == 5) {
					int temp = Integer.parseInt(latRaw.substring(0, 2), 10);
					double temp1 = Double.parseDouble(latRaw.substring(2));
					double latitude = (double) temp + temp1 / 60;
					// Print.logInfo (" Latitude-GPSSTATUS: "+latitude);
					System.out.println("Lat: " + latitude);
					double lat = Double.parseDouble(df2.format(latitude));
					gpsCardPojo.setLatitude(lat);

					temp = Integer.parseInt(lonRaw.substring(0, 3), 10);
					temp1 = Double.parseDouble(lonRaw.substring(3));
					double longitude = (double) temp + temp1 / 60;
					System.out.println("lon: " + longitude);
					double lon = Double.parseDouble(df2.format(longitude));
					gpsCardPojo.setLatitude(lon);
				}
			}
			System.out.println("Latitude  " + latRaw + "long  " + lonRaw);
			String strspeed = gpsData.substring(34, 39);
			double speed = Double.valueOf(strspeed);
			gpsCardPojo.setSpeed(speed);
			String time_gmt = gpsData.substring(39, 45);
			String dateTime = strDate.substring(0, 2) + "-" + strDate.substring(2, 4) + "-" + strDate.substring(4) + " "
					+ time_gmt.substring(0, 2) + ":" + time_gmt.substring(2, 4) + ":" + time_gmt.substring(4);
			System.out.println("dateTime " + dateTime);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			Date dateTme = dateFormat.parse(dateTime);
			SimpleDateFormat newdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			GetGMTDateTime gmt = new GetGMTDateTime(Timestamp.valueOf(newdateFormat.format(dateTme)).toString());
			gpsCardPojo.setDevice_datetime(gmt.getDate());

			String direction_angle = gpsData.substring(45, 48);
			int headingint = Integer.parseInt(direction_angle, 16);
			double heading = Double.valueOf(headingint);
			gpsCardPojo.setHeading(heading);
			String gsmsignal = gpsData.substring(51, 54);
			gpsCardPojo.setGsm_signal(gsmsignal);
			String setteliteCountStr = gpsData.substring(54, 57);
			int setteliteCount = Integer.parseInt(setteliteCountStr, 16);
			gpsCardPojo.setSatelliteCount(setteliteCount);
			String battery_level = gpsData.substring(57, 60);
			gpsCardPojo.setBattery_level(battery_level);
			String fortification = gpsData.substring(61, 63);
			gpsCardPojo.setFortification(fortification);
			String workingMode = gpsData.substring(63, 65);
			gpsCardPojo.setWorkingMode(workingMode);

			System.out.println("speed " + speed + "dateTme  " + heading + "heading" + direction_angle + "lat"
					+ gpsCardPojo.getLatitude() + "lng" + gpsCardPojo.getLongitude() + "gsmsignal " + gsmsignal
					+ "satellite" + setteliteCountStr + "battery_level" + battery_level + "fortification"
					+ fortification);

			String MCC = str[1];
			String MNC = str[2];
			String LAC = str[3];
			String CId = str[4];

			gpsCardPojo.setMCC(MCC);
			gpsCardPojo.setMNC(MNC);
			gpsCardPojo.setLAC(LAC);
			gpsCardPojo.setCID(CId);

			String alarmState = str[5];
			switch (alarmState) {
			case "00":
				System.out.println("no alarm");
				break;
			case "01":
				System.out.println("SOS");
				gpsCardPojo.setPanic(true);
				break;
			case "02":
				System.out.println("low battery");
				break;
			case "03":
				System.out.println("pull-out");
				break;
			case "04":
				System.out.println("wearing notice");
				break;
			}
			String deviceLanguage = str[6];
			String replyaddressNeeded = str[7].substring(0, 1);
			if (replyaddressNeeded.equals("0")) {

			} else {

			}
			String isMobilehyperlinkInAdress = str[7].substring(1);
			if (isMobilehyperlinkInAdress.equals("0")) {

			} else {

			}

			String wifiInformation = str[8];
			String arr_wifiinfo[] = wifiInformation.split("&");
			for (int i = 0; i < arr_wifiinfo.length; i++) {
				String inner = arr_wifiinfo[i];
				String arr[] = inner.split("\\|");
				String ssid = arr[0];
				String mac_address = arr[1];
				String signal_strength = arr[2];

				System.out
						.println("ssid  " + ssid + "mac_address " + mac_address + "signal_strength " + signal_strength);
			}

			/*if (gpsCardPojo.isLbs()) {

				if (gpsCardPojo.getLAC() != null && gpsCardPojo.getCID() != null) {
					String lbsData = "0~0~" + gpsCardPojo.getLAC() + "~" + gpsCardPojo.getCID();
					gpsCardPojo.setLbsString(lbsData);

					Util util = new Util();
					double latlon[] = util.getLBSToLatLong(lbsData, data, gpsCardPojo.getDeviceNo());
					if (latlon != null) {
						gpsCardPojo.setLatitude(latlon[0]);
						gpsCardPojo.setLongitude(latlon[1]);
						Print.logInfo("GPS card LBS data calculated: " + latlon[0] + " , " + latlon[1]);
					}
				} else {
					gpsCardPojo.setLbsString("");
				}
			} else {
				gpsCardPojo.setLbsString("");
			}*/

			System.out.println("MCC " + MCC + "LAC  " + LAC + "alarmState" + alarmState + " replyaddressNeeded "
					+ " isMobilehyperlinkInAdress " + isMobilehyperlinkInAdress);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void heartbeatPackage(String data, GpsCardPojo gpsCardPojo) {
		try {
			String str[] = data.split(",");
			String gsmSignal = str[1].substring(0, 3);
			gpsCardPojo.setGsm_signal(gsmSignal);
			String satellites = str[1].substring(3, 6);
			gpsCardPojo.setSatellite(satellites);
			String batteryLevel = str[1].substring(6, 9);
			gpsCardPojo.setBattery_level(batteryLevel);
			String fortification = str[1].substring(9, 11);
			gpsCardPojo.setFortification(fortification);
			String workingMode = str[1].substring(11, 13);
			gpsCardPojo.setWorkingMode(workingMode);
			String countingSteps = str[2];
			String RollsFrequency = str[3].substring(0, 2);

			System.out.println("gsmSignal " + gsmSignal + " satellites  " + satellites + "alarmState"
					+ " countingSteps " + countingSteps + " batteryLevel " + batteryLevel + " workingMode "
					+ workingMode + " RollsFrequency " + RollsFrequency);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertaData(GpsCardPojo tgs) {
		if (tgs != null) {
			try {
				if (DbConnection.getMongoClient() == null || DbConnection.getDb() == null) {
					DbConnection.makeConnection();
					System.out.println("makeConnection");
				}
				DBCollection table = DbConnection.getDb().getCollection("poll_data");
				BasicDBObject document = new BasicDBObject();
				document.put("wire_locked", "");
				document.put("steel_wire_cut_off", "");
				document.put("battery_percentage", tgs.getBattery_level());
				document.put("power_mode", "");

				document.put("transID", "" + System.currentTimeMillis());
				document.put("device_no", tgs.getDeviceNo());
				document.put("long", tgs.getLongitude());
				document.put("collision", false);
				document.put("lat", tgs.getLatitude());
				document.put("speed", tgs.getSpeed());
				if (tgs.getSpeed() > 0) {
					document.put("ignition", true); // ignition
				} else {
					document.put("ignition", false); // ignition
				}
				document.put("odo", tgs.getOdometer());

				document.put("heading", tgs.getHeading());
				
				// //System.out.println("9906 insert stats 3 ");
				document.put("ac_status", tgs.isAc()); // AC
				document.put("driver_panic", tgs.isPanic());
				document.put("sos", tgs.isPanic());
				document.put("imobilize", tgs.isImmobilize());
				document.put("buzzer", false);
				document.put("cutoff", 0);
				document.put("fuel_level", tgs.getFuel());
				// Print.logInfo("Fuel>"+tgs.getFuelLevel());
				document.put("input2", false);
				document.put("output3", false);
				document.put("output4", false);
				document.put("output5", false);
				document.put("case_tempring", false);
				document.put("battery_tempring", tgs.getBattery_removed());
				document.put("harness_removel", false);
				// //System.out.println("true4");
				document.put("analog2", 0);
				document.put("device_datetime", tgs.getDevice_datetime());

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				GetGMTDateTime gmt = new GetGMTDateTime(Timestamp.valueOf(dateFormat.format(date)).toString());
				document.put("received_datetime", gmt.getDate());
				System.out.println("received_datetime-----------" + gmt.getDate());
				document.put("battery_status", "");

				document.put("lbs", tgs.getLbsString());
				document.put("gsm_rssi", "");
				document.put("gps_satellite", tgs.getSatellite());
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
				document.put("device", "GPS CARD");

				document.put("status", "PENDING");
				document.put("harsh_acc", false);
				document.put("harsh_braking", false);
				document.put("zig_zag_driving", false);
				//document.put("pulse_odo", Long.parseLong(tgs.getPulse_odo()));
				/*if(tgs.getPulse_odo().length() > 0)
				{
					
				}*/
				table.insert(document);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
