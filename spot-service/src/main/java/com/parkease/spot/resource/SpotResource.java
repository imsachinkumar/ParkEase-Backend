package com.parkease.spot.resource;

import com.parkease.spot.dto.SpotRequest;
import com.parkease.spot.entity.Spot;
import com.parkease.spot.service.SpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/spots")
@RequiredArgsConstructor
public class SpotResource {

    private final SpotService spotService;

    // POST /api/v1/spots/create
    @PostMapping("/create")
    public ResponseEntity<Spot> create(@Valid @RequestBody SpotRequest req) {
        Spot spot = Spot.builder()
                .lotId(req.getLotId())
                .spotNumber(req.getSpotNumber())
                .spotType(Spot.SpotType.valueOf(req.getSpotType().toUpperCase()))
                .isEV(req.getIsEV())
                .isCovered(req.getIsCovered())
                .status(Spot.SpotStatus.AVAILABLE)
                .build();
        return ResponseEntity.ok(spotService.createSpot(spot));
    }

    // GET /api/v1/spots/{spotId}
    @GetMapping("/{spotId}")
    public ResponseEntity<Spot> getById(@PathVariable Integer spotId) {
        return spotService.getSpotById(spotId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/spots/lot/{lotId}
    @GetMapping("/lot/{lotId}")
    public ResponseEntity<List<Spot>> getByLot(@PathVariable Integer lotId) {
        return ResponseEntity.ok(spotService.getSpotsByLot(lotId));
    }

    // GET /api/v1/spots/lot/{lotId}/available
    @GetMapping("/lot/{lotId}/available")
    public ResponseEntity<List<Spot>> getAvailable(@PathVariable Integer lotId) {
        return ResponseEntity.ok(spotService.getAvailableSpotsByLot(lotId));
    }

    // GET /api/v1/spots/lot/{lotId}/type/{spotType}
    @GetMapping("/lot/{lotId}/type/{spotType}")
    public ResponseEntity<List<Spot>> getByType(@PathVariable Integer lotId,
                                                @PathVariable String spotType) {
        return ResponseEntity.ok(spotService.getSpotsByLotAndType(lotId, spotType));
    }

    // GET /api/v1/spots/lot/{lotId}/available/type/{spotType}
    @GetMapping("/lot/{lotId}/available/type/{spotType}")
    public ResponseEntity<List<Spot>> getAvailableByType(@PathVariable Integer lotId,
                                                         @PathVariable String spotType) {
        return ResponseEntity.ok(spotService.getAvailableSpotsByLotAndType(lotId, spotType));
    }

    // GET /api/v1/spots/ev
    @GetMapping("/ev")
    public ResponseEntity<List<Spot>> getEVSpots() {
        return ResponseEntity.ok(spotService.getEVSpots());
    }

    // PATCH /api/v1/spots/status/{spotId}
    @PatchMapping("/status/{spotId}")
    public ResponseEntity<Spot> updateStatus(@PathVariable Integer spotId,
                                             @RequestParam String status) {
        return ResponseEntity.ok(spotService.updateSpotStatus(spotId, status));
    }

    // PUT /api/v1/spots/update/{spotId}
    @PutMapping("/update/{spotId}")
    public ResponseEntity<Spot> update(@PathVariable Integer spotId,
                                       @RequestBody SpotRequest req) {
        Spot spot = Spot.builder()
                .spotNumber(req.getSpotNumber())
                .spotType(Spot.SpotType.valueOf(req.getSpotType().toUpperCase()))
                .isEV(req.getIsEV())
                .isCovered(req.getIsCovered())
                .build();
        return ResponseEntity.ok(spotService.updateSpot(spotId, spot));
    }

    // DELETE /api/v1/spots/delete/{spotId}
    @DeleteMapping("/delete/{spotId}")
    public ResponseEntity<String> delete(@PathVariable Integer spotId) {
        spotService.deleteSpot(spotId);
        return ResponseEntity.ok("Spot deleted successfully");
    }

    // GET /api/v1/spots/lot/{lotId}/count
    @GetMapping("/lot/{lotId}/count")
    public ResponseEntity<Long> countAvailable(@PathVariable Integer lotId) {
        return ResponseEntity.ok(spotService.countAvailableSpots(lotId));
    }
}