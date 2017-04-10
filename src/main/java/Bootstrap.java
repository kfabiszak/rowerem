import static spark.Spark.*;
import services.JSONTransformer;
import travel.Route;
import user.Registered;

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
        JSONTransformer jsonTransformer = new JSONTransformer();

        get("/test-get", (request, response) -> {
            return "MSG TO CLIENT"; //Transfer object toJson
        });

        post("/route", (request, response) -> {
            Route element = jsonTransformer.fromJson(request.body(), Route.class);
            System.out.println("jest odp");
            return element;
        });

        post("/login", (request, response) -> {
            Registered element = jsonTransformer.fromJson(request.body(), Registered.class);
            return element;
        });

    }
}