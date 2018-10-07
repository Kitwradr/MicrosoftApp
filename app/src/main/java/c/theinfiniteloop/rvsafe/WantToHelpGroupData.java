package c.theinfiniteloop.rvsafe;

import java.util.ArrayList;

public class WantToHelpGroupData {

    public int wantToHelp_id;

    public ArrayList<Integer> volunteerGroup_ids;

    public ArrayList<Integer> helpAndDonate_ids;

    public WantToHelpGroupData(int wantToHelp_id, ArrayList<Integer> volunteerGroup_ids, ArrayList<Integer> helpAndDonate_ids) {
        this.wantToHelp_id = wantToHelp_id;
        this.volunteerGroup_ids = volunteerGroup_ids;
        this.helpAndDonate_ids = helpAndDonate_ids;
    }

    public int getWantToHelp_id() {
        return wantToHelp_id;
    }

    public void setWantToHelp_id(int wantToHelp_id) {
        this.wantToHelp_id = wantToHelp_id;
    }

    public ArrayList<Integer> getVolunteerGroup_ids() {
        return volunteerGroup_ids;
    }

    public void setVolunteerGroup_ids(ArrayList<Integer> volunteerGroup_ids) {
        this.volunteerGroup_ids = volunteerGroup_ids;
    }

    public ArrayList<Integer> getHelpAndDonate_ids() {
        return helpAndDonate_ids;
    }

    public void setHelpAndDonate_ids(ArrayList<Integer> helpAndDonate_ids) {
        this.helpAndDonate_ids = helpAndDonate_ids;
    }
}
