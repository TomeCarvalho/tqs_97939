package tqs.n97939.covid.controller;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.client.methods.RequestBuilder;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api")
public class Controller {
    // @Value("${X-RapidAPI-Host}")
    private final String host = "covid-193.p.rapidapi.com";

    // @Value("${X-RapidAPI-Key}")
    private final String key = "2b40038946msh38761268e33a7fbp1c4bfejsn17b9b063c6b4";

    private final String url = "https://" + host;

    @GetMapping("/countries")
    public ResponseEntity<String> getCountries(@RequestParam(required = false) String search) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url + "/countries");
            if (search != null)
                uriBuilder.addParameter("search", search);
            URI uri = uriBuilder.build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("X-RapidAPI-Host", host)
                    .header("X-RapidAPI-Key", key)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new ResponseEntity<>(response.body(), HttpStatus.valueOf(response.statusCode()));
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
