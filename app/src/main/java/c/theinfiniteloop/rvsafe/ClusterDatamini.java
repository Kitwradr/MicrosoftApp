package c.theinfiniteloop.rvsafe;

import java.util.Arrays;

public class ClusterDatamini {
    public double[] doubles;

    public double[] getDoubles() {
        return doubles;
    }

    public void setDoubles(double[] doubles) {
        this.doubles = doubles;
    }

    @Override
    public String toString() {
        return "ClusterDatamini{" +
                "doubles=" + Arrays.toString(doubles) +
                '}';
    }
}
