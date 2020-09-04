package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import com.space.service.ShipServiceImp;
import com.space.util.ShipSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rest")
public class ShipController {

    private ShipService shipService;

    public ShipController() {
    }

    @Autowired
    public ShipController(ShipServiceImp shipService) {
        this.shipService = shipService;
    }


    @GetMapping("/ships")
    public List<Ship> getShips(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating,
            @RequestParam(value = "order", required = false) ShipOrder order,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        if (pageNumber == null) pageNumber = 0;
        if (pageSize == null) pageSize = 3;
        Pageable pageable = order == null ? PageRequest.of(pageNumber, pageSize) : PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        return shipService.getShips(ShipSpecifications.getSpecification(name, planet, shipType, after, before,
                isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating), pageable);
    }


    @GetMapping("/ships/count")
    public Integer getCount(
            @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "planet", required = false)String planet,
                               @RequestParam(value = "shipType", required = false)ShipType shipType,
                               @RequestParam(value = "after", required = false)Long after,
                               @RequestParam(value = "before", required = false)Long before,
                               @RequestParam(value = "isUsed", required = false)Boolean isUsed,
                               @RequestParam(value = "minSpeed", required = false)Double minSpeed,
                               @RequestParam(value = "maxSpeed", required = false)Double maxSpeed,
                               @RequestParam(value = "minCrewSize", required = false)Integer minCrewSize,
                               @RequestParam(value = "maxCrewSize", required = false)Integer maxCrewSize,
                               @RequestParam(value = "minRating", required = false)Double minRating,
                               @RequestParam(value = "maxRating", required = false)Double maxRating
    ) {
        return shipService.getCount(ShipSpecifications.getSpecification(name, planet, shipType, after, before,
                isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating));
    }


    @PostMapping("/ships")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        return shipService.createShip(ship);
    }

    @GetMapping("/ships/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable(value = "id") String id) {
       return shipService.getShipByID(id);
    }

    @PostMapping("/ships/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable(value = "id") String id, @RequestBody Ship ship) {
        return shipService.updateShip(id, ship);
    }

    @DeleteMapping("/ships/{id}")
    public ResponseEntity<Ship> deleteShip(@PathVariable(value = "id") String id) {
        return shipService.deleteShip(id);
    }
}
