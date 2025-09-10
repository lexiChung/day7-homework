package org.example.demo0909.controller;

import java.util.List;
import java.util.UUID;
import org.example.demo0909.domain.ParkingBoy;
import org.example.demo0909.domain.Ticket;
import org.example.demo0909.dto.CarDTO;
import org.example.demo0909.dto.ParkingBoyDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/parkingBoy")
public class ParkingBoyController {

//  @PostMapping("/create")
//  public ParkingBoy createParkingBoy(@RequestBody ParkingBoyDTO parkingBoyDTO) {
//    ParkingBoy parkingBoy = new ParkingBoy(parkingBoyDTO.getName(), UUID.randomUUID().clockSequence());
//    ParkingBoy.saveParkingBoy(parkingBoy);
//    return parkingBoy;
//  }
//
//  @GetMapping("/list")
//  public List<ParkingBoy> getAllParkingBoys() {
//    return ParkingBoy.parkingBoys;
//  }
//
//  @PostMapping("/{parkingBoyId}/parking")
//  public Ticket parking(@RequestBody CarDTO car,@PathVariable int parkingBoyId) {
//    ParkingBoy currentParkingBoy = ParkingBoy.getParkingBoyById(parkingBoyId);
//    return null;
//  }
}
