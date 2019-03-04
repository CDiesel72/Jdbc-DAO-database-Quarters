package CDiesel72;

/**
 * Created by Diesel on 03.03.2019.
 */
public class Quarter {

    @Id
    private int id;
    private String region;
    private String address;
    private int area;
    private int countRooms;
    private int price;

    public Quarter() {
    }

    public Quarter(String region, String address, int area, int countRooms, int price) {
        this.id = 0;
        this.region = region;
        this.address = address;
        this.area = area;
        this.countRooms = countRooms;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(int countRooms) {
        this.countRooms = countRooms;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Quarter{" +
                "id=" + id +
                ", region='" + region + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", countRooms=" + countRooms +
                ", price=" + price +
                '}';
    }
}
