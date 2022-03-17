package integration;

import geocoding.Address;
import geocoding.AddressResolver;
import geocoding.TqsBasicHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;


public class AddressResolverIT {
    private AddressResolver addressResolver;

    @BeforeEach
    public void setUp() {
        addressResolver = new AddressResolver(new TqsBasicHttpClient());
    }

    @AfterEach
    public void tearDown() {
        addressResolver = null;
    }

    @Test
    public void findAddressForLocationExistsTest() throws ParseException, URISyntaxException, IOException {
        Optional<Address> res = addressResolver.findAddressForLocation(40.6318, -8.658);
        Assertions.assertTrue(res.isPresent());

    }

    @Test
    public void findAddressForLocationDoesntExistTest() throws ParseException, URISyntaxException, IOException {
        Optional<Address> res = addressResolver.findAddressForLocation(100, 0);
        Assertions.assertFalse(res.isPresent());
    }
}
