package com.arcs.parkingcounter.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@EqualsAndHashCode
@Entity
@Table(name = "lotcounts")
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfacilitycounts")
    private Integer id;

    @Column(name="lotlocation")
    private String lotLocation;

    @Column(name="totalspaces")
    private Integer totalSpaces;

    @Column(name="availablespaces")
    private Integer availableSpaces;

    @Column(name="vehiclein")
    private Integer vehicleIn;

    @Column(name="vehicleout")
    private Integer vehicleOut;

    @Column(name="open")
    private Boolean gateStatus;

    public Counter() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLotLocation() {
        return lotLocation;
    }

    public void setLotLocation(String lotLocation) {
        this.lotLocation = lotLocation;
    }

    public Integer getTotalSpaces() {
        return totalSpaces;
    }

    public void setTotalSpaces(Integer totalSpaces) {
        this.totalSpaces = totalSpaces;
    }

    public Integer getAvailableSpaces() {
        return availableSpaces;
    }

    public void setAvailableSpaces(Integer availableSpaces) {
        this.availableSpaces = availableSpaces;
    }

    public Integer getVehicleIn() {
        return vehicleIn;
    }

    public void setVehicleIn(Integer vehicleIn) {
        this.vehicleIn = vehicleIn;
    }

    public Integer getVehicleOut() {
        return vehicleOut;
    }

    public void setVehicleOut(Integer vehicleOut) {
        this.vehicleOut = vehicleOut;
    }

    public Boolean getGateStatus() {
        return gateStatus;
    }

    public void setGateStatus(Boolean gateStatus) {
        this.gateStatus = gateStatus;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "id=" + id +
                ", lotLocation='" + lotLocation + '\'' +
                ", totalSpaces=" + totalSpaces +
                ", availableSpaces=" + availableSpaces +
                ", vehicleIn=" + vehicleIn +
                ", vehicleOut=" + vehicleOut +
                ", gateStatus=" + gateStatus +
                '}';
    }
}

