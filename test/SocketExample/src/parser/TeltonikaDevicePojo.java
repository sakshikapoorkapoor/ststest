package parser;

import java.util.Date;

public class TeltonikaDevicePojo {
	
	private String hardware_version;
	String CID,LAC,lbsString;
	public String getLbsString() {
		return lbsString;
	}
	public void setLbsString(String lbsString) {
		this.lbsString = lbsString;
	}
	public String getCID() {
		return CID;
	}
	public void setCID(String cID) {
		CID = cID;
	}
	public String getLAC() {
		return LAC;
	}
	public void setLAC(String lAC) {
		LAC = lAC;
	}
	int gprs_validity, gsm_strength, cell_id,battery_removed,fuel;
	double odometer, lat, lon, speed,heading,altitude;
	float hdop, internal_batt_voltage, main_pwr_voltage;
	Date device_datetime;
	int cutoff;
	boolean vibration,ignition, power_status, sim_removed, box_status, history_packet,collision,ac,panic,immobilize,lbs;
	String deviceNo, imei="", network_operator,analog_input1,analog_input2,version, ussed_satellite, visible_satellite;
	String packetType, mileage;
	boolean harshBraking,harshAcc,zigZagDriving;
	
	private String wireLock="";
	private String steelWireCut="";
	private String batteryPercentage="";
	private String powerMode="";
	
	
	
	public boolean isHarshBraking() {
		return harshBraking;
	}
	public void setHarshBraking(boolean harshBraking) {
		this.harshBraking = harshBraking;
	}
	public boolean isHarshAcc() {
		return harshAcc;
	}
	public void setHarshAcc(boolean harshAcc) {
		this.harshAcc = harshAcc;
	}
	public boolean isZigZagDriving() {
		return zigZagDriving;
	}
	public void setZigZagDriving(boolean zigZagDriving) {
		this.zigZagDriving = zigZagDriving;
	}
	public String getHardware_version() {
		return hardware_version;
	}
	public void setHardware_version(String hardware_version) {
		this.hardware_version = hardware_version;
	}
	public int getGprs_validity() {
		return gprs_validity;
	}
	public void setGprs_validity(int gprs_validity) {
		this.gprs_validity = gprs_validity;
	}
	public int getGsm_strength() {
		return gsm_strength;
	}
	public void setGsm_strength(int gsm_strength) {
		this.gsm_strength = gsm_strength;
	}
	public int getCell_id() {
		return cell_id;
	}
	public void setCell_id(int cell_id) {
		this.cell_id = cell_id;
	}
	public int getBattery_removed() {
		return battery_removed;
	}
	public void setBattery_removed(int battery_removed) {
		this.battery_removed = battery_removed;
	}
	public int getFuel() {
		return fuel;
	}
	public void setFuel(int fuel) {
		this.fuel = fuel;
	}
	public double getOdometer() {
		return odometer;
	}
	public void setOdometer(double odometer) {
		this.odometer = odometer;
	}
	public double getLat() {
		String lt = String.valueOf(lat);
		if (lt.length() >= 8) {
			lat = Double.parseDouble(lt.substring(0, 7));
		}
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		String lt = String.valueOf(lon);
		if (lt.length() >= 8) {
			lon = Double.parseDouble(lt.substring(0, 7));
		}
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getHeading() {
		return heading;
	}
	public void setHeading(double heading) {
		this.heading = heading;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public float getHdop() {
		return hdop;
	}
	public void setHdop(float hdop) {
		this.hdop = hdop;
	}
	public float getInternal_batt_voltage() {
		return internal_batt_voltage;
	}
	public void setInternal_batt_voltage(float internal_batt_voltage) {
		this.internal_batt_voltage = internal_batt_voltage;
	}
	public float getMain_pwr_voltage() {
		return main_pwr_voltage;
	}
	public void setMain_pwr_voltage(float main_pwr_voltage) {
		this.main_pwr_voltage = main_pwr_voltage;
	}
	public Date getDevice_datetime() {
		return device_datetime;
	}
	public void setDevice_datetime(Date device_datetime) {
		this.device_datetime = device_datetime;
	}
	public boolean isVibration() {
		return vibration;
	}
	public void setVibration(boolean vibration) {
		this.vibration = vibration;
	}
	public boolean isIgnition() {
		return ignition;
	}
	public void setIgnition(boolean ignition) {
		this.ignition = ignition;
	}
	public boolean isPower_status() {
		return power_status;
	}
	public void setPower_status(boolean power_status) {
		this.power_status = power_status;
	}
	public boolean isSim_removed() {
		return sim_removed;
	}
	public void setSim_removed(boolean sim_removed) {
		this.sim_removed = sim_removed;
	}
	public boolean isBox_status() {
		return box_status;
	}
	public void setBox_status(boolean box_status) {
		this.box_status = box_status;
	}
	public boolean isHistory_packet() {
		return history_packet;
	}
	public void setHistory_packet(boolean history_packet) {
		this.history_packet = history_packet;
	}
	public boolean isCollision() {
		return collision;
	}
	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	public boolean isAc() {
		return ac;
	}
	public void setAc(boolean ac) {
		this.ac = ac;
	}
	public boolean isPanic() {
		return panic;
	}
	public void setPanic(boolean panic) {
		this.panic = panic;
	}
	public boolean isImmobilize() {
		return immobilize;
	}
	public void setImmobilize(boolean immobilize) {
		this.immobilize = immobilize;
	}

	public int getCutoff() {
		return cutoff;
	}
	public void setCutoff(int cutoff) {
		this.cutoff = cutoff;
	}
	public boolean isLbs() {
		return lbs;
	}
	public void setLbs(boolean lbs) {
		this.lbs = lbs;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getNetwork_operator() {
		return network_operator;
	}
	public void setNetwork_operator(String network_operator) {
		this.network_operator = network_operator;
	}
	public String getAnalog_input1() {
		return analog_input1;
	}
	public void setAnalog_input1(String analog_input1) {
		this.analog_input1 = analog_input1;
	}
	public String getAnalog_input2() {
		return analog_input2;
	}
	public void setAnalog_input2(String analog_input2) {
		this.analog_input2 = analog_input2;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUssed_satellite() {
		return ussed_satellite;
	}
	public void setUssed_satellite(String ussed_satellite) {
		this.ussed_satellite = ussed_satellite;
	}
	public String getVisible_satellite() {
		return visible_satellite;
	}
	public void setVisible_satellite(String visible_satellite) {
		this.visible_satellite = visible_satellite;
	}
	public String getPacketType() {
		return packetType;
	}
	public void setPacketType(String packetType) {
		this.packetType = packetType;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getWireLock() {
		return wireLock;
	}
	public void setWireLock(String wireLock) {
		this.wireLock = wireLock;
	}
	public String getSteelWireCut() {
		return steelWireCut;
	}
	public void setSteelWireCut(String steelWireCut) {
		this.steelWireCut = steelWireCut;
	}
	public String getBatteryPercentage() {
		return batteryPercentage;
	}
	public void setBatteryPercentage(String batteryPercentage) {
		this.batteryPercentage = batteryPercentage;
	}
	public String getPowerMode() {
		return powerMode;
	}
	public void setPowerMode(String powerMode) {
		this.powerMode = powerMode;
	}
	
}
