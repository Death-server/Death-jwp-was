package webserver;


import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class HttpReqeustTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception{
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        HttpReqeust request = new HttpReqeust(in);
        assertEquals("GET", request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception{
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpReqeust request = new HttpReqeust(in);

        assertEquals("POST", request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

}