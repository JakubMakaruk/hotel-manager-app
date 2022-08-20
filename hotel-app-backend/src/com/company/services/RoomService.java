package com.company.services;

import com.company.models.Room;

import java.sql.SQLException;
import java.util.List;

public interface RoomService {
    boolean createRoom(String roomName, String numberOfBeds, String roomNumber) throws SQLException;
    List<Room> getAllRooms() throws SQLException;
    Room getRoomByRoomNumber(Integer roomNumber) throws SQLException;
    List<Room> getAvailableRooms() throws SQLException;
    boolean updateRoom(Room room) throws SQLException;
}
