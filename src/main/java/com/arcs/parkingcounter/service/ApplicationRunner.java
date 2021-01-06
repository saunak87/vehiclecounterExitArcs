package com.arcs.parkingcounter.service;

import com.arcs.parkingcounter.controller.CounterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {
    @Autowired
    CounterController counterController;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        counterController.startCounting();
    }

}
