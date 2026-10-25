package exercise2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class UrlProxyClient {
    public static void main(String[] args) throws IOException {
        String address = args.length > 0 ? args[0] : "http://stud.fh-wedel.de/index.html";
        String proxyHost = args.length > 1 ? args[1] : "proxy.example.org";
        int proxyPort = args.length > 2 ? Integer.parseInt(args[2]) : 3128;

        URL url = URI.create(address).toURL();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        URLConnection connection = url.openConnection(proxy);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
