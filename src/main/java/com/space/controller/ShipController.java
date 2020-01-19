package com.space.controller;


import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import com.space.service.ShipServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ShipController {

    @Autowired
    ShipService shipService;

    @Autowired
    ShipRepository shipRepository;



    //CREATE
    @RequestMapping(value = "rest/ships", method = RequestMethod.POST)
   // @PostMapping("/ships")
    public ResponseEntity createShip(@RequestBody Ship ship) {

        if (!shipService.isParamsNull(ship) && shipService.isParamsValid(ship) ) {
            ship.setIsUsed(ship.getIsUsed() == null ? false : ship.getIsUsed());
            ship.setRating();
            shipService.add(ship);
            return new ResponseEntity(ship, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }




    //GET
    @RequestMapping(value = "rest/ships/{id}", method = RequestMethod.GET)
    public ResponseEntity getShip(@PathVariable("id") long id) {
        if (id == 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return !shipService.isExist(id) ?
                new ResponseEntity(HttpStatus.NOT_FOUND) :
                new ResponseEntity(shipService.getById(id),HttpStatus.OK);
    }

    //DELETE
    @RequestMapping(value = "rest/ships/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteShip(@PathVariable("id") long id) {
        if (id > shipService.findAll(null).size()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (id == 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        shipService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    //UPDATE
    @RequestMapping(value = "rest/ships/{id}", method = RequestMethod.POST)
    public ResponseEntity editShip(@PathVariable long id, @RequestBody Ship ship) {
        if (id == 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (!shipService.isExist(id)) return new ResponseEntity(HttpStatus.NOT_FOUND);
        Ship updatedShip = shipService.update(id, ship);
        if (shipService.isParamsValid(updatedShip)) {
            shipService.saveShip(updatedShip);
            return new ResponseEntity(updatedShip, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "rest/ships", method = RequestMethod.GET)
    public ResponseEntity<List<Ship>> getFiltredShips(@RequestParam(value = "pageNumber") Optional<Integer> page,
                                                      @RequestParam(value = "pageSize")   Optional<Integer> pageSize,
                                                      @RequestParam(value = "order")      Optional<ShipOrder> shipOrder,
                                                      @RequestParam(value = "name")       Optional<String> name,
                                                      @RequestParam(value = "planet")     Optional<String> planet,
                                                      @RequestParam(value = "shipType")   Optional<ShipType> shipType,
                                                      @RequestParam(value = "after")      Optional<Long> after,
                                                      @RequestParam(value = "before")     Optional<Long> before,
                                                      @RequestParam(value = "minSpeed")   Optional<Double> minSpeed,
                                                      @RequestParam(value = "maxSpeed")   Optional<Double> maxSpeed,
                                                      @RequestParam(value = "minCrewSize")Optional<Integer> minCrewSize,
                                                      @RequestParam(value = "maxCrewSize")Optional<Integer> maxCrewSize,
                                                      @RequestParam(value = "isUsed")     Optional<Boolean> isUsed,
                                                      @RequestParam(value = "minRating")  Optional<Double> minRating,
                                                      @RequestParam(value = "maxRating")  Optional<Double> maxRating) {

        String order = shipOrder.isPresent() ? shipOrder.get().getFieldName() : "id";
        Pageable pageable = new PageRequest(page.orElse(0), pageSize.orElse(3), Sort.by(order));
        return new ResponseEntity<>(shipService.getAllShips(name, planet, shipType, after,
                before, minSpeed, maxSpeed, minCrewSize, maxCrewSize,
                isUsed, minRating, maxRating, pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "rest/ships/count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getShipsCount (@RequestParam(value = "pageNumber") Optional<Integer> page,
                                                  @RequestParam(value = "pageSize")   Optional<Integer> pageSize,
                                                  @RequestParam(value = "order")      Optional<ShipOrder> shipOrder,
                                                  @RequestParam(value = "name")       Optional<String> name,
                                                  @RequestParam(value = "planet")     Optional<String> planet,
                                                  @RequestParam(value = "shipType")   Optional<ShipType> shipType,
                                                  @RequestParam(value = "after")      Optional<Long> after,
                                                  @RequestParam(value = "before")     Optional<Long> before,
                                                  @RequestParam(value = "minSpeed")   Optional<Double> minSpeed,
                                                  @RequestParam(value = "maxSpeed")   Optional<Double> maxSpeed,
                                                  @RequestParam(value = "minCrewSize")Optional<Integer> minCrewSize,
                                                  @RequestParam(value = "maxCrewSize")Optional<Integer> maxCrewSize,
                                                  @RequestParam(value = "isUsed")     Optional<Boolean> isUsed,
                                                  @RequestParam(value = "minRating")  Optional<Double> minRating,
                                                  @RequestParam(value = "maxRating")  Optional<Double> maxRating) {
        return new ResponseEntity<>(shipService.getAllShips(name, planet, shipType, after,
                before, minSpeed, maxSpeed, minCrewSize, maxCrewSize,
                isUsed, minRating, maxRating, null).size(), HttpStatus.OK);
    }


    /*
    @GetMapping("/ship")
    public String getShip(){
       return shipService.getShip();
    }
     */
}
