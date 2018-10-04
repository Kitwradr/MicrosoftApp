package c.theinfiniteloop.rvsafe;

public class RvAzure_DataModelForVolunteerService
{
    String teamname;
    String startlocation;
    String startdate;
    String number_of_members;

    int id_;
    int teamimage;

    public RvAzure_DataModelForVolunteerService(String teamnamename,String startlocation,String startdate, String number_of_members, int id_, int teamimage)
    {
        this.teamname ="TEAM "+teamnamename;
        this.startlocation = "START LOCATION: "+startlocation;
        this.startdate="START DATE: "+startdate;
        this.number_of_members="NUMBER OF MEMBERS: "+number_of_members;
        this.id_ = id_;
        this.teamimage=teamimage;
    }

    public String getTeamname()
    {
        return  teamname;
    }

    public String getStartlocation()
    {
        return  startlocation;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getNumber_of_members() {
        return number_of_members;
    }

    public int getTeamimage()
    {
        return  teamimage;
    }

    public int getId()
    {
        return id_;
    }
}
