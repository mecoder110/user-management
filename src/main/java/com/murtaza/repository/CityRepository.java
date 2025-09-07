package com.murtaza.repository;

import com.murtaza.entity.City;
import com.murtaza.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByState(State state);
}
