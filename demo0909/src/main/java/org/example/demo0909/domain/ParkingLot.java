package org.example.demo0909.domain;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
  private int TotalCapacity;
  private Map<Ticket,Car> ticketCarMap = new HashMap<>();

  public Ticket parking(Car car) {
    if(ticketCarMap.size()<=0) throw new RuntimeException("No available position.");
    Ticket ticket = new Ticket();
    ticketCarMap.put(ticket,car);
    return ticket;
  }
}
