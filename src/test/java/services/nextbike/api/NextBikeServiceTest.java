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
    public void findCityTestCorrect() throws Exception {
        City city = nextBikeService.findCity("PL","Poznań");
        assertEquals(192, city.getUid());
    }

    @Test
    public void distance() throws Exception {
    }

    @Test
    public void findClosest() throws Exception {
    }

    @Test
    public void findStations() throws Exception {
    }

}