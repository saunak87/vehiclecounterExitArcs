package com.arcs.parkingcounter.service;

import com.arcs.parkingcounter.entity.Counter;

import java.util.List;

public interface CounterService {
    Counter findById(Integer id);
    Counter findByLotLocation(String lotLocation);
    Counter save(Counter counter);
}
