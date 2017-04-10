package travel;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;

public class Route {

    private String origin; //gdzie znajduje sie użytkownik
    private String destination; //gdzie chce dojechać
    private Station startStation; //z jakiej stacji ma wziac rower
    private Station endStation; //gdzie ma zostawić rower
    private DirectionsResult directions;

    public Route(DirectionsResult dir) {
        this.directions = dir;

    }

    //ogolnie tutaj mozemy pobierac z googleService całe to DirectionsResult i juz tutaj sobie wyciagac co trzeba z tych tras
    //gdzies sie bedziemy tez bawili tymi dystansami i czasem ale nie wiem czy jakas nowa klase do tego czy tutaj pola po prostu

    /*TODO tutaj bym pobierał z klienta gdzie jest i gdzie chce być. (+opcja jezeli wybrał od razu stacje a nie jakis adres)
      Szukam najbliżej stacji (nextbikeAPI) od punktu startowego i najbliższej od końcowego wyznaczam trasę (googleAPI)
      Dodatkowo wyznaczam 2 trasy do stacji i ze stacji (googleAPI - tam jest opcja WALKING)*/

}
