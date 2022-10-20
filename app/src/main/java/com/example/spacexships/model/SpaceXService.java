package com.example.spacexships.model;

import com.annimon.stream.Stream;
import com.example.spacexships.logger.Logger;
import com.example.spacexships.model.db.ShipDao;
import com.example.spacexships.model.db.ShipDbEntity;
import com.example.spacexships.model.network.ShipNetworkEntity;
import com.example.spacexships.model.network.SpaceXApi;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import retrofit2.Response;

public class SpaceXService {
    private ExecutorService executorService;
    private ShipDao shipDao;

    private SpaceXApi spaceXApi;
    private Logger logger;
    public SpaceXService(SpaceXApi spaceXApi,ShipDao shipDao, ExecutorService executorService,Logger logger) {
        this.spaceXApi = spaceXApi;
        this.shipDao = shipDao;
        this.executorService = executorService;
        this.logger = logger;
    }

    public Cancellable getShips (String ship_name, Callback<List<Ship>> callback) {
        Future<?> future = executorService.submit(()->{
            try{
            List<ShipDbEntity> entities = shipDao.getShips();
            List<Ship> ships = convertToShips(entities);
            callback.onResult(ships);

            Response<List<ShipNetworkEntity>> response = spaceXApi.getShips("").execute();
            if(response.isSuccessful()){
                List<ShipDbEntity> newDbShips = networkToDbEntities(response.body());
                shipDao.updateShips(newDbShips);
                callback.onResult(convertToShips(newDbShips));
            }else{
                if(!ships.isEmpty()){
                    RuntimeException exception = new RuntimeException("Error");
                    logger.e(exception);
                    callback.onError(exception);
                }
            }
            }catch (Exception e){
                logger.e(e);
                callback.onError(e);
            }
        });
        return new FutureCancellable(future);
    }
    public Cancellable getShipById(String ship_id, Callback<Ship> callback){
            Future<?> future = executorService.submit(()->{
                try {
                    ShipDbEntity dbEntity = shipDao.getById(ship_id);
                    Ship ships = new Ship(dbEntity);
                    callback.onResult(ships);
                }catch (Exception e){
                    logger.e(e);
                    callback.onError(e);
                }
            });
            return  new FutureCancellable(future);
    }

    private  List<Ship> convertToShips (List<ShipDbEntity> entities){
        return Stream.of(entities)
                .map(Ship::new)
                .toList();
    }

    private List<ShipDbEntity> networkToDbEntities(List<ShipNetworkEntity> entities){
        return  Stream.of(entities)
                .map(shipNetworkEntity -> new ShipDbEntity(shipNetworkEntity))
                .toList();
    }
    static  class FutureCancellable implements Cancellable{
        private Future<?> future;

        public FutureCancellable(Future<?> future) {
            this.future = future;
        }

        @Override
        public void cancel() {
            future.cancel(true);
        }
    }
}
