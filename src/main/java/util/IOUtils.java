package util;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength); //body에 저장하고, 0은 읽기를 시작할 배열의 인덱스, contentLength는 읽어들일 최대 길이.
        return String.copyValueOf(body); //문자 배열을 문자열로 변환.
    }
}
