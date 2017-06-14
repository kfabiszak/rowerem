package services.nextbike.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.nextbike.api.structure.City;

import static org.junit.Assert.*;

/**
 * Created by memphis on 14.05.17.
 */
public class NextBikeServiceTest {

    public NextBikeService nextBikeService;

    @Before
    public void setUp() throws Exception {
        nextBikeService = new NextBikeService();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findCityCorrect() throws Exception {
        City city = nextBikeService.findCity("PL","Poznań");
        assertEquals(192, city.getUid());
    }

    @Test
    public void findCityDoesntExist() throws Exception {
        City city = nextBikeService.findCity("XX","Xtowo");
        assertNull(city);
    }

    @Test
    public void distanceSamePoint() throws Exception {
        assertEquals(0.0,nextBikeService.distance(10.0,10.0,10.0,10.0, 0.0, 0.0), 0.001);
    }

    @Test
    public void distanceTwoPoints() throws Exception {
        assertEquals(8912656.777,nextBikeService.distance(10.0,100.0,10.0,100.0, 0.0, 0.0), 0.001);
    }

//    @Test
//    public void findClosestAtTheStation() throws Exception {
//        Place place = new Place(52.402831256725,16.911622881889);
//        Station station = nextBikeService.findClosest(nextBikeService.getRoot().countries.get(11).getCities().get(0), place);
//        assertEquals(nextBikeService.getRoot().countries.get(11).getCities().get(0).getStations().get(0), station); //Poznań Główny
//    }
//
//    @Test
//    public void findClosestToPoint() throws Exception {
//        Place place = new Place(52.382176, 16.916346);
//        Station station = nextBikeService.findClosest(nextBikeService.getRoot().countries.get(11).getCities().get(0), place);
//        assertEquals(nextBikeService.getRoot().countries.get(11).getCities().get(0).getStations().get(83), station); //Rolna
//    }

    @Test
    public void findStations() throws Exception {
    }

}