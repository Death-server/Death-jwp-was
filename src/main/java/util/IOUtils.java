package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class IOUtils {

    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static byte[] getFileText(String url) {
        final String BASE_URL = "webapp";
        try {
            File file = new File(BASE_URL + url);
            if(file.isFile()) {
                return Files.readAllBytes(file.toPath());
            }
        } catch(IOException ioException) {
            // 404 Not Found
            logger.error(ioException.getMessage());
        }
        return null;
    }
}
