package com.parkease.vehicle.repository;

import com.parkease.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByOwnerId(Integer ownerId);
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    Optional<Vehicle> findByVehicleId(Integer vehicleId);
    List<Vehicle> findByVehicleType(Vehicle.VehicleType vehicleType);
    List<Vehicle> findByIsEV(Boolean isEV);
    boolean existsByLicensePlate(String licensePlate);

    @Transactional
    void deleteByVehicleId(Integer vehicleId);
}