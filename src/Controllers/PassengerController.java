package Controllers;
import Models.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class PassengerController extends Passenger implements Runnable {
    private final String[] definedFirstNames = {"Josh","James","Jack","William","Adam","Sarah","Monika"};
    private final String[] definedLastNames = {"Oliver","Lucas","Henry","Theodore","Noah","Marcus","Zayn"};
     private final String [] definedNumbers = {"65286","45823","13597","41397","98158","81253","71963"};
     private final String [] ticketStatus = {"BOOKED","DELAYED"};
     Flight currentFlight = new Flight();
     Flight delayedFlight = new Flight();

    Random nameRandomizer = new Random();//data randomizer for changing passenger data
    Random seatsRandomizer = new Random();// seats randomizer
    Random phoneRandomizer = new Random();//phone number builder
    Passenger pass = new Passenger();//object to be changed after each creation
    TicketController ticketController = new TicketController();
    FlightDate flightDate = new FlightDate();
    int totalSeats;
    private int seatsRandomizer (){ // this functions manages the randomness of created seats at each thread.
        int requestedSeats;
        requestedSeats = seatsRandomizer.nextInt(1,4);
        return requestedSeats;
    }
    Semaphore semaphore = new Semaphore(1);

    private void passengerInitiator(){
        int namePosition;
        int phoneNum;
        namePosition = nameRandomizer.nextInt(0, 7);
        pass.setFirstName(definedFirstNames[namePosition]);
        namePosition = nameRandomizer.nextInt(0, 7);
        pass.setLastName(definedLastNames[namePosition]);
        phoneNum = phoneRandomizer.nextInt(0, 7);
        pass.setPhone(definedNumbers[phoneNum]);
        ticketController.ticketInitiator();
    }

    public int getNumberOfCreatedFlights() {
        return numberOfCreatedFlights;
    }

    public void setNumberOfCreatedFlights(int numberOfCreatedFlights) {
        this.numberOfCreatedFlights = numberOfCreatedFlights;
    }

    int numberOfCreatedFlights;
    // to create seats randomly
    public void createPassenger () throws InterruptedException {
        currentFlight.setFlight_id();
        delayedFlight.setFlight_id();
        flightDate.setThisDate();
         totalSeats = Flight.getTotal_seats();
        int currentFlightSeatsNumber = 0;
        int delayedFlightSeatsNumber = 0;
        // seats that will be assigned for all flight
        while (totalSeats >= 0) {
            int bookedSeats = seatsRandomizer();
              if (bookedSeats>totalSeats){
                  try {
                      semaphore.acquire();
                    //flightDate.setThisDate();
                    for (int i = 1; i <= bookedSeats; i++) {
                            passengerInitiator();
                            delayedFlightSeatsNumber++;
                            pass.setFlightStatus(ticketStatus[1]);
                            System.out.print(pass.getFirstName() + " " + pass.getLastName() + "  \t\t" + ticketController.getTicketId()+
                                    "  \t\t"+pass.getPhone() + "  \t\t" + pass.getFlightStatus() + "\t\t\t" + delayedFlightSeatsNumber + "\t\t" +
                                    delayedFlight.getFlight_id() + "\t\t" + delayedFlight.getAirLine() + "\t\t" + delayedFlight.getFrom_loc() + "\t\t" +
                                    delayedFlight.getTo_loc() + "\t\t" + delayedFlight.getDuration() +"\t\t"+flightDate.getThisDate() +"\t\t"+"\n");
                            Thread.sleep(1000);
                    }
                  } catch (InterruptedException ignored) {

                  }
                  finally {
                      semaphore.release();
                  }
                }
                else {
                  try {
                      semaphore.acquire();
                      totalSeats = totalSeats - bookedSeats;
                      for (int i = 1; i <= bookedSeats; i++) {
                          currentFlightSeatsNumber++;
                          passengerInitiator();
                          pass.setFlightStatus(ticketStatus[0]);
                          System.out.print(pass.getFirstName() + " " + pass.getLastName() + "  \t\t" + ticketController.getTicketId() +
                                  "  \t\t" + pass.getPhone() + "  \t\t" + pass.getFlightStatus() + "\t\t\t" + currentFlightSeatsNumber + "\t\t" +
                                  currentFlight.getFlight_id() + "\t\t" + currentFlight.getAirLine() + "\t\t" + currentFlight.getFrom_loc() + "\t\t" +
                                  currentFlight.getTo_loc() + "\t\t" + currentFlight.getDuration() + "\t\t" + flightDate.getThisDate() + "\t\t" + "\n");
                          Thread.sleep(1000);
                      }

                } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }
                  finally {
                      semaphore.release();
                  }
                  }

                  if (totalSeats == 0){
                    totalSeats = -1;
                }
            }
        return;
        }

    @Override
    public void run() {
        for (int i = 0; i < getNumberOfCreatedFlights(); i++) {
            try {
                createPassenger();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
