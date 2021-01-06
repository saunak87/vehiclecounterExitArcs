package com.arcs.parkingcounter.controller;

import com.arcs.parkingcounter.entity.Counter;
import com.arcs.parkingcounter.es.esy.playdotv.gpiolib.GPIO;
import com.arcs.parkingcounter.es.esy.playdotv.gpiolib.Mode;
import com.arcs.parkingcounter.es.esy.playdotv.gpiolib.PUD;
import com.arcs.parkingcounter.es.esy.playdotv.gpiolib.RunPythonCode;
import com.arcs.parkingcounter.service.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Controller
public class CounterController {

    @Autowired
    private CounterService counterService;

    private Boolean isSocketClose = false;
    private Boolean isClientConnected = false;

    @Value("${app.lotName}")
    private String lotName;

    static GPIO g22 = new GPIO(22, Mode.OUT);
    static GPIO g25 = new GPIO(25, Mode.IN);
    static GPIO g23 = new GPIO(23, Mode.IN);


    Logger logger = LoggerFactory.getLogger(CounterController.class);

    ServerSocket serverSocket;
    Socket socket = new Socket();
    ObjectOutputStream objectOutputStream;

    private Counter counter;

    public void startCounting() {
        try {
            serverSocket = new ServerSocket(1235);
            serverSocket.setSoTimeout(2000);
            socket = serverSocket.accept();
            System.out.println("Now Connected to " + socket.getRemoteSocketAddress().toString());
        } catch (IOException e) {
            System.out.println("Could not connect to Exit Station");
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        counter = counterService.findByLotLocation(lotName);
        Counter counter1 = new Counter();
//        System.out.println(counter);

        if (counter == null) {
            logger.warn("Could not find lotName. Please ensure lotName is corrected in property file");
        } else {
            parkingSpaceTracker();
            String lotNameInMemory = lotName;
            while (true) {

                if (socket.isClosed()) {
                    try {
                        serverSocket.setSoTimeout(2000);
//                        serverSocket = new ServerSocket(1235);
                        socket = serverSocket.accept();
                        isClientConnected = true;
                    } catch (IOException e) {
                        System.out.println("Could not connect to Exit Gate System");
                        try {
                            socket.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                if (!socket.isClosed()) {
                    try {
                        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject("alive");
//                        socket.sendUrgentData(0);
                    } catch (IOException e) {
                        System.out.println("Client Disconnected");
                        try {
                            socket.close();
                            objectOutputStream.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    if (isClientConnected) {
                        System.out.println("Now Connected to " + socket.getRemoteSocketAddress().toString());
                        isClientConnected = false;
                    }
                }
                counter = counterService.findByLotLocation(lotNameInMemory);
                if (!counter1.equals(counter)) {
                    counter1 = counter;
                    System.out.println("Counter " + counter.toString());
                }

                if (g25.isOFF(PUD.UP)) {
                    int a = 0;
                    if (RunPythonCode.getOutput().equals("0")) {
                        Integer availableSpaceCount = counter.getAvailableSpaces();
                        Integer vehicleOutCount = counter.getVehicleOut();
                        Integer vehicleInCount = counter.getVehicleIn();
                        Integer totalSpaces = counter.getTotalSpaces();
                        if (counter.getAvailableSpaces() == counter.getTotalSpaces()) {
                            System.out.println("Parking Spaces Empty");
                        } else {
                            counter.setVehicleIn(--vehicleInCount);
                            counter.setAvailableSpaces(++availableSpaceCount);
                            counter.setVehicleOut(vehicleOutCount);
                            counterService.save(counter);
//                            System.out.println(counter);
                        }
                        while (g25.isOFF(PUD.UP)) {

                        }
                        parkingSpaceTracker();
                    }
                }


                if (g23.isOFF(PUD.UP)) {
                    System.out.println("Car on exit loop");
                    isSocketClose = true;


                    if (g23.isOFF(PUD.UP)) {
                        if (!socket.isClosed()) {
                            try {
//                                serverSocket = new ServerSocket(1235);
//                                socket = serverSocket.accept();
                                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                objectOutputStream.reset();
                                objectOutputStream.writeObject("onloop");
                            } catch (IOException e) {
                                System.out.println("Could not connect to exit station");
                            }
                        }
                        while (g23.isOFF(PUD.UP)) {

                        }
                    }
                }

                if (isSocketClose) {
                    System.out.println("off the loop");
                    isSocketClose = false;
                    if (socket.isConnected()) {
                        try {
                            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                            objectOutputStream.writeObject("offloopB");

//                            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//                            String message = (String) objectInputStream.readObject();
//                            System.out.println("Received message from client " + message);

                        } catch (IOException e) {
                            System.out.println("Could not connect");
                        }
                    }
                }
            }

        }
    }


    private void parkingSpaceTracker() {
//        System.out.println(counter);
        if (counter.getAvailableSpaces() > 0) {
            g22.setOFF();
        }
        if (counter.getAvailableSpaces() <= 0) {
            g22.setON();
            System.out.println("Lot Full Sign On : " + counter.getAvailableSpaces() + "Space Available");
        }
    }
}




