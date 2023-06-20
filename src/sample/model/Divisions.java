package sample.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Divisions {

    private Integer divisionId;
    private String division;
    private Integer countryId;

    public Divisions(Integer divisionId, String division, Integer countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString(){
        return (Integer.toString(getDivisionId()) + " " + getDivision() + " " + getCountryId());
    }
}
