package parser;

import java.util.Date;

public class AvlPojo {
	double latitude, longitude, heading;
	String len, imei, alarmTyperaw, Alarmtype, hexFormat_alarmType, chckd_alarmtype, gprmc, gprmc_valadity, latdr,
			longdr, gprmc_magnetic_variation, rawheading, gprmc_ckecksum, pdop, hdop, vdop, status, rtc, formated_rtc,
			charge_status, charge_status_raw, voltage, battery_voltage, input_charge_voltage, ADC, ADA, ADB, LACCI,
			locationAreaCodeDec, Cell_id, temp, serial_no, rfid, checksum;
	String SOS_button_status, Button_A_button_status, Button_B_button_status, Button_C_button_status,
			status_digital_input_1, status_digital_input_2, Reserved1_status, Reserved2_status, Digital_Out_1_status,
			Digital_Out_2_status, Digital_Out_3_status, Reserved3_status;
	int speed_knots, speed_kph;
	Date formated_device_datetime;
	long mile_meter;

	public String getLen() {
		return len;
	}

	public void setLen(String len) {
		this.len = len;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getAlarmTyperaw() {
		return alarmTyperaw;
	}

	public void setAlarmTyperaw(String alarmTyperaw) {
		this.alarmTyperaw = alarmTyperaw;
	}

	public String getAlarmtype() {
		return Alarmtype;
	}

	public void setAlarmtype(String alarmtype) {
		Alarmtype = alarmtype;
	}

	public String getHexFormat_alarmType() {
		return hexFormat_alarmType;
	}

	public void setHexFormat_alarmType(String hexFormat_alarmType) {
		this.hexFormat_alarmType = hexFormat_alarmType;
	}

	public String getChckd_alarmtype() {
		return chckd_alarmtype;
	}

	public void setChckd_alarmtype(String chckd_alarmtype) {
		this.chckd_alarmtype = chckd_alarmtype;
	}

	public String getGprmc() {
		return gprmc;
	}

	public void setGprmc(String gprmc) {
		this.gprmc = gprmc;
	}

	public String getGprmc_valadity() {
		return gprmc_valadity;
	}

	public void setGprmc_valadity(String gprmc_valadity) {
		this.gprmc_valadity = gprmc_valadity;
	}

	public double getLatitude() {
		if (("" + latitude).length() > 7) {
			latitude = Double.parseDouble(("" + latitude).substring(0, 7));
		}
		return latitude;
	}

	public void setLatitude(double latitude) {

		this.latitude = latitude;
	}

	public String getLatdr() {
		return latdr;
	}

	public void setLatdr(String latdr) {
		this.latdr = latdr;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getLongdr() {
		return longdr;
	}

	public void setLongdr(String longdr) {
		this.longdr = longdr;
	}

	public int getSpeed_knots() {
		return speed_knots;
	}

	public void setSpeed_knots(int speed_knots) {
		this.speed_knots = speed_knots;
	}

	public int getSpeed_kph() {
		return speed_kph;
	}

	public void setSpeed_kph(int speed_kph) {
		this.speed_kph = speed_kph;
	}

	public String getRawheading() {
		return rawheading;
	}

	public void setRawheading(String rawheading) {
		this.rawheading = rawheading;
	}

	public double getHeading() {
		return heading;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public String getGprmc_magnetic_variation() {
		return gprmc_magnetic_variation;
	}

	public void setGprmc_magnetic_variation(String gprmc_magnetic_variation) {
		this.gprmc_magnetic_variation = gprmc_magnetic_variation;
	}

	public String getGprmc_ckecksum() {
		return gprmc_ckecksum;
	}

	public void setGprmc_ckecksum(String gprmc_ckecksum) {
		this.gprmc_ckecksum = gprmc_ckecksum;
	}

	public String getPdop() {
		return pdop;
	}

	public void setPdop(String pdop) {
		this.pdop = pdop;
	}

	public String getHdop() {
		return hdop;
	}

	public void setHdop(String hdop) {
		this.hdop = hdop;
	}

	public String getVdop() {
		return vdop;
	}

	public void setVdop(String vdop) {
		this.vdop = vdop;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSOS_button_status() {
		return SOS_button_status;
	}

	public void setSOS_button_status(String sOS_button_status) {
		SOS_button_status = sOS_button_status;
	}

	public String getButton_A_button_status() {
		return Button_A_button_status;
	}

	public void setButton_A_button_status(String button_A_button_status) {
		Button_A_button_status = button_A_button_status;
	}

	public String getButton_B_button_status() {
		return Button_B_button_status;
	}

	public void setButton_B_button_status(String button_B_button_status) {
		Button_B_button_status = button_B_button_status;
	}

	public String getButton_C_button_status() {
		return Button_C_button_status;
	}

	public void setButton_C_button_status(String button_C_button_status) {
		Button_C_button_status = button_C_button_status;
	}

	public String getStatus_digital_input_1() {
		return status_digital_input_1;
	}

	public void setStatus_digital_input_1(String status_digital_input_1) {
		this.status_digital_input_1 = status_digital_input_1;
	}

	public String getStatus_digital_input_2() {
		return status_digital_input_2;
	}

	public void setStatus_digital_input_2(String status_digital_input_2) {
		this.status_digital_input_2 = status_digital_input_2;
	}

	public String getReserved1_status() {
		return Reserved1_status;
	}

	public void setReserved1_status(String reserved1_status) {
		Reserved1_status = reserved1_status;
	}

	public String getReserved2_status() {
		return Reserved2_status;
	}

	public void setReserved2_status(String reserved2_status) {
		Reserved2_status = reserved2_status;
	}

	public String getDigital_Out_1_status() {
		return Digital_Out_1_status;
	}

	public void setDigital_Out_1_status(String digital_Out_1_status) {
		Digital_Out_1_status = digital_Out_1_status;
	}

	public String getDigital_Out_2_status() {
		return Digital_Out_2_status;
	}

	public void setDigital_Out_2_status(String digital_Out_2_status) {
		Digital_Out_2_status = digital_Out_2_status;
	}

	public String getDigital_Out_3_status() {
		return Digital_Out_3_status;
	}

	public void setDigital_Out_3_status(String digital_Out_3_status) {
		Digital_Out_3_status = digital_Out_3_status;
	}

	public String getReserved3_status() {
		return Reserved3_status;
	}

	public void setReserved3_status(String reserved3_status) {
		Reserved3_status = reserved3_status;
	}

	public String getRtc() {
		return rtc;
	}

	public void setRtc(String rtc) {
		this.rtc = rtc;
	}

	public Date getFormated_device_datetime() {
		return formated_device_datetime;
	}

	public void setFormated_device_datetime(Date formated_device_datetime) {
		this.formated_device_datetime = formated_device_datetime;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getCharge_status_raw() {
		return charge_status_raw;
	}

	public void setCharge_status_raw(String charge_status_raw) {
		this.charge_status_raw = charge_status_raw;
	}

	public String getCharge_status() {
		return charge_status;
	}

	public void setCharge_status(String charge_status) {
		this.charge_status = charge_status;
	}

	public String getBattery_voltage() {
		return battery_voltage;
	}

	public void setBattery_voltage(String battery_voltage) {
		this.battery_voltage = battery_voltage;
	}

	public String getInput_charge_voltage() {
		return input_charge_voltage;
	}

	public void setInput_charge_voltage(String input_charge_voltage) {
		this.input_charge_voltage = input_charge_voltage;
	}

	public String getADC() {
		return ADC;
	}

	public void setADC(String aDC) {
		ADC = aDC;
	}

	public String getADA() {
		return ADA;
	}

	public void setADA(String aDA) {
		ADA = aDA;
	}

	public String getADB() {
		return ADB;
	}

	public void setADB(String aDB) {
		ADB = aDB;
	}

	public String getLACCI() {
		return LACCI;
	}

	public void setLACCI(String lACCI) {
		LACCI = lACCI;
	}

	public String getLocationAreaCodeDec() {
		return locationAreaCodeDec;
	}

	public void setLocationAreaCodeDec(String locationAreaCodeDec) {
		this.locationAreaCodeDec = locationAreaCodeDec;
	}

	public String getCell_id() {
		return Cell_id;
	}

	public void setCell_id(String cell_id) {
		Cell_id = cell_id;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public long getMile_meter() {
		return mile_meter;
	}

	public void setMile_meter(long mile_meter) {
		this.mile_meter = mile_meter;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

}
