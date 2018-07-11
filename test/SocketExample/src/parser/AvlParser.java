package parser;

import java.awt.HeadlessException;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.print.DocFlavor.STRING;

import database.InsertToDB;

public class AvlParser {

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	AvlPojo avl = new AvlPojo();
	/*
	 * temp = Integer.parseInt(gprmcSt[2].substring(0, 2), 10); temp1 =
	 * Double.parseDouble(gprmcSt[2].substring(2)); latitude = (double) temp +
	 * temp1 / 60; // Print.logInfo (" Latitude-GPSSTATUS: "+latitude); temp =
	 * Integer.parseInt(gprmcSt[4].substring(0, 3), 10); temp1 =
	 * Double.parseDouble(gprmcSt[4].substring(3)); longitude = (double) temp +
	 * temp1 / 60;
	 * 
	 * 
	 * 
	 * Format：$$(2 Bytes) + Len(2 Bytes) + IMEI(15 Bytes) + | + AlarmType(2
	 * Bytes) + GPRMC + | + PDOP + | + HDOP + | + VDOP + | + Status(12 Bytes) +
	 * | + RTC(14 Bytes) + | + Voltage(8 Bytes) + | + ADC(8 Bytes) + | + LACCI(8
	 * Bytes) + | + Temperature(4 Bytes) | + Mile-meter(14 Bytes)+ + | Serial(4
	 * Bytes) + | RFID No(10 Byte)|+ Checksum (4 Byte) + \r\n(2 Bytes)
	 */
	// s =
	// "$$B9359772035880005|AA$GPRMC,024927.000,A,1858.6094,N,07249.4523,E,000.0,141.0,130612,,,A*61|03.6|03.1|01.8|100000000000|20120613024927|03790000|00000000|27B611FB|0000|0.0000|0018||1D6A";

	public byte[] parserData(String s) {
		try {
			System.out.println("count: " + s.length());

			String[] str = s.split("\\|");

			for (int i = 0; i < str.length; i++) {

				// System.out.println("i :" + str[i]);

			}

			String len = str[0].substring(2, 4);
			String imei = str[0].substring(4, 19);
			String alarmTyperaw = str[1].substring(0, str[1].indexOf("$"));
			if (alarmTyperaw.length() > 0) {
				int alarmDecimal = hex2decimal(alarmTyperaw);
				String hexFormat_alarmType = String.format("0x%02X", alarmDecimal);
				String chckd_alarmtype = checkAlarmType(hexFormat_alarmType);
				avl.setAlarmtype(chckd_alarmtype);
			}
			String gprmc = str[1].substring(str[1].indexOf("$GPRMC")); // $GPRMC,024927.000,A,1858.6094,N,07249.4523,E,000.0,141.0,130612,,,A*61
			// $GPRMC,024927.000,A,2232.3530,N,11403.7828,E,000.0,141.0,130612,,,A*61
			/*
			 * $GPRMC,220516,A,5133.82,N,00042.24,W,173.8,231.8,130694,004.2,W*
			 * 70 1 2 3 4 5 6 7 8 9 10 11 12
			 * 
			 * 
			 * 1 220516 Time Stamp 2 A validity - A-ok, V-invalid 3 5133.82
			 * current Latitude 4 N North/South 5 00042.24 current Longitude 6 W
			 * East/West 7 173.8 Speed in knots 8 231.8 True course 9 130694
			 * Date Stamp 10 004.2 Variation 11 W East/West 12 *70 checksum
			 */
			String[] gpsData = gprmc.split(",");
			if (gpsData[1].indexOf(".") != -1) {
				String gps_time = gpsData[1].substring(0, gpsData[1].indexOf("."));
				System.out.println("gps_time: " + gps_time);
			} else {
				String gps_timeraw = gpsData[1];
				System.out.println("gps_time: " + gps_timeraw);
			}
			String gps_dateraw = gpsData[9]; // dd/mm/yy

			String latRaw = gpsData[3];
			String lonRaw = gpsData[5];
			DecimalFormat df = new DecimalFormat("#.####");
			df.setRoundingMode(RoundingMode.CEILING);
			System.out.println("latRaw: " + latRaw);
			if (latRaw != null && latRaw.length() > 2) {
				int temp = Integer.parseInt(latRaw.substring(0, 2), 10);
				double temp1 = Double.parseDouble(latRaw.substring(2));
				double latitude = (double) temp + temp1 / 60;
				double lat = Double.valueOf(df.format(latitude));
				avl.setLatitude(lat);
				System.out.println("Lat: "+lat);
			}
			// Print.logInfo (" Latitude-GPSSTATUS: "+latitude);
			String latdr = gpsData[4];
			if (lonRaw != null && lonRaw.length() > 2) {
				int temp = Integer.parseInt(lonRaw.substring(0, 3), 10);
				double temp1 = Double.parseDouble(lonRaw.substring(3));
				double longitude = (double) temp + temp1 / 60;
				double lng = Double.valueOf(df.format(longitude));
				avl.setLongitude(lng);
			}
			String longdr = gpsData[6];
			String gprmc_valadity = gpsData[2];
			String speed_raw = gpsData[7];
			if (speed_raw.length() > 0) {
				double speed = Double.parseDouble(gpsData[7]);
				double speed_kph = speed * 1.852;
				avl.setSpeed_knots((int) speed);
				avl.setSpeed_kph((int) speed_kph);
			}
			String rawheading = gpsData[8];
			double heading = Double.parseDouble(rawheading);
			avl.setRawheading(rawheading);
			avl.setHeading(heading);
			String GpsDate = gpsData[9];
			System.out.println("GpsDate: " + GpsDate);
			String gprmc_magnetic_variation = gpsData[10];
			String gprmc_ckecksum = gpsData[12].substring(1);

			String pdop = str[2];
			String hdop = str[3];
			String vdop = str[4];
			String status = str[5];

			String rtc = str[6]; // ( RTC, "YYYY:MM:DD:hh:mm:ss" )
			Date formated_device_datetime = formatTime(rtc);

			String voltage = str[7];
			String charge_status_raw = voltage.substring(0, 1);
			String charge_status;
			if (charge_status_raw.equals("0")) {
				charge_status = "Off Charge";
			} else {
				charge_status = "On Charge";
			}
			String battery_voltage_raw = voltage.substring(1, 4);
			String battery_voltage = battery_voltage_raw.substring(0, 1) + "." + battery_voltage_raw.substring(1);
			String input_charge_voltage_raw = voltage.substring(4);
			String input_charge_voltage = input_charge_voltage_raw.substring(0, 2) + "."
					+ input_charge_voltage_raw.substring(2);

			String ADC = str[8];
			String ADA = ADC.substring(0, 2) + "." + ADC.substring(2, 4);
			String ADB = ADC.substring(4, 6) + "." + ADC.substring(6);

			String LACCI = str[9];
			String locationAreaCode = LACCI.substring(0, 4);
			int locationAreaCodeDec = hex2decimal(locationAreaCode);
			String CellId_raw = LACCI.substring(4, 8);
			int Cell_id = hex2decimal(CellId_raw);

			String temprature_raw = str[10];
			String temperature = temprature_raw.substring(0, 3) + "." + temprature_raw.substring(3);

			String mile_meter = str[11];
			double mile_d = Double.parseDouble(mile_meter);
			long mile_meterlon = Math.round(mile_d);

			String serial_no = str[12];
			String rfid = str[13];
			String checksum = str[14];

			avl.setLen(len);
			avl.setImei(imei);

			// Gps data
			avl.setGprmc_valadity(gprmc_valadity);

			avl.setLatdr(latdr);

			avl.setLongdr(longdr);

			avl.setGprmc_magnetic_variation(gprmc_magnetic_variation);
			avl.setGprmc_ckecksum(gprmc_ckecksum);

			avl.setPdop(pdop);
			avl.setHdop(hdop);
			avl.setVdop(vdop);

			avl.setStatus(status);
			checkButtonStatus(status);

			avl.setRtc(rtc);
			avl.setFormated_device_datetime(formated_device_datetime);
			avl.setVoltage(voltage);
			avl.setCharge_status_raw(charge_status_raw);
			avl.setCharge_status(charge_status);
			avl.setBattery_voltage(battery_voltage);
			avl.setInput_charge_voltage(input_charge_voltage);

			avl.setADC(ADC);
			avl.setADA(ADA);
			avl.setADB(ADB);

			avl.setLACCI(LACCI);
			avl.setLocationAreaCodeDec(String.valueOf(locationAreaCodeDec));
			avl.setCell_id(String.valueOf(Cell_id));
			avl.setTemp(temperature);

			avl.setMile_meter(mile_meterlon);
			avl.setSerial_no(serial_no);
			avl.setRfid(rfid);

			InsertToDB insertToDB = new InsertToDB();
			insertToDB.insertpollData(avl);

			System.out.println("len :" + len);
			System.out.println("imei :" + imei);

			System.out.println("Alarmtype :" + alarmTyperaw);

			System.out.println("gprmc :" + gprmc);

			System.out.println("gps_date: " + gps_dateraw);
			System.out.println("LatLngDr: " + latdr + "," + longdr);
			System.out.println("valadity: " + gprmc_valadity);
			System.out.println("magnetic_variation: " + gprmc_magnetic_variation);
			System.out.println("gprmc_ckecksum: " + gprmc_ckecksum);
			System.out.println("rawheading: " + rawheading);
			System.out.println("pdop :" + pdop);
			System.out.println("hdop :" + hdop);

			System.out.println("status :" + status);

			System.out.println("rtc :" + rtc); // ( RTC, "YYYY:MM:DD:hh:mm:ss" )
			System.out.println("formated_rtc :" + String.valueOf(formated_device_datetime));

			System.out.println("voltage :" + voltage);
			System.out.println("ADC :" + ADC);

			System.out.println("LACCI :" + LACCI);
			System.out.println("Location area code :" + locationAreaCode);
			System.out.println("locationAreaCodeDec :" + locationAreaCodeDec);

			System.out.println("cell id :" + CellId_raw);
			System.out.println("Cell_id_formated :" + Cell_id);

			System.out.println("temp :" + temperature);
			System.out.println("mile_meter :" + mile_meter);
			System.out.println("serial_no :" + serial_no);
			System.out.println("rfid :" + rfid);
			System.out.println("checksum :" + checksum);

			// String hexString = StringTools.toHexString(alarmType.getBytes());

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		/*
		 * System.out.println("hexString: " + hexString);
		 * System.out.printf("%02X",alarmType.getBytes());
		 */
	}

	public int hex2decimal(String s) {
		try {
			String digits = "0123456789ABCDEF";
			s = s.toUpperCase();
			int val = 0;
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				int d = digits.indexOf(c);
				val = 16 * val + d;
			}
			return val;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	public String checkAlarmType(String hexFormat_alarmType) {
		// if(hexFormat_alarmType.equals("0xaa"))
		try {
			String alarm_type = null;

			{
				// System.out.println("Interval GPRS data");
				switch (hexFormat_alarmType) {
				case "0x01":
					alarm_type = "SOS button pressed";
					System.out.println("SOS button is pressed");
					break;
				case "0x49":
					alarm_type = "Button A  pressed";
					System.out.println("Button A is pressed");
					break;
				case "0x09":
					alarm_type = "Auto Shutdown Alarm";
					System.out.println("Auto Shutdown Alarm");
					break;
				case "0x10":
					alarm_type = "Low battery Alarm";
					System.out.println("Low battery Alarm");
					break;
				case "0x11":
					alarm_type = "Over Speed Alarm";
					System.out.println("Over Speed Alarm");
					break;
				case "0x13":
					alarm_type = "Recover From Over Speed";
					System.out.println("Recover From Over Speed");
					break;
				case "0x14":
					alarm_type = "Deceleration Alarm";
					System.out.println("Deceleration Alarm");
					break;
				case "0x15":
					alarm_type = "Acceleration Alarm";
					System.out.println("Acceleration Alarm");
					break;
				case "0x30":
					alarm_type = " Parking Alarm";
					System.out.println("0x30 Parking Alarm");
					break;
				case "0x42":
					alarm_type = " Out Geo-fence Alarm";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x43":
					alarm_type = "Into Geo-fence Alarm";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x48":
					alarm_type = "Button B  pressed";
					System.out.println("Button B is pressed");
					break;
				case "0x47":
					alarm_type = "Button C pressed";
					System.out.println("Button C is pressed");
					break;
				case "0x50":
					alarm_type = "IO-1 Close";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x51":
					alarm_type = "IO-1 Open";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x52":
					alarm_type = "IO-2 Close";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x53":
					alarm_type = "IO-2 Open";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x60":
					alarm_type = "Begin Charge";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x61":
					alarm_type = "End Charge";
					System.out.println("End Charge");
					break;
				case "0x66":
					alarm_type = "Find a new RFID";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x67":
					alarm_type = "end  dispatch";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x77":
					alarm_type = "Angle Alarm";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x88":
					alarm_type = " Heartbeat";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x91":
					alarm_type = "Into Sleep Mode";
					System.out.println("Out Geo-fence Alarm");
					break;
				case "0x92":
					alarm_type = "Wakeup From Sleep Mode";
					System.out.println("Out Geo-fence Alarm");
					break;

				case "0xAA":
					alarm_type = "Interval GPRS data";
					System.out.println("Interval GPRS data");
					break;
				default:
					break;
				}
				return alarm_type;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	Date formatTime(String rtc)// ( RTC, "YYYY:MM:DD:hh:mm:ss" )
	{
		try {
			String formated = rtc.substring(0, 4) + "-" + rtc.substring(4, 6) + "-" + rtc.substring(6, 8) + " "
					+ rtc.substring(8, 10) + ":" + rtc.substring(10, 12) + ":" + rtc.substring(12, 14);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			cal.setTime(Timestamp.valueOf(formated));
			/*cal.add(Calendar.HOUR, +5);
			cal.add(Calendar.MINUTE, +30);*/
			Date timeDate = cal.getTime();
			GetGMTDateTime gmt = new GetGMTDateTime(Timestamp.valueOf(dateFormat.format(timeDate)).toString());
			return gmt.getDate();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	void checkButtonStatus(String status) {
		try {
			String SOS_button_status = status.substring(0, 1);
			avl.setSOS_button_status(SOS_button_status);
			String Button_A_button_status = status.substring(1, 2);
			avl.setButton_A_button_status(Button_A_button_status);
			String Button_B_button_status = status.substring(2, 3);
			avl.setButton_B_button_status(Button_B_button_status);
			String Button_C_button_status = status.substring(3, 4);
			avl.setButton_C_button_status(Button_C_button_status);
			String status_digital_input_1 = status.substring(4, 5);
			avl.setStatus_digital_input_1(status_digital_input_1);
			String status_digital_input_2 = status.substring(5, 6);
			avl.setStatus_digital_input_2(status_digital_input_2);
			String Reserved1_status = status.substring(6, 7);
			avl.setReserved1_status(Reserved1_status);
			String Reserved2_status = status.substring(7, 8);
			avl.setReserved2_status(Reserved2_status);
			String Digital_Out_1_status = status.substring(8, 9);
			avl.setDigital_Out_1_status(Digital_Out_1_status);
			String Digital_Out_2_status = status.substring(9, 10);
			avl.setDigital_Out_2_status(Digital_Out_2_status);
			String Digital_Out_3_status = status.substring(10, 11);
			avl.setDigital_Out_3_status(Digital_Out_3_status);
			String Reserved3_status = status.substring(11);
			avl.setReserved3_status(Reserved3_status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
