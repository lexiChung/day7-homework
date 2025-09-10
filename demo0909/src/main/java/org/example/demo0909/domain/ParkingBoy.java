package org.example.demo0909.domain;


import java.util.ArrayList;
import java.util.List;

public class ParkingBoy {

  private int id;
  private final String name;
  private List<ParkingLot> parkingLots = new ArrayList<>();

  public ParkingBoy(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public static List<ParkingBoy> parkingBoys = new ArrayList<>();
  public static void saveParkingBoy(ParkingBoy parkingBoy) {
    parkingBoys.add(parkingBoy);
  }

  public static ParkingBoy getParkingBoyById(int id) {
    for (ParkingBoy parkingBoy : parkingBoys) {
      if (parkingBoy.getId() == id) {
        return parkingBoy;
      }
    }
    return null;
  }
}
