package c.theinfiniteloop.rvsafe;

import java.io.Serializable;

public class RvAzure_DataModelForVolunteerService implements Serializable
{
    String teamname;
    String description;
    String startedby;
    String startlocation;
    String destination;
    String startdate;
    String expectedduration;
    int malemembers;
    int femalemembers;
    int number_of_members;

    int id_;
    String teamimage;

    public RvAzure_DataModelForVolunteerService(String teamnamename,String description,String startedby,String startlocation,String destination,String startdate,String expectedduration,int malemembers,int femalemembers ,int number_of_members, int id_, String teamimageurl)
    {
        this.teamname =teamnamename;
        this.description=description;
        this.startedby=startedby;
        this.startlocation = startlocation;
        this.destination=destination;
        this.startdate=startdate;
        this.expectedduration=expectedduration;
        this.malemembers=malemembers;
        this.femalemembers=femalemembers;
        this.number_of_members=number_of_members;
        this.id_ = id_;
        this.teamimage=teamimageurl;
    }

    public String getTeamname()
    {
        return  "TEAM NAME:"+teamname;
    }

    public String getStartlocation()
    {
        return  "START LOCATION:"+startlocation;
    }

    public String getStartdate() {
        return "START DATE:"+startdate;
    }

    public String getNumber_of_members() {
        return "NUMBER OF MEMBERS:"+number_of_members;
    }

    public String getTeamimage()
    {
        return  teamimage;
    }

    public int getId()
    {
        return id_;
    }

    public String getDescription() {
        return "DESCRIPTION:"+description;
    }

    public String getStartedby() {
        return "STARTED BY:"+startedby;
    }

    public String getDestination() {
        return "DESTINATION:"+destination;
    }

    public String getFemalemembers() {
        return "FEMALE MEMBERS:"+femalemembers;
    }

    public String getMalemembers() {
        return "MALE MEMBERS:"+malemembers;
    }

    public String getExpectedduration() {
        return "EXPECTED DURATION:"+expectedduration;
    }
}
