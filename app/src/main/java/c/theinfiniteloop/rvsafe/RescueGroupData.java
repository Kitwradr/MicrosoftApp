package c.theinfiniteloop.rvsafe;

public class RescueGroupData {

    public int _id;

    public int disaster_id;

    public String group_name;

    public double latitude;

    public double longitude;

    public String contact_no;

    public int getDisaster_id() {
        return disaster_id;
    }

    public void setDisaster_id(int disaster_id) {
        this.disaster_id = disaster_id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
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

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    @Override
    public String toString() {
        return "RescueGroupData{" +
                "_id=" + _id +
                ", disaster_id=" + disaster_id +
                ", group_name='" + group_name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", contact_no='" + contact_no + '\'' +
                '}';
    }
}
