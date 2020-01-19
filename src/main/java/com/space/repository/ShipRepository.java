package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ShipRepository extends PagingAndSortingRepository<Ship, Long> {

    Page<Ship> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM ship", nativeQuery = true)
    List<Ship> getAll();

    @Query("SELECT ship FROM Ship ship WHERE ship.name LIKE %:name%")
    List<Ship> getAllWithFiltersNamePageNumber(@Param("name") String name, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.planet LIKE %:planet%")
    List<Ship> getAllWithFiltersPlanetPageSize(@Param("planet") String planet, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.shipType LIKE :shipType AND ship.prodDate > :after AND ship.prodDate < :before")
    List<Ship> getAllWithFiltersShipTypeAfterBefore(@Param("shipType") ShipType shipType, @Param("after") Date after, @Param("before") Date before, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.shipType LIKE :shipType AND ship.speed >= :minSpeed AND ship.speed <= :maxSpeed")
    List<Ship> getAllWithFiltersShipTypeMinSpeedMaxSpeed(@Param("shipType") ShipType shipType, @Param("minSpeed") double minSpeed, @Param("maxSpeed") double maxSpeed, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.shipType LIKE :shipType AND ship.crewSize >= :minCrewSize AND ship.crewSize <= :maxCrewSize")
    List<Ship> getAllWithFiltersShipTypeMinCrewSizeMaxCrewSize(@Param("shipType") ShipType shipType, @Param("minCrewSize") int minCrewSize, @Param("maxCrewSize") int maxCrewSize, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.isUsed = :isUsed AND ship.rating >= :minRating AND ship.rating <= :maxRating")
    List<Ship> getAllWithFiltersIsUsedMinMaxRating(@Param("isUsed") boolean isUsed, @Param("minRating") double minRating, @Param("maxRating") double maxRating, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.isUsed = :isUsed AND ship.speed <= :maxSpeed AND ship.rating <= :maxRating")
    List<Ship> getAllWithFiltersIsUsedMaxSpeedMaxRating(@Param("isUsed") boolean isUsed, @Param("maxSpeed") double maxSpeed, @Param("maxRating") double maxRating, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.prodDate >= :after AND ship.prodDate <= :before AND ship.crewSize >= :minCrewSize AND ship.crewSize <= :maxCrewSize")
    List<Ship> getAllWithFiltersAfterBeforeMinCrewMaxCrew(@Param("after") Date after, @Param("before") Date before, @Param("minCrewSize") int minCrewSize, @Param("maxCrewSize") int maxCrewSize, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.rating >= :minRating AND ship.crewSize >= :minCrewSize AND ship.speed >= :minSpeed")
    List<Ship> getAllWithFiltersMinRatingMinCrewSizeMinSpeed(@Param("minRating") double minRating, @Param("minCrewSize") int minCrewSize, @Param("minSpeed") double minSpeed, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.name LIKE %:name% AND ship.prodDate >= :after AND ship.rating <= :maxRating")
    List<Ship> getAllWithFiltersNameAfterMaxRating(@Param("name") String name, @Param("after") Date after, @Param("maxRating") double maxRating, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.shipType LIKE :shipType AND ship.isUsed = :isUsed")
    List<Ship> getAllWithFiltersShipTypeIsUsed(@Param("shipType") ShipType shipType, @Param("isUsed") boolean isUsed, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.shipType LIKE :shipType AND ship.crewSize <= :maxCrewSize")
    List<Ship> getAllWithFiltersShipTypeMaxCrewSize(@Param("shipType") ShipType shipType, @Param("maxCrewSize") int maxCrewSize, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.shipType LIKE :shipType AND ship.prodDate <= :before AND ship.speed <= :maxSpeed")
    List<Ship> getAllWithFiltersShipTypeBeforeMaxSpeed(@Param("shipType") ShipType shipType, @Param("before") Date before, @Param("maxSpeed") double maxSpeed, Pageable pageable);

    @Query("SELECT ship FROM Ship ship WHERE ship.isUsed = :isUsed AND ship.speed >= :minSpeed AND ship.speed <= :maxSpeed")
    List<Ship> getAllWithFiltersIsUsedMinMaxSpeed(@Param("isUsed") boolean isUsed, @Param("minSpeed") double minSpeed, @Param("maxSpeed") double maxSpeed, Pageable pageable);
}




