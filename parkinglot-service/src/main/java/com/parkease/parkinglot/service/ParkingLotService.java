package com.parkease.parkinglot.service;

import com.parkease.parkinglot.entity.ParkingLot;
import java.util.List;
import java.util.Optional;

public interface ParkingLotService {
    ParkingLot createLot(ParkingLot parkingLot);
    Optional<ParkingLot> getLotById(Integer lotId);
    List<ParkingLot> getAllLots();
    List<ParkingLot> getLotsByCity(String city);
    List<ParkingLot> getActiveLots();
    List<ParkingLot> getLotsByOwner(Integer ownerId);
    List<ParkingLot> getEVLots();
    ParkingLot updateLot(Integer lotId, ParkingLot parkingLot);
    ParkingLot updateAvailableSpots(Integer lotId, Integer spots);
    void deleteLot(Integer lotId);
    ParkingLot updateStatus(Integer lotId, String status);
}