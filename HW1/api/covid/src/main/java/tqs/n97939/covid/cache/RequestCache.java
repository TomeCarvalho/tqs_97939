package tqs.n97939.covid.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestCache {
    private final Logger logger = LoggerFactory.getLogger(RequestCache.class);
    private Map<HttpRequest, ResponseEntity<String>> cache;
    private Map<HttpRequest, Long> timeMap;
    private final long ttl; // in ms
    private int hitCount;
    private int missCount;

    public RequestCache(long ttl) {
        this.cache = new ConcurrentHashMap<>();
        this.timeMap = new ConcurrentHashMap<>();
        new CleanerThread().start();
        this.ttl = ttl;
        this.hitCount = 0;
        this.missCount = 0;
        logger.info("Cache - initialised with TTL = " + ttl);
    }

    public ResponseEntity<String> get(HttpRequest key) {
        logger.info("Cache - checking - request: " + key);
        ResponseEntity<String> notFound = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<String> ret = cache.getOrDefault(key, notFound);
        if (ret.equals(notFound)) {
            missCount++;
            logger.info("Cache - not found - request: " + key);
        } else {
            hitCount++;
            logger.info("Cache - found - request: " + key);
        }
        return ret;
    }

    public void put(HttpRequest key, ResponseEntity<String> val) {
        Date date = new Date();
        timeMap.put(key, date.getTime());
        cache.put(key, val);
        logger.info("Cache - stored - request: " + key + " at " + date.getTime());
    }

    public void remove(HttpRequest key) {
        cache.remove(key);
        timeMap.remove(key);
        logger.info("Cache - removed - request: " + key);
    }

    public double hitRatio() {
        return ((double) hitCount) / (hitCount + missCount);
    }

    public int size() {
        return cache.size();
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getMissCount() {
        return missCount;
    }

    public int getCount() {
        return hitCount + missCount;
    }

    public long getTtl() {
        return ttl;
    }

    class CleanerThread extends Thread {
        @Override
        public void run() {
            logger.info("Cache - CleanerThread started.");
            while (true) {
                cleanMap();
                try {
                    Thread.sleep(ttl / 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void cleanMap() {
            long currentTime = new Date().getTime();
            for (HttpRequest key : timeMap.keySet())
                if (currentTime > (timeMap.get(key) + ttl))
                    remove(key);
        }
    }
}
