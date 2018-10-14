package c.theinfiniteloop.rvsafe;

import java.util.ArrayList;
import java.util.HashMap;

public class SingleCusterData {

    public HashMap<String,String> data;


    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SingleCusterData{" +
                "data=" + data +
                '}';
    }

    public SingleCusterData(HashMap<String, String> data) {
        this.data = data;
    }
}
