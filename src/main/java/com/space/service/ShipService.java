package com.space.service;


import com.space.model.Ship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ShipService {

    List<Ship> getShips(Specification<Ship> specification, Pageable pageable);

    Integer getCount(Specification<Ship> specification);

    ResponseEntity<Ship> createShip(Ship ship);

    ResponseEntity<Ship> getShipByID(String id);

    ResponseEntity<Ship> updateShip(String id, Ship ship);

    ResponseEntity<Ship> deleteShip(String id);

}

