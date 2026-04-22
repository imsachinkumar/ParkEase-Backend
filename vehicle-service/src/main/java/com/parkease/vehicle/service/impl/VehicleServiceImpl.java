package com.parkease.vehicle.service.impl;

import com.parkease.vehicle.entity.Vehicle;
import com.parkease.vehicle.repository.VehicleRepository;
import com.parkease.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle registerVehicle(Vehicle vehicle) {
        if (vehicleRepository.existsByLicensePlate(vehicle.getLicensePlate())) {
            throw new RuntimeException("License plate already registered: " + vehicle.getLicensePlate());
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> getVehicleById(Integer vehicleId) {
        return vehicleRepository.findByVehicleId(vehicleId);
    }

    @Override
    public List<Vehicle> getVehiclesByOwner(Integer ownerId) {
        return vehicleRepository.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Vehicle> getByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }

    @Override
    public Vehicle updateVehicle(Integer vehicleId, Vehicle updated) {
        Vehicle existing = vehicleRepository.findByVehicleId(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + vehicleId));
        existing.setMake(updated.getMake());
        existing.setModel(updated.getModel());
        existing.setColor(updated.getColor());
        existing.setVehicleType(updated.getVehicleType());
        existing.setIsEV(updated.getIsEV());
        return vehicleRepository.save(existing);
    }

    @Override
    public void deleteVehicle(Integer vehicleId) {
        vehicleRepository.deleteByVehicleId(vehicleId);
    }

    @Override
    public String getVehicleType(Integer vehicleId) {
        Vehicle vehicle = vehicleRepository.findByVehicleId(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + vehicleId));
        return vehicle.getVehicleType().name();
    }

    @Override
    public boolean isEVVehicle(Integer vehicleId) {
        Vehicle vehicle = vehicleRepository.findByVehicleId(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + vehicleId));
        return vehicle.getIsEV();
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}