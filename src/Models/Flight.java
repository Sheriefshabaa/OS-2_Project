package Models;
import java.util.Random;

public class Flight{
    private int Flight_id;

    protected  static  int total_seats;
    Random flightIdRandomizer = new Random();
    private int flightIdBuilder() {
        return  flightIdRandomizer.nextInt(1000,3000);
    }
    public void setFlight_id() {
        Flight_id = flightIdBuilder();
    }
    public int getFlight_id(){
        return Flight_id;
    }

    public static void setTotal_seats(int total_seats) {
        Models.Flight.total_seats = total_seats;
    }
    public static int getTotal_seats() {
        return total_seats;
    }

    /*getters*/
    public String getFrom_loc() {
        return "EGYPT, CAIRO";
    }
    public String getAirLine() {
        return "Egypt Air";
    }
    public String getTo_loc() {
        return "GERMANY, BERLIN";
    }

    public String getDuration() {
        return "4h 15m";
    }


}
