package com.murtaza.repository;

import com.murtaza.entity.Country;
import com.murtaza.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    List<State> findByCountry(Country country);
}
