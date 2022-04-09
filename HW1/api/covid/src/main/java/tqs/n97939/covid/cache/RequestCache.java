package tqs.n97939.covid.cache;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestCache {
    private Map<HttpRequest, ResponseEntity<String>> cache;
    private final int capacity;
    private final int ttl;
    private int hitCount;
    private int missCount;

    public RequestCache(int capacity, int ttl) {
        this.cache = new HashMap<>(capacity);
        this.capacity = capacity;
        this.ttl = ttl;
        this.hitCount = 0;
        this.missCount = 0;
    }

    public ResponseEntity<String> get(HttpRequest key) {
        ResponseEntity<String> notFound = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<String> ret = cache.getOrDefault(key, notFound);
        if (ret.equals(notFound))
            missCount++;
        else
            hitCount++;
        return ret;
    }

    public void put(HttpRequest key, ResponseEntity<String> val) {
        cache.put(key, val);
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(ttl * 1000L);
                } catch (InterruptedException e) {
                    remove(key);
                }
            }
        });
        t.setDaemon(true);
        t.start();
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
