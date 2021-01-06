package com.arcs.parkingcounter.config;


import com.arcs.parkingcounter.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class KeepConnectionAlive {
    @Autowired
    private CounterService counterService;

    @Value("${app.lotName")
    private String lotName;

    @Scheduled(fixedRate = 500000)
    public void keepConnnectionAlive(){
        if(lotName.equals("")){
            System.out.println("lot Name not found");
        }else{
            counterService.findByLotLocation(lotName);
            System.out.println("Database connection good");
        }
    }

}
