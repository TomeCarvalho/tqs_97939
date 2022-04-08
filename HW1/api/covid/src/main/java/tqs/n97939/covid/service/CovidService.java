package tqs.n97939.covid.service;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CovidService {
    // @Value("${X-RapidAPI-Host}")
    private final String host = "covid-193.p.rapidapi.com";

    // @Value("${X-RapidAPI-Key}")
    private final String key = "2b40038946msh38761268e33a7fbp1c4bfejsn17b9b063c6b4";

    private final String url = "https://" + host;

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

    public ResponseEntity<String> getCurrentStatistics(@RequestParam(required = false) String country) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url + "/statistics");
            if (country != null)
                uriBuilder.addParameter("country", country);
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
