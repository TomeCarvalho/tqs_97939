package tqs.n97939.covid;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import tqs.n97939.covid.controller.Controller;
import tqs.n97939.covid.service.CovidService;

import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(Controller.class)
public class ControllerWithMockServiceTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CovidService service;

    static final ResponseEntity<String> COUNTRIES_RE = new ResponseEntity<>(
            "{\"get\":\"countries\",\"parameters\":[],\"errors\":[],\"results\":233,\"response\":[\"Afghanistan\",\"Albania\",\"Algeria\",\"Andorra\",\"Angola\",\"Anguilla\",\"Antigua-and-Barbuda\",\"Argentina\",\"Armenia\",\"Aruba\",\"Australia\",\"Austria\",\"Azerbaijan\",\"Bahamas\",\"Bahrain\",\"Bangladesh\",\"Barbados\",\"Belarus\",\"Belgium\",\"Belize\",\"Benin\",\"Bermuda\",\"Bhutan\",\"Bolivia\",\"Bosnia-and-Herzegovina\",\"Botswana\",\"Brazil\",\"British-Virgin-Islands\",\"Brunei\",\"Bulgaria\",\"Burkina-Faso\",\"Burundi\",\"Cabo-Verde\",\"Cambodia\",\"Cameroon\",\"Canada\",\"CAR\",\"Caribbean-Netherlands\",\"Cayman-Islands\",\"Chad\",\"Channel-Islands\",\"Chile\",\"China\",\"Colombia\",\"Comoros\",\"Congo\",\"Cook-Islands\",\"Costa-Rica\",\"Croatia\",\"Cuba\",\"Cura&ccedil;ao\",\"Cyprus\",\"Czechia\",\"Denmark\",\"Diamond-Princess\",\"Diamond-Princess-\",\"Djibouti\",\"Dominica\",\"Dominican-Republic\",\"DRC\",\"Ecuador\",\"Egypt\",\"El-Salvador\",\"Equatorial-Guinea\",\"Eritrea\",\"Estonia\",\"Eswatini\",\"Ethiopia\",\"Faeroe-Islands\",\"Falkland-Islands\",\"Fiji\",\"Finland\",\"France\",\"French-Guiana\",\"French-Polynesia\",\"Gabon\",\"Gambia\",\"Georgia\",\"Germany\",\"Ghana\",\"Gibraltar\",\"Greece\",\"Greenland\",\"Grenada\",\"Guadeloupe\",\"Guam\",\"Guatemala\",\"Guinea\",\"Guinea-Bissau\",\"Guyana\",\"Haiti\",\"Honduras\",\"Hong-Kong\",\"Hungary\",\"Iceland\",\"India\",\"Indonesia\",\"Iran\",\"Iraq\",\"Ireland\",\"Isle-of-Man\",\"Israel\",\"Italy\",\"Ivory-Coast\",\"Jamaica\",\"Japan\",\"Jordan\",\"Kazakhstan\",\"Kenya\",\"Kiribati\",\"Kuwait\",\"Kyrgyzstan\",\"Laos\",\"Latvia\",\"Lebanon\",\"Lesotho\",\"Liberia\",\"Libya\",\"Liechtenstein\",\"Lithuania\",\"Luxembourg\",\"Macao\",\"Madagascar\",\"Malawi\",\"Malaysia\",\"Maldives\",\"Mali\",\"Malta\",\"Marshall-Islands\",\"Martinique\",\"Mauritania\",\"Mauritius\",\"Mayotte\",\"Mexico\",\"Micronesia\",\"Moldova\",\"Monaco\",\"Mongolia\",\"Montenegro\",\"Montserrat\",\"Morocco\",\"Mozambique\",\"MS-Zaandam\",\"MS-Zaandam-\",\"Myanmar\",\"Namibia\",\"Nauru\",\"Nepal\",\"Netherlands\",\"New-Caledonia\",\"New-Zealand\",\"Nicaragua\",\"Niger\",\"Nigeria\",\"Niue\",\"North-Macedonia\",\"Norway\",\"Oman\",\"Pakistan\",\"Palau\",\"Palestine\",\"Panama\",\"Papua-New-Guinea\",\"Paraguay\",\"Peru\",\"Philippines\",\"Poland\",\"Portugal\",\"Puerto-Rico\",\"Qatar\",\"R&eacute;union\",\"Romania\",\"Russia\",\"Rwanda\",\"S-Korea\",\"Saint-Helena\",\"Saint-Kitts-and-Nevis\",\"Saint-Lucia\",\"Saint-Martin\",\"Saint-Pierre-Miquelon\",\"Samoa\",\"San-Marino\",\"Sao-Tome-and-Principe\",\"Saudi-Arabia\",\"Senegal\",\"Serbia\",\"Seychelles\",\"Sierra-Leone\",\"Singapore\",\"Sint-Maarten\",\"Slovakia\",\"Slovenia\",\"Solomon-Islands\",\"Somalia\",\"South-Africa\",\"South-Sudan\",\"Spain\",\"Sri-Lanka\",\"St-Barth\",\"St-Vincent-Grenadines\",\"Sudan\",\"Suriname\",\"Sweden\",\"Switzerland\",\"Syria\",\"Taiwan\",\"Tajikistan\",\"Tanzania\",\"Thailand\",\"Timor-Leste\",\"Togo\",\"Tonga\",\"Trinidad-and-Tobago\",\"Tunisia\",\"Turkey\",\"Turks-and-Caicos\",\"UAE\",\"Uganda\",\"UK\",\"Ukraine\",\"Uruguay\",\"US-Virgin-Islands\",\"USA\",\"Uzbekistan\",\"Vanuatu\",\"Vatican-City\",\"Venezuela\",\"Vietnam\",\"Wallis-and-Futuna\",\"Western-Sahara\",\"Yemen\",\"Zambia\",\"Zimbabwe\"]}",
            HttpStatus.OK
    );

    static final ResponseEntity<String> COUNTRIES_STAN = new ResponseEntity<>(
            "{\"get\":\"countries\",\"parameters\":{\"search\":\"stan\"},\"errors\":[],\"results\":6,\"response\":[\"Afghanistan\",\"Kazakhstan\",\"Kyrgyzstan\",\"Pakistan\",\"Tajikistan\",\"Uzbekistan\"]}",
            HttpStatus.OK
    );


    static final ResponseEntity<String> STATS_USA_RE = new ResponseEntity<>(
            "{\"get\":\"statistics\",\"parameters\":{\"country\":\"usa\"},\"errors\":[],\"results\":1,\"response\":[{\"continent\":\"North-America\",\"country\":\"USA\",\"population\":334453530,\"cases\":{\"new\":null,\"active\":1126705,\"critical\":1559,\"recovered\":80052061,\"1M_pop\":\"245753\",\"total\":82192880},\"deaths\":{\"new\":null,\"1M_pop\":\"3032\",\"total\":1014114},\"tests\":{\"1M_pop\":\"2969875\",\"total\":993285213},\"day\":\"2022-04-14\",\"time\":\"2022-04-14T08:30:03+00:00\"}]}",
            HttpStatus.OK
    );

    static final ResponseEntity<String> HISTORY_DP_RE = new ResponseEntity<>(
            "{\"get\":\"history\",\"parameters\":{\"country\":\"diamond-princess\"},\"errors\":[],\"results\":10,\"response\":[{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":0,\"critical\":null,\"recovered\":699,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":13},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2022-04-14\",\"time\":\"2022-04-14T08:30:05+00:00\"},{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":40,\"critical\":4,\"recovered\":659,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":13},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2020-12-03\",\"time\":\"2020-12-03T22:00:13+00:00\"},{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":48,\"critical\":4,\"recovered\":651,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":13},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2020-10-10\",\"time\":\"2020-10-10T05:00:07+00:00\"},{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":48,\"critical\":4,\"recovered\":651,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":13},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2020-05-29\",\"time\":\"2020-05-29T17:45:07+00:00\"},{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":48,\"critical\":4,\"recovered\":651,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":13},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2020-05-29\",\"time\":\"2020-05-29T00:15:06+00:00\"},{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":54,\"critical\":4,\"recovered\":645,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":13},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2020-05-10\",\"time\":\"2020-05-10T23:00:05+00:00\"},{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":55,\"critical\":7,\"recovered\":644,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":13},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2020-04-23\",\"time\":\"2020-04-23T00:15:05+00:00\"},{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":56,\"critical\":7,\"recovered\":644,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":12},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2020-04-17\",\"time\":\"2020-04-17T05:15:05+00:00\"},{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":61,\"critical\":7,\"recovered\":639,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":12},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2020-04-16\",\"time\":\"2020-04-16T02:15:05+00:00\"},{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":82,\"critical\":10,\"recovered\":619,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":11},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2020-04-14\",\"time\":\"2020-04-14T00:30:11+00:00\"}]}",
            HttpStatus.OK
    );

    static final ResponseEntity<String> HISTORY_DP_DAY_RE = new ResponseEntity<>(
            "{\"get\":\"history\",\"parameters\":{\"country\":\"diamond-princess\",\"day\":\"2020-04-14\"},\"errors\":[],\"results\":1,\"response\":[{\"continent\":null,\"country\":\"Diamond-Princess\",\"population\":null,\"cases\":{\"new\":null,\"active\":82,\"critical\":10,\"recovered\":619,\"1M_pop\":null,\"total\":712},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":11},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2020-04-14\",\"time\":\"2020-04-14T00:30:11+00:00\"}]}",
            HttpStatus.OK
    );

    static final ResponseEntity<String> CACHE_STATS_RE = new ResponseEntity<>(
            "{\n" +
                    "    \"ttl\": 10000,\n" +
                    "    \"stats\": {\n" +
                    "        \"count\": 7,\n" +
                    "        \"hits\": 0,\n" +
                    "        \"misses\": 7,\n" +
                    "        \"hit_ratio\": 0.000000,\n" +
                    "        \"size\": 0\n" +
                    "    }\n" +
                    "}",
            HttpStatus.OK
    );

    @Test
    void testGetCountries() throws Exception {
        when(service.getCountries(null)).thenReturn(COUNTRIES_RE);
        when(service.getCountries("stan")).thenReturn(COUNTRIES_STAN);
        mvc.perform(get("/api/countries").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Objects.requireNonNull(COUNTRIES_RE.getBody())));
        mvc.perform(get("/api/countries?search=stan").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Objects.requireNonNull(COUNTRIES_STAN.getBody())));
        verify(service, times(1)).getCountries(null);
        verify(service, times(1)).getCountries("stan");
    }

    @Test
    void testGetCurrentStatistics() throws Exception {
        when(service.getCurrentStatistics("usa")).thenReturn(STATS_USA_RE);
        mvc.perform(get("/api/statistics?country=usa").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Objects.requireNonNull(STATS_USA_RE.getBody())));
        verify(service, times(1)).getCurrentStatistics("usa");
    }

    @Test
    void testGetHistory() throws Exception {
        when(service.getHistory("diamond-princess", null)).thenReturn(HISTORY_DP_RE);
        when(service.getHistory("diamond-princess", "2020-04-14")).thenReturn(HISTORY_DP_DAY_RE);
        mvc.perform(get("/api/history?country=diamond-princess").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Objects.requireNonNull(HISTORY_DP_RE.getBody())));
        mvc.perform(get("/api/history?country=diamond-princess&day=2020-04-14").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Objects.requireNonNull(HISTORY_DP_DAY_RE.getBody())));
        verify(service, times(1)).getHistory("diamond-princess", null);
        verify(service, times(1)).getHistory("diamond-princess", "2020-04-14");
    }

    @Test
    void testGetCacheStats() throws Exception {
        when(service.getCacheStats()).thenReturn(CACHE_STATS_RE);
        mvc.perform(get("/api/cache-stats").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Objects.requireNonNull(CACHE_STATS_RE.getBody())));
        verify(service, times(1)).getCacheStats();
    }
}
