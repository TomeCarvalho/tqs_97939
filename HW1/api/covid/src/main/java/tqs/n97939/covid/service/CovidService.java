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
import java.text.MessageFormat;

@Service
public class CovidService {
    private final Logger logger = LoggerFactory.getLogger(CovidService.class);

    private static final String HOST = "covid-193.p.rapidapi.com";

    private static final String URL = "https://" + HOST;

    private final RequestCache cache = new RequestCache(10000);

    private static final String CAUGHT_EXCEPTION = "Caught exception.\n";

    private static final String CAUGHT_EXCEPTION_FORMAT = "{0} {1}";

    public ResponseEntity<String> getCountries(@RequestParam(required = false) String search) {
        logger.info("getCountries called.");
        try {
            URIBuilder uriBuilder = new URIBuilder(URL + "/countries");
            if (search != null)
                uriBuilder.addParameter("search", search);
            return getStringResponseEntity(uriBuilder);
        } catch (IOException | URISyntaxException e) {
            logger.error(MessageFormat.format(CAUGHT_EXCEPTION_FORMAT, CAUGHT_EXCEPTION, e));
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            logger.error(MessageFormat.format(CAUGHT_EXCEPTION_FORMAT, CAUGHT_EXCEPTION, e));
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getCurrentStatistics(@RequestParam(required = false) String country) {
        logger.info("getCurrentStatistics called.");
        try {
            URIBuilder uriBuilder = new URIBuilder(URL + "/statistics");
            if (country != null)
                uriBuilder.addParameter("country", country);
            return getStringResponseEntity(uriBuilder);
        } catch (IOException | URISyntaxException e) {
            logger.error(MessageFormat.format(CAUGHT_EXCEPTION_FORMAT, CAUGHT_EXCEPTION, e));
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            logger.error(MessageFormat.format(CAUGHT_EXCEPTION_FORMAT, CAUGHT_EXCEPTION, e));
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getHistory(@RequestParam String country, @RequestParam(required = false) String day) {
        try {
            URIBuilder uriBuilder = new URIBuilder(URL + "/history");
            uriBuilder.addParameter("country", country);
            if (day != null)
                uriBuilder.addParameter("day", day);
            return getStringResponseEntity(uriBuilder);
        } catch (IOException | URISyntaxException e) {
            logger.error(MessageFormat.format(CAUGHT_EXCEPTION_FORMAT, CAUGHT_EXCEPTION, e));
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            logger.error(MessageFormat.format(CAUGHT_EXCEPTION_FORMAT, CAUGHT_EXCEPTION, e));
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> getStringResponseEntity(URIBuilder uriBuilder) throws URISyntaxException, IOException, InterruptedException {
        URI uri = uriBuilder.build();
        if (logger.isInfoEnabled() && uri != null)
            logger.info(MessageFormat.format("Built URI: {0}", uri));
        HttpRequest request = createRapidApiGet(uri);
        HttpResponse<String> response;
        if (logger.isInfoEnabled() && request != null)
            logger.info(MessageFormat.format("Checking cache for request {0}", request));
        ResponseEntity<String> cacheRes = cache.get(request);
        if (cacheRes.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            if (logger.isInfoEnabled() && request != null)
                logger.info(MessageFormat.format("Not found in cache: request {0}. Sending request to API.", request));
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            ResponseEntity<String> responseEntity = new ResponseEntity<>(response.body(), HttpStatus.valueOf(response.statusCode()));
            cache.put(request, responseEntity);
            if (logger.isInfoEnabled() && request != null)
                logger.info(MessageFormat.format("Response cached for request: {0}", request));
            return responseEntity;
        }
        if (logger.isInfoEnabled() && request != null)
            logger.info(MessageFormat.format("Found in cache: request {0}", request));
        return cacheRes;
    }

    private HttpRequest createRapidApiGet(URI uri) {
        logger.info("createRapidApiGet called.");
        String key = "2b40038946msh38761268e33a7fbp1c4bfejsn17b9b063c6b4";
        HttpRequest ret = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-RapidAPI-Host", HOST)
                .header("X-RapidAPI-Key", key)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        if (logger.isInfoEnabled() && ret != null)
            logger.info(MessageFormat.format("Created HTTP Request: {0}", ret));
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
