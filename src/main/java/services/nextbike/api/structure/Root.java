package services.nextbike.api.structure;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Root of data to process.
 */
public class Root {

    @SerializedName("countries")
    /**
     * List of countries.
     */
    public List<Country> countries;

}
