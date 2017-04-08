import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class Bootstrap {

    public static void main(String[] args) {
        get("/hello", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                System.out.println(request.params("adupa"));
                return "Hello World!!";
            }
        });
    }
}