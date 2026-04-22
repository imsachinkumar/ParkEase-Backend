package com.parkease.vehicle.service;

import com.parkease.vehicle.entity.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleService {
    Vehicle registerVehicle(Vehicle vehicle);
    Optional<Vehicle> getVehicleById(Integer vehicleId);
    List<Vehicle> getVehiclesByOwner(Integer ownerId);
    Optional<Vehicle> getByLicensePlate(String licensePlate);
    Vehicle updateVehicle(Integer vehicleId, Vehicle vehicle);
    void deleteVehicle(Integer vehicleId);
    String getVehicleType(Integer vehicleId);
    boolean isEVVehicle(Integer vehicleId);
    List<Vehicle> getAllVehicles();
}