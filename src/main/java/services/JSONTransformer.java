package services;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;

public class JSONTransformer {

    public String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public <T extends Object> T  fromJson(String json, Class<T> classe) {
        return new Gson().fromJson(json, classe);
    }

    public String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
