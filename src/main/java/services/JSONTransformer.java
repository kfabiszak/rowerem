package services;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;

public class JSONTransformer {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static <T extends Object> T  fromJson(String json, Class<T> classe) {
        return new Gson().fromJson(json, classe);
    }

    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
