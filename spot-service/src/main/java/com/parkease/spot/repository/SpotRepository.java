package com.parkease.spot.repository;

import com.parkease.spot.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer> {

    List<Spot> findByLotId(Integer lotId);
    List<Spot> findByLotIdAndStatus(Integer lotId, Spot.SpotStatus status);
    List<Spot> findByLotIdAndSpotType(Integer lotId, Spot.SpotType spotType);
    List<Spot> findByLotIdAndSpotTypeAndStatus(Integer lotId, Spot.SpotType spotType, Spot.SpotStatus status);
    List<Spot> findByIsEV(Boolean isEV);
    boolean existsByLotIdAndSpotNumber(Integer lotId, String spotNumber);
    long countByLotIdAndStatus(Integer lotId, Spot.SpotStatus status);
}