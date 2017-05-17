package services.nextbike.api;

import com.squareup.okhttp.Route;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.nextbike.api.structure.City;
import services.nextbike.api.structure.Place;
import services.nextbike.api.structure.Station;

import static org.junit.Assert.*;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.verification.AtLeast;
import travel.RouteFromClient;

/**
 * Created by memphis on 14.05.17.
 */
public class NextBikeServiceTest {

    public NextBikeService nextBikeService;

    @Before
    public void setUp() throws Exception {
        nextBikeService = new NextBikeService("nextbike-official.json");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findCityCorrect() throws Exception {
        City city = nextBikeService.findCity("PL", "Poznań");
        assertEquals(192, city.getUid());
    }

    @Test
    public void findCityDoesntExist() throws Exception {
        City city = nextBikeService.findCity("XX", "Xtowo");
        assertNull(city);
    }

    @Test
    public void distanceSamePoint() throws Exception {
        assertEquals(0.0, nextBikeService.distance(10.0, 10.0, 10.0, 10.0, 0.0, 0.0), 0.001);
    }

    @Test
    public void distanceTwoPoints() throws Exception {
        assertEquals(8912656.777, nextBikeService.distance(10.0, 100.0, 10.0, 100.0, 0.0, 0.0), 0.001);
    }

    @Test
    public void distanceSymmetry() throws Exception {
        assertEquals(nextBikeService.distance(10.0, 100.0, 10.0, 100.0, 0, 0), nextBikeService.distance(100.0, 10.0, 100.0, 10.0, 0, 0), 0.000001);
    }

    @Test
    public void findClosestAtTheStation() throws Exception {
        Place place = new Place(52.402831256725, 16.911622881889);
        Station station = nextBikeService.findClosest(nextBikeService.getRoot().countries.get(11).getCities().get(0), place);
        assertEquals(nextBikeService.getRoot().countries.get(11).getCities().get(0).getStations().get(0), station); //Poznań Główny
    }

    @Test
    public void findClosestToPoint() throws Exception {
        Place place = new Place(52.382176, 16.916346);
        Station station = nextBikeService.findClosest(nextBikeService.getRoot().countries.get(11).getCities().get(0), place);
        assertEquals(nextBikeService.getRoot().countries.get(11).getCities().get(0).getStations().get(83), station); //Rolna
    }

    @Test
    public void findStationsCalls() throws Exception {
        RouteFromClient route = Mockito.mock(RouteFromClient.class);
        City city = Mockito.mock(City.class);
        List<Station> stations = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            stations.add(new Station(i, i));
        }
        Mockito.when(route.getCity()).thenReturn(city);
        Mockito.when(route.getOriginLat()).thenReturn(0.0);
        Mockito.when(route.getOriginLng()).thenReturn(0.0);
        Mockito.when(route.getStartStation()).thenReturn(new Station(0, 0));
        Mockito.when(route.getEndStation()).thenReturn(new Station(1, 1));

        Mockito.when(city.getStations()).thenReturn(stations);

        nextBikeService.findStations(route);

        Mockito.verify(route, Mockito.times(1)).setCity(Matchers.any());
        Mockito.verify(route, Mockito.atLeast(20)).getStartStation();
    }

    @Test
    public void findStationsNone() throws Exception {
        RouteFromClient route = Mockito.mock(RouteFromClient.class);
        City city = Mockito.mock(City.class);
        List<Station> stations = new LinkedList<>();

        Mockito.when(route.getCity()).thenReturn(city);
        Mockito.when(route.getOriginLat()).thenReturn(0.0);
        Mockito.when(route.getOriginLng()).thenReturn(0.0);
        Mockito.when(route.getStartStation()).thenReturn(new Station(0, 0));
        Mockito.when(route.getEndStation()).thenReturn(new Station(1, 1));
        Mockito.when(city.getStations()).thenReturn(stations);

        nextBikeService.findStations(route);
        
        Mockito.verify(route).getCity();
        Mockito.verify(route, Mockito.never()).getStartStation();
    }

    @Test
    public void findClosestCalls() throws Exception {

        Place place = Mockito.mock(Place.class);
        City city = Mockito.mock(City.class);
        List<Station> stations = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            stations.add(new Station(i, i));
        }

        Mockito.when(place.getLat()).thenReturn(0.0);
        Mockito.when(place.getLng()).thenReturn(0.0);
        Mockito.when(city.getStations()).thenReturn(stations);

        nextBikeService.findClosest(city, place);

        Mockito.verify(place, Mockito.atLeast(20)).getLat();
        Mockito.verify(city, Mockito.atLeastOnce()).getStations();

    }

    @Test
    public void findClosestNone() throws Exception {
        Place place = Mockito.mock(Place.class);
        City city = Mockito.mock(City.class);
        List<Station> stations = new LinkedList<>();

        Mockito.when(place.getLat()).thenReturn(0.0);
        Mockito.when(place.getLng()).thenReturn(0.0);
        Mockito.when(city.getStations()).thenReturn(stations);

        assertNull(nextBikeService.findClosest(city, place));

        Mockito.verify(place, Mockito.never()).getLat();
        Mockito.verify(city).getStations();

    }

}
