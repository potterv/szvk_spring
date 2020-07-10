package ru.pfr.szvk.readwritefiles.fromfms;

/**
 * Класс в который загружается информация о  подтверждении
 * проживания гражданина на момент референдума в Крыму
 * 18.03.2014
 * @author 092goncharVV
 */
public class AdrRowFromFms extends RowFromFms {
    public AdrRowFromFms(){
        super();
    }
    @Override
    public String toString() {
        return String.join("","AdrRowFromFms{ ",super.toString()," }",", country - ",this.country,", area - ",this.area,", region - ",this.region,", city - ",this.city) ;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String country;
    private String area;
    private String region;
    private String city;


}
