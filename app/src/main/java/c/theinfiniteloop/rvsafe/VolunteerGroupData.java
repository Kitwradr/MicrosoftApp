package c.theinfiniteloop.rvsafe;

import java.time.LocalDate;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("VolunteerGroups")

public class VolunteerGroupData {

    @Id
    public int group_id;

    public String group_name;

    public String group_image_url;

    public VolunteerGroupData() {
    }

    public String group_description;

    public String started_by;

    public double location_lat;

    public double location_long;

    public LocalDate date;

    public float expected_duration;

    public int male_members;

    public int female_members;

    public String email_id;

    public VolunteerGroupData(int group_id, String group_name, String group_image_url, String group_description, String started_by, double location_lat, double location_long, LocalDate date, float expected_duration, int male_members, int female_members, String email_id) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.group_image_url = group_image_url;
        this.group_description = group_description;
        this.started_by = started_by;
        this.location_lat = location_lat;
        this.location_long = location_long;
        this.date = date;
        this.expected_duration = expected_duration;
        this.male_members = male_members;
        this.female_members = female_members;
        this.email_id = email_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_image_url() {
        return group_image_url;
    }

    public void setGroup_image_url(String group_image_url) {
        this.group_image_url = group_image_url;
    }

    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }

    public String getStarted_by() {
        return started_by;
    }

    public void setStarted_by(String started_by) {
        this.started_by = started_by;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(double location_lat) {
        this.location_lat = location_lat;
    }

    public double getLocation_long() {
        return location_long;
    }

    public void setLocation_long(double location_long) {
        this.location_long = location_long;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getExpected_duration() {
        return expected_duration;
    }

    public void setExpected_duration(float expected_duration) {
        this.expected_duration = expected_duration;
    }

    public int getMale_members() {
        return male_members;
    }

    public void setMale_members(int male_members) {
        this.male_members = male_members;
    }

    public int getFemale_members() {
        return female_members;
    }

    public void setFemale_members(int female_members) {
        this.female_members = female_members;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
}
