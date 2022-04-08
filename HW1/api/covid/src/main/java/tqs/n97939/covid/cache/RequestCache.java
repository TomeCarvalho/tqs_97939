package tqs.n97939.covid.cache;

import org.springframework.http.ResponseEntity;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestCache {
    private Map<HttpRequest, ResponseEntity<String>> cache;
    private final int capacity;
    private int hitCount;
    private int missCount;

    public RequestCache(int capacity) {
        this.cache = new HashMap<>(capacity);
        this.capacity = capacity;
        this.hitCount = 0;
        this.missCount = 0;
    }

    public void put(HttpRequest key, ResponseEntity<String> val) {
        cache.put(key, val);
    }

    public void remove(HttpRequest key) {
        cache.remove(key);
    }

    public int hitRatio() {
        return hitCount / (hitCount + missCount);
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getMissCount() {
        return missCount;
    }
}
