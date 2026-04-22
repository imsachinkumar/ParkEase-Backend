package com.parkease.parkinglot.service.impl;

import com.parkease.parkinglot.entity.ParkingLot;
import com.parkease.parkinglot.repository.ParkingLotRepository;
import com.parkease.parkinglot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    @Override
    public ParkingLot createLot(ParkingLot parkingLot) {
        parkingLot.setAvailableSpots(parkingLot.getTotalSpots());
        return parkingLotRepository.save(parkingLot);
    }

    @Override
    public Optional<ParkingLot> getLotById(Integer lotId) {
        return parkingLotRepository.findById(lotId);
    }

    @Override
    public List<ParkingLot> getAllLots() {
        return parkingLotRepository.findAll();
    }

    @Override
    public List<ParkingLot> getLotsByCity(String city) {
        return parkingLotRepository.findByCity(city);
    }

    @Override
    public List<ParkingLot> getActiveLots() {
        return parkingLotRepository.findByStatus(ParkingLot.LotStatus.ACTIVE);
    }

    @Override
    public List<ParkingLot> getLotsByOwner(Integer ownerId) {
        return parkingLotRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<ParkingLot> getEVLots() {
        return parkingLotRepository.findByHasEVCharging(true);
    }

    @Override
    public ParkingLot updateLot(Integer lotId, ParkingLot updated) {
        ParkingLot existing = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found: " + lotId));

        existing.setLotName(updated.getLotName());
        existing.setAddress(updated.getAddress());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setPincode(updated.getPincode());
        existing.setTotalSpots(updated.getTotalSpots());
        existing.setPricePerHour(updated.getPricePerHour());
        existing.setEvChargingPrice(updated.getEvChargingPrice());
        existing.setHasEVCharging(updated.getHasEVCharging());
        existing.setIsCovered(updated.getIsCovered());

        return parkingLotRepository.save(existing);
    }

    @Override
    public ParkingLot updateAvailableSpots(Integer lotId, Integer spots) {
        ParkingLot existing = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found: " + lotId));
        existing.setAvailableSpots(spots);
        return parkingLotRepository.save(existing);
    }

    @Override
    public void deleteLot(Integer lotId) {
        parkingLotRepository.deleteById(lotId);
    }

    @Override
    public ParkingLot updateStatus(Integer lotId, String status) {
        ParkingLot existing = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found: " + lotId));
        existing.setStatus(ParkingLot.LotStatus.valueOf(status.toUpperCase()));
        return parkingLotRepository.save(existing);
    }
}