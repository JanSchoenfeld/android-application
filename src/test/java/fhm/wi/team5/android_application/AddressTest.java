package fhm.wi.team5.android_application;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fhm.wi.team5.android_application.model.Address;

import static org.junit.Assert.assertEquals;

/**
 * @author Jan Schönfeld
 */

public class AddressTest {

    Address address;

    @Before
    public void setUp(){
        address = new Address("Langestraße", "31", "44137", "Dortmund", "nope");
        address.setLatitude(43.4);
        address.setLongitude(12.3);
    }

    @Test
    public void testStreet(){
       assertEquals("Langestraße", address.getStreet());
    }

    @Test
    public void testSetStreet(){
        address.setStreet("Kurzestraße");
        assertEquals("Kurzestraße", address.getStreet());
    }

    @Test
    public void testHousenumber(){
        assertEquals("31", address.getHouseNumber());
    }

    @Test
    public void testSetHousenumber(){
        address.setHouseNumber("12");
        assertEquals("12", address.getHouseNumber());
    }

    @Test
    public void testAdditionalInfo(){
        assertEquals("nope", address.getAdditionalInfo());
    }

    @Test
    public void testSetAdditionalInfo(){
        address.setAdditionalInfo("joa");
        assertEquals("joa", address.getAdditionalInfo());
    }

    @Test
    public void testCity(){
        assertEquals("Dortmund", address.getCity());
    }

    @Test
    public void testSetCity(){
        address.setCity("Münster");
        assertEquals("Münster", address.getCity());
    }

    @Test
    public void testLongitude(){
        assertEquals(12.3, address.getLongitude(), 0);
    }

    @Test
    public void testSetLongitude(){
        address.setLongitude(3.4);
        assertEquals(3.4, address.getLongitude(), 0);
    }

    @Test
    public void testLatitude(){
        assertEquals(43.4, address.getLatitude(), 0);
    }

    @Test
    public void testSetLatitude(){
        address.setLatitude(28.3);
        assertEquals(28.3, address.getLatitude(), 0);
    }

    @Test
    public void testZipCode(){
        assertEquals("44137", address.getZipCode());
    }

    @Test
    public void testSetZipCode(){
        address.setZipCode("48163");
        assertEquals("48163", address.getZipCode());
    }

}
