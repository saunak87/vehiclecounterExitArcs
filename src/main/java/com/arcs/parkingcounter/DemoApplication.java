package com.arcs.parkingcounter;

import com.arcs.parkingcounter.config.KeepConnectionAlive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication implements ApplicationRunner{

    @Autowired
    private KeepConnectionAlive connectionAlive;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
        ctx.close();
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        connectionAlive.keepConnnectionAlive();
    }
}
