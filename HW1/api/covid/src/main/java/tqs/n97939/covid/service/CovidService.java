package tqs.n97939.covid.service;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import tqs.n97939.covid.cache.RequestCache;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CovidService {
    // @Value("${X-RapidAPI-Host}")
    private final Logger logger = LoggerFactory.getLogger(CovidService.class);

    private final String host = "covid-193.p.rapidapi.com";

    private final String url = "https://" + host;

    private final RequestCache cache = new RequestCache(10000);

    public ResponseEntity<String> getCountries(@RequestParam(required = false) String search) {
        logger.info("getCountries called.");
        try {
            URIBuilder uriBuilder = new URIBuilder(url + "/countries");
            if (search != null)
                uriBuilder.addParameter("search", search);
            URI uri = uriBuilder.build();
            logger.info("Built URI: " + uri);
            HttpRequest request = createRapidApiGet(uri);
            HttpResponse<String> response;
            logger.info("Checking cache for request " + request);
            ResponseEntity<String> cacheRes = cache.get(request);
            if (cacheRes.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                logger.info("Not found in cache: request " + request + ". Sending request to API.");
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                ResponseEntity<String> responseEntity = new ResponseEntity<>(response.body(), HttpStatus.valueOf(response.statusCode()));
                cache.put(request, responseEntity);
                logger.info("Response cached for request: " + request);
                return responseEntity;
            }
            logger.info("Found in cache: request " + request);
            return cacheRes;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            logger.error("Caught exception\n" + e);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getCurrentStatistics(@RequestParam(required = false) String country) {
        logger.info("getCurrentStatistics called.");
        try {
            URIBuilder uriBuilder = new URIBuilder(url + "/statistics");
            if (country != null)
                uriBuilder.addParameter("country", country);
            URI uri = uriBuilder.build();
            logger.info("Built URI: " + uri);
            HttpRequest request = createRapidApiGet(uri);
            HttpResponse<String> response;
            logger.info("Checking cache for request " + request);
            ResponseEntity<String> cacheRes = cache.get(request);
            if (cacheRes.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                logger.info("Not found in cache: request " + request + ". Sending request to API.");
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                ResponseEntity<String> responseEntity = new ResponseEntity<>(response.body(), HttpStatus.valueOf(response.statusCode()));
                cache.put(request, responseEntity);
                logger.info("Response cached for request: " + request);
                return responseEntity;
            }
            logger.info("Found in cache: request " + request);
            return cacheRes;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            logger.error("Caught exception\n" + e);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getHistory(@RequestParam String country, @RequestParam(required = false) String day) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url + "/history");
            uriBuilder.addParameter("country", country);
            if (day != null)
                uriBuilder.addParameter("day", day);
            URI uri = uriBuilder.build();
            logger.info("Built URI: " + uri);
            HttpRequest request = createRapidApiGet(uri);
            HttpResponse<String> response;
            logger.info("Checking cache for request " + request);
            ResponseEntity<String> cacheRes = cache.get(request);
            if (cacheRes.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                logger.info("Not found in cache: request " + request + ". Sending request to API.");
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                ResponseEntity<String> responseEntity = new ResponseEntity<>(response.body(), HttpStatus.valueOf(response.statusCode()));
                cache.put(request, responseEntity);
                logger.info("Response cached for request: " + request);
                return responseEntity;
            }
            logger.info("Found in cache: request " + request);
            return cacheRes;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            logger.error("Caught exception\n" + e);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private HttpRequest createRapidApiGet(URI uri) {
        logger.info("createRapidApiGet called.");
        // @Value("${X-RapidAPI-Key}")
        String key = "2b40038946msh38761268e33a7fbp1c4bfejsn17b9b063c6b4";
        HttpRequest ret = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-RapidAPI-Host", host)
                .header("X-RapidAPI-Key", key)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        logger.info("Created HTTP Request: " + ret);
        return ret;
    }

    public ResponseEntity<String> getCacheStats() {
        return new ResponseEntity<>(String.format("{\n" +
                "    \"ttl\": %d,\n" +
                "    \"stats\": {\n" +
                "        \"count\": %d,\n" +
                "        \"hits\": %d,\n" +
                "        \"misses\": %d,\n" +
                "        \"hit_ratio\": %f,\n" +
                "        \"size\": %d\n" +
                "    }\n" +
                "}", cache.getTtl(), cache.getCount(), cache.getHitCount(), cache.getMissCount(), cache.hitRatio(), cache.size()), HttpStatus.OK);
    }
}
