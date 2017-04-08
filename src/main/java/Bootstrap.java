import com.google.gson.Gson;

import static spark.Spark.*;

class RouteModel {
    private String origin;
    private String destination;
}

class LoginModel {
    private String login;
    private String password;
}

class JsonTransformer {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static <T extends Object> T  fromJson(String json, Class<T> classe) {
        return new Gson().fromJson(json, classe);
    }

}

/*
    Dokumentacja: http://sparkjava.com/
    Obsługa spark + MongoDb: https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/

    Odbieranie parametrów:
     request.queryParams("param_name")
    Odbieranie wiadomości:
     Model_name element = jsonTransformer.fromJson(request.body(), Model_name.class);

    #TODO
    Klient przysyła informacje o trasie -> punkt startowy, docelowy, dodatkowe parametry
    Serwer znajduje optymalną trasę w NextBike Api
    Serwer wyznacza trasę w Google Map Api
    Serwer zwraca gotową trasę do klienta

    #TODO
    Obsługa logowania rejestracji z MongoDb
*/
public class Bootstrap {
    public static void main(String[] args) {
        JsonTransformer jsonTransformer = new JsonTransformer();

        get("/test-get", (request, response) -> {
            return "MSG TO CLIENT"; //Transfer object toJson
        });

        post("/route", (request, response) -> {
            RouteModel element = jsonTransformer.fromJson(request.body(), RouteModel.class);
            return element;
        });

        post("/login", (request, response) -> {
            LoginModel element = jsonTransformer.fromJson(request.body(), LoginModel.class);
            return element;
        });

    }
}