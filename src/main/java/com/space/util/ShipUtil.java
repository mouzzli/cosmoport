package com.space.util;

import com.space.model.Ship;
import com.space.model.ShipType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

public class ShipUtil {

    public static boolean isValid(Ship ship) {
        if (!isValidName(ship.getName())) return false;
        if (!isValidPlanet(ship.getPlanet())) return false;
        if (!isValidType(ship.getShipType())) return false;
        if(!isValidDate(ship.getProdDate())) return false;
        if(!isValidSpeed(ship.getSpeed())) return false;
        return (isValidCrewSize(ship.getCrewSize()));
    }

    public static boolean checkIdToValid(String id) {
        long shipId;
        try {
            shipId = Long.parseLong(id);
            if (shipId < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isValidName(String name) {
        return name != null && !name.isEmpty() && name.length() <= 50;
    }

    public static boolean isValidPlanet(String planet) {
        return planet != null && !planet.isEmpty() && planet.length() <= 50;
    }

    public static boolean isValidType(ShipType shipType) {
        return shipType != null;
    }

    public static boolean isValidDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return  cal.get(Calendar.YEAR) >= 2800 && cal.get(Calendar.YEAR) <= 3019;
    }

    public static boolean isValidSpeed(Double speed) {
        return speed != null &&  speed > 0 && speed < 0.996;
    }

    public static boolean isValidCrewSize(Integer crewSize) {
        return crewSize != null && crewSize > 0 && crewSize < 10000;
    }

    private static Boolean setIsUsed(Boolean isUsed) {
        return isUsed == null ? false : isUsed;
    }

    public static Ship createValidShip(Ship ship) {
        ship.setUsed(setIsUsed(ship.getUsed()));
        ship.setRating(calcRating(ship));
        ship.setSpeed(round(ship.getSpeed()));
        return ship;
    }

    private static double calcRating(Ship ship) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ship.getProdDate());
        int currentYear = 3019;
        double k = ship.getUsed() ? 0.5 : 1 ;
        return round((80 * ship.getSpeed() * k) / (currentYear - calendar.get(Calendar.YEAR) + 1));
    }

    public static double round(Double d) {
        BigDecimal result = new BigDecimal(d);
        result =  result.setScale(2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

}
