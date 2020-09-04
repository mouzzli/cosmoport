package com.space.util;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;


public class ShipSpecifications {

    public static Specification<Ship> getSpecification(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {

        return Specification.where(ShipSpecifications.filterByName(name))
                .and(ShipSpecifications.filterByPlanet(planet))
                .and(ShipSpecifications.filterByShipType(shipType))
                .and(ShipSpecifications.filterByDate(after, before))
                .and(ShipSpecifications.filterByIsUsed(isUsed))
                .and(ShipSpecifications.filterBySpeed(minSpeed, maxSpeed))
                .and(ShipSpecifications.filterByCrewSize(minCrewSize, maxCrewSize))
                .and(ShipSpecifications.filterByRating(minRating, maxRating));
    }

    public static Specification<Ship> filterByName(String name) {
        return (root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Ship> filterByPlanet(String planet) {
        return (root, query, criteriaBuilder) -> planet == null ? null : criteriaBuilder.like(root.get("planet"), "%" + planet + "%");
    }

    public static Specification<Ship> filterByShipType(ShipType shipType) {
        return (root, query, criteriaBuilder) -> shipType == null ? null : criteriaBuilder.equal(root.get("shipType"), shipType);
    }



    public static Specification<Ship> filterByDate(Long after, Long before) {
        return (root, query, criteriaBuilder) -> after == null && before == null ? null : after == null  ?
                criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"),new Date(before)) :  before == null ?
                criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"), new Date(after)) :
                criteriaBuilder.between(root.get("prodDate"), new Date(after ), new Date(before ));
    }


    public static Specification<Ship> filterByIsUsed(Boolean isUsed) {
        return (root, query, criteriaBuilder) -> isUsed == null ? null : isUsed ? criteriaBuilder.isTrue(root.get("isUsed")) : criteriaBuilder.isFalse(root.get("isUsed"));
    }


    public static Specification<Ship> filterBySpeed(Double minSpeed, Double maxSpeed) {
        return (root, query, criteriaBuilder) -> minSpeed == null && maxSpeed == null ? null : minSpeed == null ?
                criteriaBuilder.lessThanOrEqualTo(root.get("speed"), maxSpeed): maxSpeed == null ?
                criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), minSpeed) :
                criteriaBuilder.between(root.get("speed"), minSpeed, maxSpeed);
    }


    public static Specification<Ship> filterByCrewSize(Integer minCrewSize, Integer maxCrewSize) {
        return (root, query, criteriaBuilder) -> minCrewSize == null && maxCrewSize == null ? null : minCrewSize == null ?
                criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize) : maxCrewSize == null ?
                criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize) :
                criteriaBuilder.between(root.get("crewSize"), minCrewSize, maxCrewSize);
    }


    public static Specification<Ship> filterByRating(Double minRating, Double maxRating) {
        return (root, query, criteriaBuilder) -> minRating == null && maxRating == null ? null : minRating == null ?
                criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating) : maxRating == null ?
                criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating) :
                criteriaBuilder.between(root.get("rating"), minRating, maxRating);
    }
}
