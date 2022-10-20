package com.example.spacexships.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ShipDao {
    @Query("SELECT * FROM ships WHERE ship_id")
    List<ShipDbEntity> getShips();

    @Query("SELECT * FROM ships WHERE ship_id = :ship_id")
    ShipDbEntity getById(String ship_id);

    @Insert
    void  insertShips(List<ShipDbEntity> ships);

    @Delete
    void deleteShips(List<ShipDbEntity> ships);

    @Transaction
    default void updateShips(List<ShipDbEntity> ships){
        deleteShips(ships);
        insertShips(ships);

    }
}
