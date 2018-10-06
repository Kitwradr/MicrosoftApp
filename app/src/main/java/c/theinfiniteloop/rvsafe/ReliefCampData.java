package c.theinfiniteloop.rvsafe;

public class ReliefCampData {

    public int ID;

    public String Location_name;

    public double camp_latitude;

    public double camp_longitude;

    public ReliefCampData(int ID, String location_name, double camp_latitude, double camp_longitude) {
        this.ID = ID;
        Location_name = location_name;
        this.camp_latitude = camp_latitude;
        this.camp_longitude = camp_longitude;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLocation_name() {
        return Location_name;
    }

    public void setLocation_name(String location_name) {
        Location_name = location_name;
    }

    public double getCamp_latitude() {
        return camp_latitude;
    }

    public void setCamp_latitude(double camp_latitude) {
        this.camp_latitude = camp_latitude;
    }

    public double getCamp_longitude() {
        return camp_longitude;
    }

    public void setCamp_longitude(double camp_longitude) {
        this.camp_longitude = camp_longitude;
    }
}
