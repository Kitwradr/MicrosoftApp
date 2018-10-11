package c.theinfiniteloop.rvsafe;

import java.util.ArrayList;

public class VolunteerDataList {

    ArrayList<VolunteerGroupData> data ;

    public ArrayList<VolunteerGroupData> getData() {
        return data;
    }

    public void setData(ArrayList<VolunteerGroupData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VolunteerDataList{" +
                "data=" + data +
                '}';
    }
}
