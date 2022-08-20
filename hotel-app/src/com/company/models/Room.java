package com.company.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "rooms")
public class Room implements Serializable {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;
    @DatabaseField(canBeNull = false)
    private String roomNumber;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private String numberOfBeds;
    @DatabaseField(canBeNull = false)
    private boolean isFree;

    public Room() {
    }

    public Room(String roomNumber, String name, String numberOfBeds, boolean isFree) {
        this.roomNumber = roomNumber;
        this.name = name;
        this.numberOfBeds = numberOfBeds;
        this.isFree = isFree;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }



    public String getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(String numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public String toString() {
        return this.getName() + " | " + this.getRoomNumber() + " | " + this.getNumberOfBeds();
    }
}
