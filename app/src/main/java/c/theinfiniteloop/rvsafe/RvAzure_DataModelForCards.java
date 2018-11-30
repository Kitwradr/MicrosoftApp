package c.theinfiniteloop.rvsafe;

public class RvAzure_DataModelForCards
{
    String disastername;
    String disastertype;
    int id_;
    String imageurl;

    public RvAzure_DataModelForCards(String disastername, String disastertype, int id_, String imageurl)
    {
        this.disastername = "  " + disastername;
        this.disastertype = "TYPE: " + disastertype;
        this.id_ = id_;
        this.imageurl = imageurl;
    }

    public String getName()
    {
        return disastername;
    }

    public String getVersion()
    {
        return disastertype;
    }

    public String getImage()
    {
        return imageurl;
    }

    public int getId() {
        return id_;
    }

}