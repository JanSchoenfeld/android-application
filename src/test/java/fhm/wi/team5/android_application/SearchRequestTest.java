package fhm.wi.team5.android_application;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import fhm.wi.team5.android_application.transfer.SearchRequest;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jan Schönfeld on 25.06.2017.
 */

public class SearchRequestTest {

    SearchRequest request;

    @Before
    public void setUp(){
        request = new SearchRequest();
        request.setLatitude(7.12);
        request.setLongitude(52.12);
        request.setType("CITY");
    }

    @Test
    public void testConstructor(){
        request = new SearchRequest(7.12, 52.12, "CITY");
        assertEquals(SearchRequest.class, request.getClass());
    }

    @Test
    public void testLatitude(){
        assertEquals(7.12, request.getLatitude(), 0);
    }

    @Test
    public void testSetLatitude(){
        request.setLatitude(15.5);
        assertEquals(15.5, request.getLatitude(), 0);
    }

    @Test
    public void testLongitude(){
        assertEquals(52.12, request.getLongitude(), 0);
    }

    @Test
    public void testSetLongitude(){
        request.setLongitude(80.9);
        assertEquals(80.9, request.getLongitude(), 0);
    }

    @Test
    public void testType(){
        assertEquals("CITY", request.getType());
    }

    @Test
    public void testSetType(){
        request.setType("RACER");
        assertEquals("RACER", request.getType());
    }
}
