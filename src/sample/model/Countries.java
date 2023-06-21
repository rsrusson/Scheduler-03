package sample.model;

public class Countries {

    private Integer countryId;
    private String country;
    //private LocalDateTime createDate;
    //private String createdBy;
    //private Timestamp lastUpdate;
    //private String lastUpdatedBy;

    public Countries(Integer countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString(){
        return (String.valueOf(getCountryId()));
    }
}
