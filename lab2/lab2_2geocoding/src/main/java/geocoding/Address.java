package geocoding;

import java.util.Objects;

public class Address {
    private String road;
    private String state;
    private String city;
    private String zip;
    private String houseNumber;

    public Address(String road, String state, String city, String zip, String houseNumber) {
        this.road = road;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "road='" + road + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(road, address.road) && Objects.equals(state, address.state) && Objects.equals(city, address.city) && Objects.equals(zip, address.zip) && Objects.equals(houseNumber, address.houseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(road, state, city, zip, houseNumber);
    }
}
