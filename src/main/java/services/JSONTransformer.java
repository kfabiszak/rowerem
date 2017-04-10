package services;

import com.google.gson.Gson;

public class JSONTransformer {

        public static String toJson(Object object) {
            return new Gson().toJson(object);
        }

        public static <T extends Object> T  fromJson(String json, Class<T> classe) {
            return new Gson().fromJson(json, classe);
        }

}
