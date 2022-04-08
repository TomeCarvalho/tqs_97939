package tqs.n97939.covid.controller;

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
    @Autowired
    private CovidService service;

    @GetMapping("/countries")
    public ResponseEntity<String> getCountries(@RequestParam(required = false) String search) {
        return service.getCountries(search);
    }

    @GetMapping("/statistics")
    public ResponseEntity<String> getCurrentStatistics(@RequestParam(required = false) String country) {
        return service.getCurrentStatistics(country);
    }
}
