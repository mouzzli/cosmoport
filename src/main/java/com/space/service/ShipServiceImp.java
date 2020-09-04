package com.space.service;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import com.space.util.ShipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class ShipServiceImp implements ShipService {
    private ShipRepository shipRepository;

    public ShipServiceImp() {
    }


    @Autowired
    public ShipServiceImp(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }


    @Override
    public List<Ship> getShips(Specification<Ship> specification, Pageable pageable) {
        return shipRepository.findAll(specification, pageable).getContent();
    }

    @Override
    public Integer getCount(Specification<Ship> specification) {
        return shipRepository.findAll(specification).size();
    }

    @Override
    public ResponseEntity<Ship> createShip(Ship ship) {
        if (!ShipUtil.isValid(ship)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship responseShip = ShipUtil.createValidShip(ship);
        shipRepository.save(responseShip);
        return new ResponseEntity<>(responseShip, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Ship> getShipByID(String id) {
        long shipId;

        if (ShipUtil.checkIdToValid(id)) {
            shipId = Long.parseLong(id);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        if (shipRepository.findById(shipId).isPresent()) {
            return new ResponseEntity<>(shipRepository.findById(shipId).get(), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Ship> updateShip(String id, Ship ship) {
        long shipID;

        if (ShipUtil.checkIdToValid(id)) {
            shipID = Long.parseLong(id);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        if (!shipRepository.findById(shipID).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Ship responseShip = shipRepository.findById(shipID).get();


        if (ship.getName() != null) {
            if (ShipUtil.isValidName(ship.getName())) responseShip.setName(ship.getName());
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (ship.getPlanet() != null) {
            if (ShipUtil.isValidPlanet(ship.getPlanet())) responseShip.setPlanet(ship.getPlanet());
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (ship.getShipType() != null) {
            if (ShipUtil.isValidType(ship.getShipType())) responseShip.setShipType(ship.getShipType());
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (ship.getProdDate() != null) {
            if (ShipUtil.isValidDate(ship.getProdDate())) responseShip.setProdDate(ship.getProdDate());
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (ship.getCrewSize() != null) {
            System.out.println(ship.getCrewSize());
            if (ShipUtil.isValidCrewSize(ship.getCrewSize())) responseShip.setCrewSize(ship.getCrewSize());
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (ship.getSpeed() != null) {
            if (ShipUtil.isValidSpeed(ship.getSpeed())) responseShip.setSpeed(ship.getSpeed());
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (ship.getUsed() != null) responseShip.setUsed(ship.getUsed());

        ShipUtil.createValidShip(responseShip);
        shipRepository.save(responseShip);

        return new ResponseEntity<>(responseShip, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Ship> deleteShip(String id) {
        long shipId;

        if (ShipUtil.checkIdToValid(id)) {
            shipId = Long.parseLong(id);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        if (shipRepository.findById(shipId).isPresent()) {
            shipRepository.deleteById(shipId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}

