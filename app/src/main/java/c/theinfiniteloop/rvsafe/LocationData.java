package c.theinfiniteloop.rvsafe;

public class LocationData {

    private double latitude;

    private double longitude;

    private  String clientType;


    public LocationData(double latitude, double longitude, String clientType) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.clientType = clientType;
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

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }


}
