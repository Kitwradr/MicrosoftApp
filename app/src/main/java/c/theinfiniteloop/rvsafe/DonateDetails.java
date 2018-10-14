package c.theinfiniteloop.rvsafe;

import java.util.List;

public class DonateDetails {

    public String id;

    public String phone_number;

    public String Address;

    public String City;

    public List<String> items ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "DonateDetails{" +
                "id='" + id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", Address='" + Address + '\'' +
                ", City='" + City + '\'' +
                ", items=" + items +
                '}';
    }
}
