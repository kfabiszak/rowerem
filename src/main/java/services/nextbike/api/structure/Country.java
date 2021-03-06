package services.nextbike.api.structure;

import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Represent a country.
 */
public class Country extends Place {

    @SerializedName("country")
    /**
     * Short name of the country.
     */
    private String country_shortname;
    /**
     * Name of the country.
     */
    private String country_name;
    /**
     * URL of the terms.
     */
    private String terms;
    /**
     * URL of the website.
     */
    private String website;
    /**
     * List of Cities.
     */
    private List<City> cities;

    public Country(double lat, double lng) {
        super(lat, lng);
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getTerms() {
        return terms;
    }

    public URL getWebsite() throws MalformedURLException {
        return new URL(website);
    }

    public List<City> getCities() {
        return cities;
    }

    public String getCountry_shortname() {
        return country_shortname;
    }
}
