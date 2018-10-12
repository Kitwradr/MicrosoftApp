package c.theinfiniteloop.rvsafe;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    public String _id;

    public String user_id;

    public String Lat;

    public String Long;

    public String issafe;

    public String Disasterid;

    public String numvictims;

    public ArrayList<String> bloburls;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getIssafe() {
        return issafe;
    }

    public void setIssafe(String issafe) {
        this.issafe = issafe;
    }

    public String getDisasterid() {
        return Disasterid;
    }

    public void setDisasterid(String disasterid) {
        Disasterid = disasterid;
    }

    public String getNumvictims() {
        return numvictims;
    }

    public void setNumvictims(String numvictims) {
        this.numvictims = numvictims;
    }

    public ArrayList<String> getBloburls() {
        return bloburls;
    }

    public void setBloburls(ArrayList<String> bloburls) {
        this.bloburls = bloburls;
    }
}
