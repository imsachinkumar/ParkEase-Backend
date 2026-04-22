package com.parkease.parkinglot.resource;

import com.parkease.parkinglot.dto.ParkingLotRequest;
import com.parkease.parkinglot.entity.ParkingLot;
import com.parkease.parkinglot.service.ParkingLotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-lots")
@RequiredArgsConstructor
public class ParkingLotResource {

    private final ParkingLotService parkingLotService;

    // POST /api/v1/parking-lots/create
    @PostMapping("/create")
    public ResponseEntity<ParkingLot> create(@Valid @RequestBody ParkingLotRequest req) {
        ParkingLot lot = ParkingLot.builder()
                .lotName(req.getLotName())
                .address(req.getAddress())
                .city(req.getCity())
                .state(req.getState())
                .pincode(req.getPincode())
                .latitude(req.getLatitude())
                .longitude(req.getLongitude())
                .totalSpots(req.getTotalSpots())
                .pricePerHour(req.getPricePerHour())
                .evChargingPrice(req.getEvChargingPrice())
                .hasEVCharging(req.getHasEVCharging())
                .isCovered(req.getIsCovered())
                .ownerId(req.getOwnerId())
                .status(ParkingLot.LotStatus.ACTIVE)
                .build();
        return ResponseEntity.ok(parkingLotService.createLot(lot));
    }

    // GET /api/v1/parking-lots/{lotId}
    @GetMapping("/{lotId}")
    public ResponseEntity<ParkingLot> getById(@PathVariable Integer lotId) {
        return parkingLotService.getLotById(lotId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/parking-lots/all
    @GetMapping("/all")
    public ResponseEntity<List<ParkingLot>> getAll() {
        return ResponseEntity.ok(parkingLotService.getAllLots());
    }

    // GET /api/v1/parking-lots/city/{city}
    @GetMapping("/city/{city}")
    public ResponseEntity<List<ParkingLot>> getByCity(@PathVariable String city) {
        return ResponseEntity.ok(parkingLotService.getLotsByCity(city));
    }

    // GET /api/v1/parking-lots/active
    @GetMapping("/active")
    public ResponseEntity<List<ParkingLot>> getActive() {
        return ResponseEntity.ok(parkingLotService.getActiveLots());
    }

    // GET /api/v1/parking-lots/owner/{ownerId}
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ParkingLot>> getByOwner(@PathVariable Integer ownerId) {
        return ResponseEntity.ok(parkingLotService.getLotsByOwner(ownerId));
    }

    // GET /api/v1/parking-lots/ev
    @GetMapping("/ev")
    public ResponseEntity<List<ParkingLot>> getEVLots() {
        return ResponseEntity.ok(parkingLotService.getEVLots());
    }

    // PUT /api/v1/parking-lots/update/{lotId}
    @PutMapping("/update/{lotId}")
    public ResponseEntity<ParkingLot> update(@PathVariable Integer lotId,
                                             @RequestBody ParkingLotRequest req) {
        ParkingLot lot = ParkingLot.builder()
                .lotName(req.getLotName())
                .address(req.getAddress())
                .city(req.getCity())
                .state(req.getState())
                .pincode(req.getPincode())
                .totalSpots(req.getTotalSpots())
                .pricePerHour(req.getPricePerHour())
                .evChargingPrice(req.getEvChargingPrice())
                .hasEVCharging(req.getHasEVCharging())
                .isCovered(req.getIsCovered())
                .build();
        return ResponseEntity.ok(parkingLotService.updateLot(lotId, lot));
    }

    // PATCH /api/v1/parking-lots/spots/{lotId}
    @PatchMapping("/spots/{lotId}")
    public ResponseEntity<ParkingLot> updateSpots(@PathVariable Integer lotId,
                                                  @RequestParam Integer spots) {
        return ResponseEntity.ok(parkingLotService.updateAvailableSpots(lotId, spots));
    }

    // PATCH /api/v1/parking-lots/status/{lotId}
    @PatchMapping("/status/{lotId}")
    public ResponseEntity<ParkingLot> updateStatus(@PathVariable Integer lotId,
                                                   @RequestParam String status) {
        return ResponseEntity.ok(parkingLotService.updateStatus(lotId, status));
    }

    // DELETE /api/v1/parking-lots/delete/{lotId}
    @DeleteMapping("/delete/{lotId}")
    public ResponseEntity<String> delete(@PathVariable Integer lotId) {
        parkingLotService.deleteLot(lotId);
        return ResponseEntity.ok("Parking lot deleted successfully");
    }
}