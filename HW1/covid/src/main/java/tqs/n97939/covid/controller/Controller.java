package tqs.n97939.covid.controller;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class Controller {
    @Value("${X-RapidAPI-Host}")
    private String host;

    @Value("${X-RapidAPI-Key}")
    private String key;

    private final String url = "https://" + host;
    private final CloseableHttpClient client = HttpClients.createDefault();

    public Controller() throws MalformedURLException {
    }

    @GetMapping("/countries")
    public ResponseEntity<String> getCountries() {
        HttpGet get = new HttpGet(url);
        URI uri = new URIBuilder(get.getURI())
                .addParameter("")
    }
}
