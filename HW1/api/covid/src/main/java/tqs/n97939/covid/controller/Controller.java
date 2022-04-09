package tqs.n97939.covid.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tqs.n97939.covid.service.CovidService;


@RestController
@RequestMapping("/api")
public class Controller {
    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private CovidService service;

    @GetMapping("/countries")
    public ResponseEntity<String> getCountries(@RequestParam(required = false) String search) {
        logger.info("GET /countries - search = " + search);
        return service.getCountries(search);
    }

    @GetMapping("/statistics")
    public ResponseEntity<String> getCurrentStatistics(@RequestParam(required = false) String country) {
        logger.info("GET /statistics - country = " + country);
        return service.getCurrentStatistics(country);
    }

    @GetMapping("/history")
    public ResponseEntity<String> getHistory(@RequestParam String country, @RequestParam(required = false) String day) {
        logger.info("GET /history - country = " + country + ", day = " + day);
        return service.getHistory(country, day);
    }
}
