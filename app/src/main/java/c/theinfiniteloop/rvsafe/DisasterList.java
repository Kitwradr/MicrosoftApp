package c.theinfiniteloop.rvsafe;

import java.util.ArrayList;

public class DisasterList {

    public ArrayList<DisasterData> data;

    public ArrayList<DisasterData> getData() {
        return data;
    }

    public void setData(ArrayList<DisasterData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DisasterList{" +
                "data=" + data +
                '}';
    }
}
