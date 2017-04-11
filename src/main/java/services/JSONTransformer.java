package services;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;

/**
 * Class that helps to save Object to JSON and retrive Object from JSON.
 */
public class JSONTransformer {

    /**
     * Transform Object to JSON.
     * @param object Object to transform.
     * @return String containing JSON
     */
    public String toJson(Object object) {
        return new Gson().toJson(object);
    } 

    /**
     * Retrieves Object of T from JSON 
     * @param <T> Type of retrieved Object.
     * @param json String containing JSON
     * @param classe Class of Object to retrieve from JSON
     * @return retrieved Object of T
     */
    public <T extends Object> T  fromJson(String json, Class<T> classe) {
        return new Gson().fromJson(json, classe);
    }

    /**
     * Packs everything from a Reader to String.
     * @param rd Reader to read
     * @return String containing all of Reader content
     * @throws IOException
     */
    public String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
