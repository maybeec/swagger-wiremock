package uk.co.culturetrip.qa;


import org.junit.*;

import java.net.HttpURLConnection;
import java.net.URL;

public class SwaggerWiremockTest {

    MyStub myStub = new MyStub();


    @Before
    public void setUp() {
        myStub.configureStub("9988", "src/test/resources/openApi.json", "src/test/resources" );
        myStub.start();

    }

    @After
    public void tearDown() {
        myStub.stop();
    }

    @Test
    public void basicHappyPathTest() throws Exception {
        URL url = new URL("http://localhost:9988/cars");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-type", "application/json");
        con.setRequestProperty("Accept", "*/*");
        String response = con.getResponseMessage();
        Assert.assertEquals("OK", response);
    }

    @Test
    public void basicSadPathTest() throws Exception {
        URL url = new URL("http://localhost:9988/car");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-type", "application/json");
        con.setRequestProperty("Accept", "*/*");
        String response = con.getResponseMessage();
        Assert.assertEquals("Invalid contractValidation failed.?[ERROR] No API path found that matches request '/car'.", response);
    }


    public static class MyStub extends SwaggerWiremock {
    }
}
