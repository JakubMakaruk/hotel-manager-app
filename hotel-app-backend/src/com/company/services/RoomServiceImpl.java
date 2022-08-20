package com.company.services;

import com.company.models.Room;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class RoomServiceImpl implements RoomService {
    Dao<Room, Integer> roomDao;

    public RoomServiceImpl(Dao<Room, Integer> roomDao) {
        this.roomDao = roomDao;
    }


    @Override
    public boolean createRoom(String roomNumber, String roomName, String numberOfBeds) throws SQLException {
        Room room = new Room(roomNumber, roomName, numberOfBeds, true);
        return roomDao.create(room) == 1;
    }

    @Override
    public List<Room> getAllRooms() throws SQLException {
        return roomDao.queryForAll();
    }
    @Override
    public Room getRoomByRoomNumber(Integer roomNumber) throws SQLException {
        return roomDao.queryForEq("roomNumber", roomNumber).get(0);
    }
    @Override
    public List<Room> getAvailableRooms() throws SQLException {
        return roomDao.queryForEq("isFree", true);
    }
    @Override
    public boolean updateRoom(Room room) throws SQLException {
        return roomDao.update(room) == 1;
    }
}
