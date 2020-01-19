package com.space.service;


import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ShipService implements ShipServiceImp {

    @Autowired
    private ShipRepository shipRepository;

    public List<Ship> filterByIsUsedMinMaxSpeed(boolean isUsed, double minSpeed, double maxSpeed, Pageable pageable) {
        return shipRepository.getAllWithFiltersIsUsedMinMaxSpeed(isUsed, minSpeed, maxSpeed, pageable);
    }

    public List<Ship> filterByShipTypeBeforeMaxSpeed(ShipType shipType, Date before, double maxSpeed, Pageable pageable) {
        return shipRepository.getAllWithFiltersShipTypeBeforeMaxSpeed(shipType, before, maxSpeed, pageable);
    }

    public List<Ship> filterByShipTypeMaxCrewSize(ShipType shipType, int maxCrewSize, Pageable pageable) {
        return shipRepository.getAllWithFiltersShipTypeMaxCrewSize(shipType, maxCrewSize, pageable);
    }

    public List<Ship> filterByShipTypeIsUsed(ShipType shipType, boolean isUsed, Pageable pageable) {
        return shipRepository.getAllWithFiltersShipTypeIsUsed(shipType, isUsed, pageable);
    }

    public List<Ship> filterByNameAfterMaxRating(String name, Date after, double maxRating, Pageable pageable) {
        return shipRepository.getAllWithFiltersNameAfterMaxRating(name, after, maxRating, pageable);
    }

    public List<Ship> filterByMinRatingMinCrewSizeMinSpeed(double minRating, int minCrewSize, double minSpeed, Pageable pageable) {
        return shipRepository.getAllWithFiltersMinRatingMinCrewSizeMinSpeed(minRating, minCrewSize, minSpeed, pageable);
    }

    public List<Ship> filterByAfterBeforeMinCrewMaxCrew(Date after, Date before, int minCrewSize, int maxCrewSize, Pageable pageable) {
        return shipRepository.getAllWithFiltersAfterBeforeMinCrewMaxCrew(after, before, minCrewSize, maxCrewSize, pageable);
    }

    public List<Ship> filterByIsUsedMaxSpeedMaxRating(boolean isUsed, double maxSpeed, double maxRating, Pageable pageable) {
        return shipRepository.getAllWithFiltersIsUsedMaxSpeedMaxRating(isUsed, maxSpeed, maxRating, pageable);
    }

    public List<Ship> filterByIsUsedMinMaxRating(boolean isUsed, double minRating, double maxRating, Pageable pageable) {
        return shipRepository.getAllWithFiltersIsUsedMinMaxRating(isUsed, minRating, maxRating, pageable);
    }

    public List<Ship> filterByShipTypeMinCrewSizeMaxCrewSize(ShipType shipType, int minCrewSize, int maxCrewSize, Pageable pageable) {
        return shipRepository.getAllWithFiltersShipTypeMinCrewSizeMaxCrewSize(shipType, minCrewSize, maxCrewSize, pageable);
    }

    public List<Ship> filterByShipTypeMinSpeedMaxSpeed(ShipType shipType, double minSpeed, double maxSpeed, Pageable pageable) {
        return shipRepository.getAllWithFiltersShipTypeMinSpeedMaxSpeed(shipType, minSpeed, maxSpeed, pageable);
    }

    public List<Ship> filterByShipTypeAfterBefore(ShipType shipType, Date after, Date before, Pageable pageable) {
        return shipRepository.getAllWithFiltersShipTypeAfterBefore(shipType, after, before, pageable);
    }

    public List<Ship> filterByNameAndPage(String name, Pageable pageable) {
        return shipRepository.getAllWithFiltersNamePageNumber(name, pageable);
    }

    public List<Ship> filterByPlanetAndPage(String planet, Pageable pageable) {
        return shipRepository.getAllWithFiltersPlanetPageSize(planet, pageable);
    }

    @Override
    public List<Ship> findAll(Pageable pageable) {
        if (pageable == null) {
            return shipRepository.getAll();
        }
        return shipRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean isExist(long id){
        return shipRepository.existsById(id);
    }

    @Override
    public List<Ship> getAllShips(Optional<String> name, Optional<String> planet, Optional<ShipType> shipType, Optional<Long> after, Optional<Long> before,
                                  Optional<Double> minSpeed, Optional<Double> maxSpeed, Optional<Integer> minCrewSize, Optional<Integer> maxCrewSize,
                                  Optional<Boolean> isUsed, Optional<Double> minRating, Optional<Double> maxRating, Pageable pageable) {
        List<Ship> ships;

        if (after.isPresent() && before.isPresent() && minCrewSize.isPresent() && maxCrewSize.isPresent()) {
            ships = filterByAfterBeforeMinCrewMaxCrew(new Date(after.get()), new Date(before.get()), minCrewSize.get(), maxCrewSize.get(), pageable);
        } else if (name.isPresent() && after.isPresent() && maxRating.isPresent()) {
            ships = filterByNameAfterMaxRating(name.get(), new Date(after.get()), maxRating.get(), pageable);
        } else if (shipType.isPresent() && after.isPresent() && before.isPresent()) {
            ships = filterByShipTypeAfterBefore(shipType.get(), new Date(after.get()), new Date(before.get()), pageable);
        } else if (shipType.isPresent() && minSpeed.isPresent() && maxSpeed.isPresent()) {
            ships = filterByShipTypeMinSpeedMaxSpeed(shipType.get(), minSpeed.get(), maxSpeed.get(), pageable);
        } else if (shipType.isPresent() && minCrewSize.isPresent() && maxCrewSize.isPresent()) {
            ships = filterByShipTypeMinCrewSizeMaxCrewSize(shipType.get(), minCrewSize.get(), maxCrewSize.get(), pageable);
        } else if (isUsed.isPresent() && minRating.isPresent() && maxRating.isPresent()) {
            ships = filterByIsUsedMinMaxRating(isUsed.get(), minRating.get(), maxRating.get(), pageable);
        } else if (isUsed.isPresent() && maxSpeed.isPresent() && maxRating.isPresent()) {
            ships = filterByIsUsedMaxSpeedMaxRating(isUsed.get(), maxSpeed.get(), maxRating.get(), pageable);
        } else if (minRating.isPresent() && minCrewSize.isPresent() && minSpeed.isPresent()) {
            ships = filterByMinRatingMinCrewSizeMinSpeed(minRating.get(), minCrewSize.get(), minSpeed.get(), pageable);
        } else if (shipType.isPresent() && before.isPresent() && maxSpeed.isPresent()) {
            ships = filterByShipTypeBeforeMaxSpeed(shipType.get(), new Date(before.get()), maxSpeed.get(), pageable);
        } else if (isUsed.isPresent() && minSpeed.isPresent() && maxSpeed.isPresent()) {
            ships = filterByIsUsedMinMaxSpeed(isUsed.get(), minSpeed.get(), maxSpeed.get(), pageable);
        } else if (shipType.isPresent() && isUsed.isPresent()) {
            ships = filterByShipTypeIsUsed(shipType.get(), isUsed.get(), pageable);
        } else if (shipType.isPresent() && maxCrewSize.isPresent()) {
            ships = filterByShipTypeMaxCrewSize(shipType.get(), maxCrewSize.get(), pageable);
        } else if (planet.isPresent()) {
            ships = filterByPlanetAndPage(planet.get(), pageable);
        } else if (name.isPresent()) {
            ships = filterByNameAndPage(name.get(), pageable);
        } else {
            ships = findAll(pageable);
        }
        return ships;
    }

    @Override
    public void add(Ship ship) {
        shipRepository.save(ship);
    }

    @Override
    public void delete(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public Ship update(long id, Ship ship) {
        Ship boat = getById(id);
        if (ship.getName() != null)
            boat.setName(ship.getName());
        if (ship.getPlanet() != null)
            boat.setPlanet(ship.getPlanet());
        if (ship.getSpeed() != null && ship.getSpeed() != 0.0)
            boat.setSpeed(ship.getSpeed());
        if (ship.getProdDate() != null)
            boat.setProdDate(ship.getProdDate());
        if (ship.getCrewSize() != null && ship.getCrewSize() != 0)
            boat.setCrewSize(ship.getCrewSize());
        if (ship.getShipType() != null)
            boat.setShipType(ship.getShipType());
        boat.setRating();

        return boat;
    }

    @Override
    public void saveShip(Ship ship){
        shipRepository.save(ship);
    }

    @Override
    public Ship getById(long id) {
        Optional<Ship> optional = shipRepository.findById(id);
        return optional.get();
    }

    @Override
    public boolean isParamsNull(Ship ship){
        return ship.getName() == null || ship.getPlanet() == null || ship.getShipType() == null || ship.getProdDate() == null ||
                ship.getSpeed() == null || ship.getCrewSize() == null;
    }

    @Override
    public boolean isParamsValid(Ship ship) {
        if (ship.getName().isEmpty() || ship.getPlanet().isEmpty() || ship.getSpeed() == 0 ||
                ship.getProdDate().getYear() < 0 || ship.getCrewSize() == 0 || ship.getShipType().name().isEmpty())
            return false;
        if ((ship.getName().length() > 50 || ship.getPlanet().length() > 50) ||
                (ship.getProdDate().getYear() + 1900 < 2800 || ship.getProdDate().getYear()+1900 > 3019) ||
                (ship.getSpeed() < 0.01 || ship.getSpeed() > 0.99) ||
                (ship.getCrewSize() < 1 || ship.getCrewSize() > 9999)) {
            return false;
        }

        return true;
    }
}
