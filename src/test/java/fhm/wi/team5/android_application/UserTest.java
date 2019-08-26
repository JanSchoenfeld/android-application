package fhm.wi.team5.android_application;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import fhm.wi.team5.android_application.model.Address;
import fhm.wi.team5.android_application.model.User;

/**
 * Created by Jan Schönfeld on 25.06.2017.
 */

public class UserTest {

    User user;
    Address address;

    @Before
    public void setUp(){
        user = new User(1, "Bernd", "", "bernd@bernd.de", "123456789", "bernd@bernd.de");
        address = new Address("Hallostr", "31", "41234", "Dortmund", "nope");
    }

    @Test
    public void testUsername(){
        Assert.assertEquals("Bernd", user.getUsername());
    }

    @Test
    public void testSetUsername(){
        user.setUsername("Dieter");
        Assert.assertEquals("Dieter", user.getUsername());
    }

    @Test
    public void testId(){
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void testSetId(){
        user.setId(2);
        Assert.assertEquals(2, user.getId());
    }


    @Test
    public void testImageUrl(){
        Assert.assertEquals("", user.getImg_url());
    }

    @Test
    public  void testSetImageUrl(){
        user.setImg_url("image.url.com");
        Assert.assertEquals("image.url.com", user.getImg_url());
    }

    @Test
    public void testEmail(){
        Assert.assertEquals("bernd@bernd.de", user.getEmail());
    }

    @Test
    public void testSetEmail(){
        user.setEmail("dieter@dieter.de");
        Assert.assertEquals("dieter@dieter.de", user.getEmail());
    }

    @Test
    public void testGetName(){
        Assert.assertEquals("bernd@bernd.de", user.getName());
    }

    @Test
    public void testSetName(){
        user.setName("dieter@dieter.de");
        Assert.assertEquals("dieter@dieter.de", user.getName());
    }

    @Test
    public void testPhone(){
        Assert.assertEquals("123456789", user.getPhoneNumber());
    }

    @Test
    public void testSetPhone(){
        user.setPhoneNumber("4321");
        Assert.assertEquals("4321", user.getPhoneNumber());
    }

    @Test
    public void testAddress(){
        user.setAddress(address);
        Assert.assertEquals(address, user.getAddress());
    }
}
