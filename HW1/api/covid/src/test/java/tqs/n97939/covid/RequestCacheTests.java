package tqs.n97939.covid;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
// import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.n97939.covid.cache.RequestCache;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notANumber;
import static org.hamcrest.core.Is.is;

public class RequestCacheTests {
    private RequestCache cache;
    private static final long LONG_TTL = 1000000;
    private static final long SHORT_TTL = 1;
    private static final ResponseEntity<String> NOT_FOUND = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    private static final HttpRequest REQUEST1 = HttpRequest.newBuilder().uri(URI.create("https://foo1.bar")).build();
    private static final HttpRequest REQUEST2 = HttpRequest.newBuilder().uri(URI.create("https://foo2.bar")).build();
    private static final ResponseEntity<String> RESPONSE1 = new ResponseEntity<>("RESPONSE1", HttpStatus.OK);
    // private static final ResponseEntity<String> RESPONSE2 = new ResponseEntity<>("RESPONSE2", HttpStatus.OK);

    @AfterEach
    public void tearDown() {
        cache = null;
    }

    @DisplayName("Cache construction")
    @Test
    void testConstruction() {
        cache = new RequestCache(LONG_TTL);
        assertThat(cache.getTtl(), is(LONG_TTL));
        assertThat(cache.size(), is(0));
        assertThat(cache.getCount(), is(0));
        assertThat(cache.getHitCount(), is(0));
        assertThat(cache.getMissCount(), is(0));
        assertThat(cache.hitRatio(), is(notANumber()));  // 0/0
    }

    @DisplayName("Cache put")
    @Test
    void testCachePut() {
        cache = new RequestCache(LONG_TTL);
        cache.put(REQUEST1, RESPONSE1);
        assertThat(cache.size(), is(1));
    }

    @DisplayName("Cache get")
    @Test
    void testCacheGet() {
        cache = new RequestCache(LONG_TTL);
        cache.put(REQUEST1, RESPONSE1);
        assertThat(cache.get(REQUEST1), is(RESPONSE1));
        assertThat(cache.getHitCount(), is(1));
        assertThat(cache.getMissCount(), is(0));
        assertThat(cache.getCount(), is(1));
        assertThat(cache.hitRatio(), is(1.0));

        assertThat(cache.get(REQUEST2), is(NOT_FOUND));
        assertThat(cache.getHitCount(), is(1));
        assertThat(cache.getMissCount(), is(1));
        assertThat(cache.getCount(), is(2));
        assertThat(cache.hitRatio(), is(.5));
    }

    @DisplayName("Cache expiry")
    @Test
    void testCacheExpiry() {
        cache = new RequestCache(SHORT_TTL);
        cache.put(REQUEST1, RESPONSE1);
        await().until(cacheIsEmpty());
        assertThat(cache.get(REQUEST1), is(NOT_FOUND));
    }

    private Callable<Boolean> cacheIsEmpty() {
        return () -> cache.size() == 0; // The condition that must be fulfilled
    }
}
