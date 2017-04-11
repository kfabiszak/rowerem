package travel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Country extends Place {

    private String country_name;
    private String terms;
    private String website;
    private List<City> cities;

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
}
