package com.company.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.time.LocalDate;

@DatabaseTable(tableName = "reservations")
public class Reservation implements Serializable {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    private Customer customer;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    private Room room;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private LocalDate startTime;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private LocalDate endTime;

    public Reservation() {
    }

    public Reservation(Customer customer, Room room, LocalDate startTime, LocalDate endTime) {
        this.customer = customer;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public LocalDate getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }
    public LocalDate getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }
}
