/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.maps.model.LatLng;
import java.io.EOFException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 *
 * @author monday
 */
public class JSONTransformerTest {
    
    private JSONTransformer jsont;
    
    public JSONTransformerTest() {
    }
    
    @Before
    public void setUp() {
        jsont = new JSONTransformer();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toJson method, of class JSONTransformer.
     */
    @Test
    public void testToJson() {
        LatLng coords = new LatLng(10.013, 9.88);
        String json = "{\"lat\":10.013,\"lng\":9.88}";
        assertEquals(jsont.toJson(coords), json);
    }
    

    /**
     * Test of fromJson method, of class JSONTransformer.
     */
    @Test
    public void testFromJson() {
        String json = "{\"lat\":10.013,\"lng\":9.88}";
        LatLng coords = new LatLng(10.013, 9.88);
        LatLng coords2 = jsont.fromJson(json, LatLng.class);
        
        assertEquals(coords.lat, coords2.lat, 0.00001);
        assertEquals(coords.lng, coords2.lng, 0.00001);
    }
    
    @Test(expected = JsonSyntaxException.class)
    public void testFromJsonSyntaxError() throws Exception {
        String json = "{\"lat\":10.013,\"lng\":9.88}}";
        LatLng coords = new LatLng(10.013, 9.88);
        LatLng coords2 = jsont.fromJson(json, LatLng.class);
        
        assertEquals(coords.lat, coords2.lat, 0.00001);
        assertEquals(coords.lng, coords2.lng, 0.00001);
    }
    
    @Test(expected = NumberFormatException.class)
    public void testFromJsonTypeError() throws Exception {
        String json = "{\"lat\":ABC,\"lng\":9.88}";
        LatLng coords = new LatLng(10.013, 9.88);
        LatLng coords2 = jsont.fromJson(json, LatLng.class);
        
        assertEquals(coords.lat, coords2.lat, 0.00001);
        assertEquals(coords.lng, coords2.lng, 0.00001);
    }
    
    /**
     * Test of readAll method, of class JSONTransformer.
     */
    @Test
    public void testReadAll() throws Exception {
        String test = "Wlazł kotek na płotek i mruga.";
        Reader reader = new StringReader(test);
        
        assertEquals(jsont.readAll(reader), test);;
        
    }
    
}
