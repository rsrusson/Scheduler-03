package sample.model;

public class Countries {

    private int countryId;
    private String country;
    //private LocalDateTime createDate;
    //private String createdBy;
    //private Timestamp lastUpdate;
    //private String lastUpdatedBy;

    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
