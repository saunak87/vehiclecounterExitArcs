package com.arcs.parkingcounter.service;

import com.arcs.parkingcounter.entity.Counter;
import com.arcs.parkingcounter.repository.CounterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterRepo counterRepo;

    @Override
    public Counter findById(Integer id) {
        Optional<Counter> result = counterRepo.findById(id);
        Counter counter;
        if (result.isPresent()) {
            counter = result.get();
        } else {
            throw new RuntimeException("Id cannot be found");
        }
        return counter;
    }

    @Override
    public Counter findByLotLocation(String lotLocation) {
        return counterRepo.findByLotLocation(lotLocation);
    }

    @Override
    public Counter save(Counter counter) {
        return counterRepo.save(counter);
    }
}
