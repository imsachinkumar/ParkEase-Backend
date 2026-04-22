package com.parkease.parkinglot.repository;

import com.parkease.parkinglot.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Integer> {

    List<ParkingLot> findByCity(String city);
    List<ParkingLot> findByStatus(ParkingLot.LotStatus status);
    List<ParkingLot> findByHasEVCharging(Boolean hasEVCharging);
    List<ParkingLot> findByOwnerId(Integer ownerId);
    List<ParkingLot> findByCityAndStatus(String city, ParkingLot.LotStatus status);
    boolean existsByLotName(String lotName);
}