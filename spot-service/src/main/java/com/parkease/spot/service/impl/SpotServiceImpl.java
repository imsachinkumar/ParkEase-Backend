package com.parkease.spot.service.impl;

import com.parkease.spot.entity.Spot;
import com.parkease.spot.repository.SpotRepository;
import com.parkease.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {

    private final SpotRepository spotRepository;

    @Override
    public Spot createSpot(Spot spot) {
        if (spotRepository.existsByLotIdAndSpotNumber(spot.getLotId(), spot.getSpotNumber())) {
            throw new RuntimeException("Spot number already exists in this lot: " + spot.getSpotNumber());
        }
        return spotRepository.save(spot);
    }

    @Override
    public Optional<Spot> getSpotById(Integer spotId) {
        return spotRepository.findById(spotId);
    }

    @Override
    public List<Spot> getSpotsByLot(Integer lotId) {
        return spotRepository.findByLotId(lotId);
    }

    @Override
    public List<Spot> getAvailableSpotsByLot(Integer lotId) {
        return spotRepository.findByLotIdAndStatus(lotId, Spot.SpotStatus.AVAILABLE);
    }

    @Override
    public List<Spot> getSpotsByLotAndType(Integer lotId, String spotType) {
        return spotRepository.findByLotIdAndSpotType(lotId,
                Spot.SpotType.valueOf(spotType.toUpperCase()));
    }

    @Override
    public List<Spot> getAvailableSpotsByLotAndType(Integer lotId, String spotType) {
        return spotRepository.findByLotIdAndSpotTypeAndStatus(lotId,
                Spot.SpotType.valueOf(spotType.toUpperCase()),
                Spot.SpotStatus.AVAILABLE);
    }

    @Override
    public List<Spot> getEVSpots() {
        return spotRepository.findByIsEV(true);
    }

    @Override
    public Spot updateSpotStatus(Integer spotId, String status) {
        Spot existing = spotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Spot not found: " + spotId));
        existing.setStatus(Spot.SpotStatus.valueOf(status.toUpperCase()));
        return spotRepository.save(existing);
    }

    @Override
    public Spot updateSpot(Integer spotId, Spot updated) {
        Spot existing = spotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Spot not found: " + spotId));
        existing.setSpotNumber(updated.getSpotNumber());
        existing.setSpotType(updated.getSpotType());
        existing.setIsEV(updated.getIsEV());
        existing.setIsCovered(updated.getIsCovered());
        return spotRepository.save(existing);
    }

    @Override
    public void deleteSpot(Integer spotId) {
        spotRepository.deleteById(spotId);
    }

    @Override
    public long countAvailableSpots(Integer lotId) {
        return spotRepository.countByLotIdAndStatus(lotId, Spot.SpotStatus.AVAILABLE);
    }
}