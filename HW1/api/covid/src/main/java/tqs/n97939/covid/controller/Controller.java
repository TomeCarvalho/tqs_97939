package tqs.n97939.covid.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.n97939.covid.service.CovidService;

import java.text.MessageFormat;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class Controller {
    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final CovidService service;

    public Controller(CovidService service) {
        this.service = service;
    }

    @Operation(summary = "Get all countries/regions.")
    @ApiResponse(responseCode = "200", description = "Got all countries/regions.")
    @GetMapping("/countries")
    public ResponseEntity<String> getCountries(@Parameter(description = "Partial name search query.") @RequestParam(required = false) String search) {
        if (logger.isInfoEnabled())
            logger.info(MessageFormat.format("GET /countries - search = {0}", search));
        return service.getCountries(search);
    }

    @Operation(summary = "Get a country/region's current COVID-19 statistics.")
    @ApiResponse(responseCode = "200", description = "Got all of the country/region's COVID-19 statistics.")
    @GetMapping("/statistics")
    public ResponseEntity<String> getCurrentStatistics(@Parameter(description = "Name of the country to be queried.") @RequestParam(required = false) String country) {
        if (logger.isInfoEnabled())
            logger.info(MessageFormat.format("GET /statistics - country = {0}", country));
        return service.getCurrentStatistics(country);
    }


    @Operation(summary = "Get a country/region's historical COVID-19 statistics (all or just a day's).")
    @ApiResponse(responseCode = "200", description = "Got all of the country/region's desired historical COVID-19 statistics.")
    @GetMapping("/history")
    public ResponseEntity<String> getHistory(
            @Parameter(description = "Name of the country to be queried.") @RequestParam String country,
            @Parameter(description = "Date to be queried, in ISO 8601 format.") @RequestParam(required = false) String day) {
        if (logger.isInfoEnabled())
            logger.info(MessageFormat.format("GET /history - country = {0}, day = {1}", country, day));
        return service.getHistory(country, day);
    }

    @Operation(summary = "Get cache statistics.")
    @ApiResponse(responseCode = "200", description = "Got cache statistics.")
    @GetMapping("/cache-stats")
    public ResponseEntity<String> getCacheStats() {
        return service.getCacheStats();
    }
}
