package parser;

import java.util.Date;

public class GpsCardPojo {
	double odometer, latitude, longitude,heading,speed;
	String imei,deviceNo,direction,gsm_signal,fortification,workingMode,MCC,LAC,MNC,CID,country_code,operator_code,lbsString;
	Date date,device_datetime;
	boolean ignition,ac,panic,immobilize,lbs;
	private String wireLock="",battery_level="",satellite="";
	private String steelWireCut="",pulse_odo;
	private String batteryPercentage="";
	private String powerMode="";
	int cutoff,fuel,battery_removed,satelliteCount;
	
	public int getSatelliteCount() {
		return satelliteCount;
	}
	public void setSatelliteCount(int satelliteCount) {
		this.satelliteCount = satelliteCount;
	}
	public int getBattery_removed() {
		return battery_removed;
	}
	public void setBattery_removed(int battery_removed) {
		this.battery_removed = battery_removed;
	}
	public double getOdometer() {
		return odometer;
	}
	public void setOdometer(double odometer) {
		this.odometer = odometer;
	}
	public boolean isIgnition() {
		return ignition;
	}
	public void setIgnition(boolean ignition) {
		this.ignition = ignition;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getHeading() {
		return heading;
	}
	public void setHeading(double heading) {
		this.heading = heading;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getGsm_signal() {
		return gsm_signal;
	}
	public void setGsm_signal(String gsm_signal) {
		this.gsm_signal = gsm_signal;
	}
	public String getSatellite() {
		return satellite;
	}
	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}
	public String getBattery_level() {
		return battery_level;
	}
	public void setBattery_level(String battery_level) {
		this.battery_level = battery_level;
	}
	public String getFortification() {
		return fortification;
	}
	public void setFortification(String fortification) {
		this.fortification = fortification;
	}
	public String getWorkingMode() {
		return workingMode;
	}
	public void setWorkingMode(String workingMode) {
		this.workingMode = workingMode;
	}
	public String getMCC() {
		return MCC;
	}
	public void setMCC(String mCC) {
		MCC = mCC;
	}
	public String getLAC() {
		return LAC;
	}
	public void setLAC(String lAC) {
		LAC = lAC;
	}
	public String getMNC() {
		return MNC;
	}
	public void setMNC(String mNC) {
		MNC = mNC;
	}
	public String getCID() {
		return CID;
	}
	public void setCID(String cID) {
		CID = cID;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getOperator_code() {
		return operator_code;
	}
	public void setOperator_code(String operator_code) {
		this.operator_code = operator_code;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
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
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
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
	public boolean isLbs() {
		return lbs;
	}
	public void setLbs(boolean lbs) {
		this.lbs = lbs;
	}
	public int getCutoff() {
		return cutoff;
	}
	public void setCutoff(int cutoff) {
		this.cutoff = cutoff;
	}
	public int getFuel() {
		return fuel;
	}
	public void setFuel(int fuel) {
		this.fuel = fuel;
	}
	public Date getDevice_datetime() {
		return device_datetime;
	}
	public void setDevice_datetime(Date device_datetime) {
		this.device_datetime = device_datetime;
	}
	public String getLbsString() {
		return lbsString;
	}
	public void setLbsString(String lbsString) {
		this.lbsString = lbsString;
	}
	public String getPulse_odo() {
		return pulse_odo;
	}
	public void setPulse_odo(String pulse_odo) {
		this.pulse_odo = pulse_odo;
	}
	
}
