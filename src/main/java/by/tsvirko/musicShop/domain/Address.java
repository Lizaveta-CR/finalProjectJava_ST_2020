package by.tsvirko.musicShop.domain;

/**
 * Buyer's addresses class
 */
public class Address extends Entity {
    private String country;
    private String city;
    private int zipCode;
    private String street;
    private int apartment_number;
    private int house_number;
//    private Buyer buyer;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getApartment_number() {
        return apartment_number;
    }

    public void setApartment_number(int apartment_number) {
        this.apartment_number = apartment_number;
    }

    public int getHouse_number() {
        return house_number;
    }

    public void setHouse_number(int house_number) {
        this.house_number = house_number;
    }

//    public Buyer getBuyer() {
//        return buyer;
//    }
//
//    public void setBuyer(Buyer buyer) {
//        this.buyer = buyer;
//    }


    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", zipCode=" + zipCode +
                ", street='" + street + '\'' +
                ", apartment_number=" + apartment_number +
                ", house_number=" + house_number +
                '}';
    }
}
