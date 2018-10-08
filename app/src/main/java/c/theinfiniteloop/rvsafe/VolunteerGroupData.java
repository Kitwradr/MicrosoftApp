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

    public String start_location;

    public String destination;

    public String group_description;

    public String started_by;

    public String date;

    public String expected_duration;

    public int male_members;

    public int female_members;

    public String email_id;

    public int number_of_members;

    public int getNumber_of_members() {
        return number_of_members;
    }

    public void setNumber_of_members(int number_of_members) {
        this.number_of_members = number_of_members;
    }

    public VolunteerGroupData() {
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

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpected_duration() {
        return expected_duration;
    }

    public void setExpected_duration(String expected_duration) {
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
