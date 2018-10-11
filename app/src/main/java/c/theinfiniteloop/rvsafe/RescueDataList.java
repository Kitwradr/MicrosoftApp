package c.theinfiniteloop.rvsafe;

import java.util.ArrayList;

public class RescueDataList {

    ArrayList<RescueGroupData> data;

    @Override
    public String toString() {
        return "RescueDataList{" +
                "data=" + data +
                '}';
    }

    public ArrayList<RescueGroupData> getData() {
        return data;
    }

    public void setData(ArrayList<RescueGroupData> data) {
        this.data = data;
    }
}
