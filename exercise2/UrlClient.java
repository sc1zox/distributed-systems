package exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class UrlClient {
    public static void main(String[] args) throws IOException {
        String address = args.length > 0 ? args[0] : "stud.fh-wedel.de/index.html";
        URL url = URI.create(address).toURL();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}

/*
Here you can only see the body. URL constructs the request line and the headers itself.
You cannot see the status line or the headers.
`new URL(String)` is deprecated in newer versions of Java. That is why we use `URI.create(...).toURL()`.
 */