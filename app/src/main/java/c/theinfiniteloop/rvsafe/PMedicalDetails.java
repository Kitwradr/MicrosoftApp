package c.theinfiniteloop.rvsafe;

public class PMedicalDetails {

    public String name;

    public String age;

    public String user_id;

    public String blood;

    public String height;

    public String weight;

    public String medical_condition;

    public String allergy;

    public String notes;

    public String pitureLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMedical_condition() {
        return medical_condition;
    }

    public void setMedical_condition(String medical_condition) {
        this.medical_condition = medical_condition;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPitureLink() {
        return pitureLink;
    }

    public void setPitureLink(String pitureLink) {
        this.pitureLink = pitureLink;
    }

    @Override
    public String toString() {
        return "PMedicalDetails{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", user_id='" + user_id + '\'' +
                ", blood='" + blood + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", medical_condition='" + medical_condition + '\'' +
                ", allergy='" + allergy + '\'' +
                ", notes='" + notes + '\'' +
                ", pitureLink='" + pitureLink + '\'' +
                '}';
    }
}
