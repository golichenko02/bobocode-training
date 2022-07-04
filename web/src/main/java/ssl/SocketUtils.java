package ssl;

import lombok.Cleanup;
import lombok.SneakyThrows;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.net.URL;

public class SocketUtils {

    @SneakyThrows
    public static Socket createSocketClient(URL url) {
        String host = url.getHost();
        return isSecured(url) ? (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, url.getDefaultPort()) : new Socket(host, getPort(url));
    }


    @SneakyThrows
    public static String doGet(Socket socket, URL url) {
        boolean isSecuredRequest = isSecured(url);
        @Cleanup BufferedReader reader = getReader(socket);
        @Cleanup PrintWriter writer = getWriter(socket);

        if (isSecuredRequest) {
            ((SSLSocket) socket).startHandshake();
        }

        writer.write("GET " + url + " HTTP/1.1\n");
        writer.write("Host: " + url.getHost() + "\n");
        writer.write("Connection: close\n\n");
        writer.flush();

        if (writer.checkError()) {
            System.err.println("SocketClient error!");
        }

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("{") || line.contains("}")) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    @SneakyThrows
    public static String doHead(Socket socket, URL url) {
        boolean isSecuredRequest = isSecured(url);
        @Cleanup BufferedReader reader = getReader(socket);
        @Cleanup PrintWriter writer = getWriter(socket);

        if (isSecuredRequest) {
            ((SSLSocket) socket).startHandshake();
        }

        writer.write("HEAD " + url + " HTTP/1.1\n");
        writer.write("Host: " + url.getHost() + "\n\n");
        writer.write("Connection: close\n\n");
        writer.flush();

        if (writer.checkError()) {
            System.err.println("SocketClient error!");
        }

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        return stringBuilder.toString();
    }

    @SneakyThrows
    private static BufferedReader getReader(Socket socket) {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @SneakyThrows
    private static PrintWriter getWriter(Socket socket) {
        return new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private static boolean isSecured(URL url) {
        return url.toString().contains("https");
    }

    private static int getPort(URL url) {
        int port = url.getPort();
        return port == -1 ? url.getDefaultPort() : port;
    }
}
