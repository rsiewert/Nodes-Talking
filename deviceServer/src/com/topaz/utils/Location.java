package com.topaz.utils;

public class Location {

	// Locations are specified absolutely using lat, long, alt
	double latitude = 0;
	double longitude = 0;
	double altitude = 0;
	
	public Location(double latitude, double longitude, double altitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		
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
	public void setLongiture(double longiture) {
		this.longitude = longiture;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	
}
