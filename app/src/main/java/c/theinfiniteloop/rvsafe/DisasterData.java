package c.theinfiniteloop.rvsafe;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("DisasterData")
public class DisasterData {

    @Id
    public int disaster_id;

    public String disaster_name;

    public String disaster_type;

    public String image_url;

    public int wantToHelp_id;

    public DisasterData(int disaster_id, String disaster_name, String disaster_type, String image_url, int wantToHelp_id) {
        this.disaster_id = disaster_id;
        this.disaster_name = disaster_name;
        this.disaster_type = disaster_type;
        this.image_url = image_url;
        this.wantToHelp_id = wantToHelp_id;
    }

    public DisasterData() {
    }

    public int getDisaster_id() {
        return disaster_id;
    }

    public void setDisaster_id(int disaster_id) {
        this.disaster_id = disaster_id;
    }

    public String getDisaster_name() {
        return disaster_name;
    }

    public void setDisaster_name(String disaster_name) {
        this.disaster_name = disaster_name;
    }

    public String getGetDisaster_type() {
        return disaster_type;
    }

    public void setDisaster_type(String disaster_type) {
        this.disaster_type = disaster_type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getWantToHelp_id() {
        return wantToHelp_id;
    }

    public void setWantToHelp_id(int wantToHelp_id) {
        this.wantToHelp_id = wantToHelp_id;
    }

    @Override
    public String toString() {
        return "DisasterData{" +
                "disaster_id=" + disaster_id +
                ", disaster_name='" + disaster_name + '\'' +
                ", disaster_type='" + disaster_type + '\'' +
                ", image_url='" + image_url + '\'' +
                ", wantToHelp_id=" + wantToHelp_id +
                '}';
    }
}
