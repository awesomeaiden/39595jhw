public class Automobile {

    private Integer vin; // vehicle identification number
    private Float miles; // mileage on vehicle
    private String dolm; // date of last maintenance

    public Automobile(Integer _vin, Float _miles, Integer _month, Integer _day, Integer _year) {
        vin = _vin;
        miles = _miles;
        this.setMaintenance(_month, _day, _year);
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
        return dolm;
    }

    public void setMaintenance(Integer _month, Integer _day, Integer _year) {
        dolm = _month.toString() + "/" + _day.toString() + "/" + _year.toString();
    }

    public String toString() {
        String auto = "VIN: " + vin.toString() + "\n";
        auto += "Miles: " + miles.toString() + "\n";
        auto += "Date of last maintenance: " + dolm + "\n";
        return auto;
    }
}
