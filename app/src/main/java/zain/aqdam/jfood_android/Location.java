package zain.aqdam.jfood_android;

public class Location {
    private String province, description, city;

    public Location(String province, String description, String city) {
        this.province = province;
        this.description = description;
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
