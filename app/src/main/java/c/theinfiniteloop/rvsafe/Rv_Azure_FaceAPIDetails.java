package c.theinfiniteloop.rvsafe;

import java.util.Arrays;

public class Rv_Azure_FaceAPIDetails {


    public  String user_id;

    public String Lat;

    public String Long;

    public int priority;

    public String children;

    public int numstuck;

    public String female;

    public String male;

    public String elders;

    public String[] blobs;

    @Override
    public String toString() {
        return "Rv_Azure_FaceAPIDetails{" +
                "Lat='" + Lat + '\'' +
                ", Long='" + Long + '\'' +
                ", priority=" + priority +
                ", children='" + children + '\'' +
                ", numstuck=" + numstuck +
                ", female='" + female + '\'' +
                ", male='" + male + '\'' +
                ", elders='" + elders + '\'' +
                ", blobs=" + Arrays.toString(blobs) +
                '}';
    }


    public String getUserid() {
        return user_id;
    }

    public void setUserid(String userid) {
        this.user_id = userid;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public int getNumstuck() {
        return numstuck;
    }

    public void setNumstuck(int numstuck) {
        this.numstuck = numstuck;
    }

    public String getFemale() {
        return female;
    }

    public void setFemale(String female) {
        this.female = female;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getElders() {
        return elders;
    }

    public void setElders(String elders) {
        this.elders = elders;
    }

    public String[] getBlobs() {
        return blobs;
    }

    public void setBlobs(String[] blobs) {
        this.blobs = blobs;
    }
}
