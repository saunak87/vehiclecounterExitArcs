package com.arcs.parkingcounter.repository;

import com.arcs.parkingcounter.entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepo extends JpaRepository<Counter, Integer> {

    Counter findByLotLocation(String lotLocation);

}
