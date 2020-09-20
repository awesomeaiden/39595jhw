public class Automobile {

    private Integer vin; // vehicle identification number
    private Float miles; // mileage on vehicle
    private Integer month; // month of last maintenance
    private Integer day; // day of last maintenance
    private Integer year; // year of last maintenance

    public Automobile(Integer _vin, Float _miles, Integer _month, Integer _day, Integer _year) {
        vin = _vin;
        miles = _miles;
        month = _month;
        day = _day;
        year = _year;
    }

    public Integer getVin() {
        return vin;
    }

    public void setVin(Integer _vin) {
        vin = _vin;
    }

    public Float getMiles() {
        return miles;
    }

    public void setMiles(Float _miles) {
        miles = _miles;
    }

    public String getMaintenance() {
        return month.toString() + "/" + day.toString() + "/" + year.toString();
    }

    public void setMaintenance(Integer _month, Integer _day, Integer _year) {
        month = _month;
        day = _day;
        year = _year;
    }

    public String toString() {
        String auto = "VIN: " + vin.toString() + "\n";
        auto += "Miles: " + miles.toString() + "\n";
        auto += "Date of last maintenance: " + getMaintenance() + "\n";
        return auto;
    }
}
