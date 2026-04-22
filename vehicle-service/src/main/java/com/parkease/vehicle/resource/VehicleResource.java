package com.parkease.vehicle.resource;

import com.parkease.vehicle.dto.VehicleRequest;
import com.parkease.vehicle.entity.Vehicle;
import com.parkease.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleResource {

    private final VehicleService vehicleService;

    // POST /api/v1/vehicles/register
    @PostMapping("/register")
    public ResponseEntity<Vehicle> register(@Valid @RequestBody VehicleRequest req) {
        Vehicle vehicle = Vehicle.builder()
                .ownerId(req.getOwnerId())
                .licensePlate(req.getLicensePlate().toUpperCase())
                .make(req.getMake())
                .model(req.getModel())
                .color(req.getColor())
                .vehicleType(Vehicle.VehicleType.valueOf(req.getVehicleType().toUpperCase()))
                .isEV(req.getIsEV())
                .isActive(true)
                .build();
        return ResponseEntity.ok(vehicleService.registerVehicle(vehicle));
    }

    // GET /api/v1/vehicles/{vehicleId}
    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getById(@PathVariable Integer vehicleId) {
        return vehicleService.getVehicleById(vehicleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/vehicles/owner/{ownerId}
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Vehicle>> getByOwner(@PathVariable Integer ownerId) {
        return ResponseEntity.ok(vehicleService.getVehiclesByOwner(ownerId));
    }

    // GET /api/v1/vehicles/plate/{licensePlate}
    @GetMapping("/plate/{licensePlate}")
    public ResponseEntity<Vehicle> getByPlate(@PathVariable String licensePlate) {
        return vehicleService.getByLicensePlate(licensePlate.toUpperCase())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/vehicles/all
    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> getAll() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    // PUT /api/v1/vehicles/update/{vehicleId}
    @PutMapping("/update/{vehicleId}")
    public ResponseEntity<Vehicle> update(@PathVariable Integer vehicleId,
                                          @RequestBody VehicleRequest req) {
        Vehicle vehicle = Vehicle.builder()
                .make(req.getMake())
                .model(req.getModel())
                .color(req.getColor())
                .vehicleType(Vehicle.VehicleType.valueOf(req.getVehicleType().toUpperCase()))
                .isEV(req.getIsEV())
                .build();
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleId, vehicle));
    }

    // DELETE /api/v1/vehicles/delete/{vehicleId}
    @DeleteMapping("/delete/{vehicleId}")
    public ResponseEntity<String> delete(@PathVariable Integer vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }

    // GET /api/v1/vehicles/type/{vehicleId}
    @GetMapping("/type/{vehicleId}")
    public ResponseEntity<String> getType(@PathVariable Integer vehicleId) {
        return ResponseEntity.ok(vehicleService.getVehicleType(vehicleId));
    }

    // GET /api/v1/vehicles/isEV/{vehicleId}
    @GetMapping("/isEV/{vehicleId}")
    public ResponseEntity<Boolean> isEV(@PathVariable Integer vehicleId) {
        return ResponseEntity.ok(vehicleService.isEVVehicle(vehicleId));
    }
}