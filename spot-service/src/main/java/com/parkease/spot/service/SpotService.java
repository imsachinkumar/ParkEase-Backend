package com.parkease.spot.service;

import com.parkease.spot.entity.Spot;
import java.util.List;
import java.util.Optional;

public interface SpotService {
    Spot createSpot(Spot spot);
    Optional<Spot> getSpotById(Integer spotId);
    List<Spot> getSpotsByLot(Integer lotId);
    List<Spot> getAvailableSpotsByLot(Integer lotId);
    List<Spot> getSpotsByLotAndType(Integer lotId, String spotType);
    List<Spot> getAvailableSpotsByLotAndType(Integer lotId, String spotType);
    List<Spot> getEVSpots();
    Spot updateSpotStatus(Integer spotId, String status);
    Spot updateSpot(Integer spotId, Spot spot);
    void deleteSpot(Integer spotId);
    long countAvailableSpots(Integer lotId);
}