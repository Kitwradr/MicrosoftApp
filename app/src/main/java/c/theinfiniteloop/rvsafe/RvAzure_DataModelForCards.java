package c.theinfiniteloop.rvsafe;

public class RvAzure_DataModelForCards
{
    String disastername;
    String disastertype;
    int id_;
    int image;

    public RvAzure_DataModelForCards(String disastername, String disastertype, int id_, int image) {
        this.disastername ="  "+disastername;
        this.disastertype = "TYPE: "+disastertype;
        this.id_ = id_;
        this.image=image;
    }

    public String getName()
    {
        return  disastername;
    }

    public String getVersion()
    {
        return  disastertype;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }
}