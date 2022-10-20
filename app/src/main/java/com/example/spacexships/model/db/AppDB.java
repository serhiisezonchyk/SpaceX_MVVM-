package com.example.spacexships.model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {ShipDbEntity.class})
public abstract class AppDB extends RoomDatabase {
    public abstract ShipDao getShipDao();
}
