package c.theinfiniteloop.rvsafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClusterData {

    public int status;

    public int numsafe;

    public int numunsafe;

    public SingleCusterData safe;

    public SingleCusterData unsafe;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumsafe() {
        return numsafe;
    }

    public void setNumsafe(int numsafe) {
        this.numsafe = numsafe;
    }

    public int getNumunsafe() {
        return numunsafe;
    }

    public void setNumunsafe(int numunsafe) {
        this.numunsafe = numunsafe;
    }

    public SingleCusterData getSafe() {
        return safe;
    }

    public void setSafe(SingleCusterData safe) {
        this.safe = safe;
    }

    public SingleCusterData getUnsafe() {
        return unsafe;
    }

    public void setUnsafe(SingleCusterData unsafe) {
        this.unsafe = unsafe;
    }

    @Override
    public String toString() {
        return "ClusterData{" +
                "status=" + status +
                ", numsafe=" + numsafe +
                ", numunsafe=" + numunsafe +
                ", safe='" + safe + '\'' +
                ", unsafe='" + unsafe + '\'' +
                '}';
    }

}

