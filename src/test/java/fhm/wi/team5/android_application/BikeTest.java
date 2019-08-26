package fhm.wi.team5.android_application;

import org.junit.Before;
import org.junit.Test;

import fhm.wi.team5.android_application.model.Bike;
import fhm.wi.team5.android_application.model.User;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.type;
import static org.junit.Assert.assertEquals;

/**
 * @author Jan Schönfeld
 */

public class BikeTest {

    Bike bike;
    Bike bike2;
    User user;

    @Before
    public void setUp(){
        user = new User();
        bike = new Bike();
        bike.setId(1);
        bike.setUser(user);
        bike.setDeleted(false);
        bike.setImageUrl("image.url.com");
        bike.setLocked(false);
        bike.setName("Bernhard");
        bike.setPrice(19);
        bike.setType("MOUNTAIN");
    }

    @Test
    public void testConstructor(){
        bike2 = new Bike(3, "Arne", 13, "image", false, false, "CITY", user);
        assertEquals(false, bike.isLocked());
    }

    @Test
    public void testId(){
        assertEquals(1, bike.getId());
    }

    @Test
    public void testSetId(){
        bike.setId(2);
        assertEquals(2, bike.getId());
    }

    @Test
    public void testUser(){
        assertEquals(user, bike.getUser());
    }

    @Test
    public void testImageUrl(){
        assertEquals("image.url.com", bike.getImageUrl());
    }

    @Test
    public void testSetImageUrl(){
        bike.setImageUrl("image.url.de");
        assertEquals("image.url.de", bike.getImageUrl());
    }

    @Test
    public void testLocked(){
        assertEquals(false, bike.isLocked());
    }

    @Test
    public void testSetLocked(){
        bike.setLocked(true);
        assertEquals(true, bike.isLocked());
    }

    @Test
    public void testName(){
        assertEquals("Bernhard", bike.getName());
    }

    @Test
    public void testSetName(){
        bike.setName("Rüdiger");
        assertEquals("Rüdiger", bike.getName());
    }

    @Test
    public void testPrice(){
        assertEquals(19, bike.getPrice(), 0);
    }

    @Test
    public void testSetPrice(){
        bike.setPrice(20);
        assertEquals(20, bike.getPrice(), 0);
    }

    @Test
    public void testType(){
        assertEquals("MOUNTAIN", bike.getType());
    }

    @Test
    public void testSetType(){
        bike.setType("CITY");
        assertEquals("CITY", bike.getType());
    }
}
