/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.google.maps.api;

import com.google.maps.GeoApiContext;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 *
 * @author monday
 */
public class GoogleServiceTest {
    
    private GoogleService service;
    private GoogleApiSingleton gApi;
    
    public GoogleServiceTest() {
    }
    
    @Before
    public void setUp() {
        gApi = Mockito.mock(GoogleApiSingleton.class);
        service = new GoogleService(gApi);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of directionsFromAddress method, of class GoogleService.
     */
    @Test
    public void testAddressToCoords() throws Exception {
        
        String testString = "Keplera, Poznań, Poland";
        GeocodingResult res = new GeocodingResult();
        res.geometry = new Geometry();
        res.geometry.location = new LatLng(0, 0);
        GeocodingResult list[] = {res};
        
        Mockito.when(gApi.geocodingGeocode(Matchers.any(), Matchers.any())).thenReturn(list);
        
        service.addressToCoords(testString);
        Mockito.verify(gApi).geocodingGeocode(Matchers.isA(GeoApiContext.class), Matchers.eq(testString));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddressToCoordsNothing() throws Exception {
        String testString = "xaxaxaxaxaxa";
        GeocodingResult list[] = {};
        
        Mockito.when(gApi.geocodingGeocode(Matchers.any(), Matchers.any())).thenReturn(list);
        
        service.addressToCoords(testString);
        Mockito.verify(gApi).geocodingGeocode(Matchers.isA(GeoApiContext.class), Matchers.eq(testString));

    }

    /**
     * Test of addressToCoords method, of class GoogleService.
     */
    @Test
    public void  testDirectionsFromAddress() throws Exception {
        String testBegin = "Keplera, Poznań, Poland";
        String testEnd = "Polna, Poznań, Poland";
        
        service.directionsFromAddress(testBegin, testEnd, true);
        Mockito.verify(gApi).directionsNewRequestAddress(Matchers.isA(GeoApiContext.class), Matchers.eq(testBegin), Matchers.eq(testEnd), Matchers.eq(true));
    }

    /**
     * Test of directionsFromCoords method, of class GoogleService.
     */
    @Test
    public void testDirectionsFromCoords() throws Exception {
        //Coords versions of previous testBegin and testEnd
        LatLng testBegin = new LatLng(52.390388, 16.864202);
        LatLng testEnd = new LatLng(52.409806, 16.89492);
        
        service.directionsFromCoords(testBegin, testEnd, true);
        Mockito.verify(gApi).directionsNewRequestCoords(Matchers.isA(GeoApiContext.class), Matchers.eq(testBegin), Matchers.eq(testEnd), Matchers.eq(true));
    }
    
    
    
}
