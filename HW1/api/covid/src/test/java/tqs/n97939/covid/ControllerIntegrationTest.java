package tqs.n97939.covid;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.n97939.covid.service.CovidService;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.n97939.covid.ControllerWithMockServiceTest.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CovidApplication.class)
@AutoConfigureMockMvc
class ControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CovidService service;

    @Test
    void testGetCountries() throws Exception {
        mvc.perform(get("/api/countries").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Objects.requireNonNull(COUNTRIES_RE.getBody())));
        mvc.perform(get("/api/countries?search=stan").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Objects.requireNonNull(COUNTRIES_STAN.getBody())));
    }

    @Test
    void testGetCurrentStatistics() throws Exception {
        mvc.perform(get("/api/statistics?country=usa").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetHistory() throws Exception {
        mvc.perform(get("/api/history?country=diamond-princess&day=2020-04-14").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Objects.requireNonNull(HISTORY_DP_DAY_RE.getBody())));
    }

    @Test
    void testGetCacheStats() throws Exception {
        mvc.perform(get("/api/cache-stats").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
