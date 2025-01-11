package com.wsz.protocol;

import com.wsz.common.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    public String send(String hostname, int port, Invocation data) {
        // 1. 建立连接
        try {
            URL url = new URL("http", hostname, port, "/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            //配置
            OutputStream outputStream = urlConnection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            oos.writeObject(data);
            oos.flush();
            oos.close();

            InputStream inputStream = urlConnection.getInputStream();
            return IOUtils.toString(inputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
