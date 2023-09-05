package TES.Server;

import TES.Server.Datas.CommandData;
import TES.Server.Datas.GeoPositionData;
import TES.Server.Datas.MovementData;
import TES.Server.Executors.*;
import TES.Server.SimObjects.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulator {

    private ExecutionManager exMan;
    private HashMap<Integer, SimObjectBase> simObjects;
    private ConcurrentLinkedQueue<MovementData> updatingList;
    private Timer updateTimer;
    private ConcurrentLinkedQueue changes;
    private long tickInterval;
    private long delay;


    public Simulator(long updateDelay, long updateTickIntervalMS) {
        simObjects = new HashMap<>();
        updatingList = new ConcurrentLinkedQueue<>();
        changes = new ConcurrentLinkedQueue<>();
        updateTimer = new Timer();
        exMan = new ExecutionManager(this);
        configureUpdateTimer(updateDelay, updateTickIntervalMS);
    }

    private void configureUpdateTimer(long delay, long tickInterval) {
        this.delay = delay;
        this.tickInterval = tickInterval;
        updateTimer.scheduleAtFixedRate(getUpdateTask(), delay, tickInterval);
    }

    private TimerTask getUpdateTask() {
        TimerTask update = new TimerTask() {
            @Override
            public void run() {
                for (MovementData movObj : updatingList) {
                    if (movObj.isMoving) {
                        updatePos(movObj, (double) tickInterval / 1000);
                        System.out.println(movObj.simObj.geoPosition);
                    }
                }
            }
        };
        return update;
    }

    public void close() {
        updateTimer.cancel();
    }

    public void execute(CommandData cmd) {
        if (exMan.execute(cmd))
            System.out.println("TESServer.Simulator::execute - execution completed");
    }

    /********************************************          Simulasyon Dünyasını Mıncıklayan Metotlar          **********************************************/

    private void updatePos(MovementData movObj, double dT) {
        movObj.simObj.geoPosition = calcCoordChange(movObj, dT);
    }

    public boolean addObject(SimObjectBase simObject) {
        if (simObjects.get(simObject.id) != null) {
            System.out.printf("TESServer.Simulator::addObject FAIL. SimObj with id %s already exists.", simObject.id);
            return false;
        }
        simObjects.put(simObject.id, simObject);
        return true;
    }

    public void removeObject(int id) {
        updatingList.stream().filter(movData -> movData.simObj.id == id).findAny().ifPresent(mov -> updatingList.remove(mov));
        simObjects.remove(id);
    }

    public boolean addPointToPath(int pathId, GeoPositionData pos) {
        if (!(simObjects.get(pathId) instanceof Path path)) {
            System.out.printf("TESServer.Path with id %s coulden't find", pathId);
            return false;
        }
        path.addPoint(pos);
        return true;
    }

    public boolean addPathToTrack(int trackId, int pathId) {
        MovementData mov = getMovementData(trackId);
        if (!(simObjects.get(pathId) instanceof Path path)) {
            System.out.printf("TESServer.Path with id %s coulden't find", pathId);
            System.out.println(simObjects);
            return false;
        }
        mov.geoPositions.addAll(path.points);
        return true;
    }

    public boolean setSpeedOfObj(int id, double speed) {
        MovementData mov = getMovementData(id);
        mov.speed = speed;
        return true;
    }

    public boolean setPosOfObj(int id, GeoPositionData pos) {
        SimObjectBase simObj = simObjects.get(id);
        if (!Double.isNaN(pos.lon)) {
            simObj.geoPosition.lon = pos.lon;
        }
        if (!Double.isNaN(pos.lat)) {
            simObj.geoPosition.lat = pos.lat;
        }
        if (!Double.isNaN(pos.alt)) {
            simObj.geoPosition.alt = pos.alt;
        }
        return true;
    }

    /*******************************************************************************************************************************************************/





/*
    //Sim Obje Vermek Bertan Abinin Fikriydi
        private TESServer.SimObjectBase getSimObject(int id) {
        return simObjects.get(id);
    }*/
    public boolean setTrackMoving(int id, boolean isMoving) {
        getMovementData(id).isMoving = isMoving;
        return true;
    }


    private MovementData getMovementData(int objID) {
        MovementData mov = updatingList.stream().filter(movData -> movData.simObj.id == objID).findAny().orElse(null);
        if (mov == null) {
            mov = new MovementData(simObjects.get(objID), 0);
            updatingList.add(mov);
        }
        return mov;
    }


    /*  public boolean addTargetPoint(int simObjId, TESServer.GeoPosition geoPosition) {
          for (TESServer.MovementData kinematic : updatingList) {
              if (kinematic.simObj.id == simObjId) {
                  kinematic.geoPositions.add(geoPosition);
              }
          }
          TESServer.MovementData trackMovement = new TESServer.MovementData((TESServer.Track) simObjects.get(simObjId), 0);
          updatingList.add(trackMovement);

          System.out.println("TESServer.Simulator::addTargetCoord");
          return true;
      }

      public boolean startMotion(int simObjId, double speed) {
          for (TESServer.MovementData kinematic : updatingList) {
              if (kinematic.simObj.id == simObjId) {
                  kinematic.speed = speed;
                  kinematic.isMoving = true;
                  return true;
              }
          }
          return false;
      }
  */
/*
    public boolean moveOnPath(int id, int pathId) {
        TESServer.SimObjectBase obj = simObjects.get(pathId);
        if (!(obj instanceof TESServer.Path path)) {
            // todo log error
            return false;
        }
        for (TESServer.GeoPosition p : path.points){
            addTargetPoint(id,p);
        }
    }
*/


    /***********************************************************    COMMAND OLUŞTURMA İŞLEMLERİ    **********************************************************************/
    public CommandData createCommand(SimObjectBase simObj) {
        HashMap<String, String> keyValues = new HashMap();
        keyValues.put("lon", String.valueOf(simObj.geoPosition.lon));
        keyValues.put("lat", String.valueOf(simObj.geoPosition.lat));
        keyValues.put("alt", String.valueOf(simObj.geoPosition.alt));
        CommandData cmd = new CommandData("update_position", keyValues);
        return cmd;
    }
    private boolean sendSimObjData(Track track) {
        HashMap trackData = new HashMap<>();
        trackData.put("x", track.geoPosition.lon);
        trackData.put("y", track.geoPosition.lat);
        trackData.put("z", track.geoPosition.alt);
        trackData.put("id", track.id);
        CommandData cmd = new CommandData("UpdateView", trackData);
        System.out.println("Comamnd Send:");
        System.out.println(cmd.toString());
        return true;
    }


    public void printSimObjectNames() {
        simObjects.forEach((key, value) -> {
            System.out.println(key.toString() + value.toString());
        });
    }


    /*****************************************************************    GEO CALCULATIONS   ****************************************************************************/

    // TODO Mustafa method basına 20 comment line

    /**
     * Geçen Süre Boyunca Objenin ilerlemesi Gerektiği Miktar
     */
    private GeoPositionData calcCoordChange(MovementData mov, double dtSec) {
        //2 update arasında geçen sürede alınması gereken yolu hesapla
        double distToMove = mov.speed * dtSec;
        //Asıl pozisyondan kaç nokta ve metre uzakta olduğunu hesapla
        GeoPositionData currentPos = mov.simObj.geoPosition;
        GeoPositionData nextPos;
        for (int i = mov.targetPositionIndex; i < mov.geoPositions.size(); i++) {
            nextPos = mov.getNextPoint();
            double temp_dist = distance(currentPos, nextPos);
            if (distToMove > temp_dist) {
                currentPos = nextPos;
                mov.targetPositionIndex += 1;
                distToMove -= temp_dist;
                continue;
            }
            double distRatio = distToMove / temp_dist;
            //Üzerine eklenilmesi gereken miktarı bul ve döndür
            return new GeoPositionData(
                    (nextPos.lon - currentPos.lon) * distRatio,
                    (nextPos.lat - currentPos.lat) * distRatio,
                    (nextPos.alt - currentPos.alt) * distRatio);

        }
        mov.targetPositionIndex -= 1;
        mov.isMoving = false;
        return mov.getNextPoint();

    }

    private double distance(GeoPositionData pos1, GeoPositionData pos2) {
        final int R = 6371; // Earth is totaly circle
        //2 lat ve lon arasındaki radyanı hesapla
        double latDistance = Math.toRadians(pos2.lat - pos1.lat);
        double lonDistance = Math.toRadians(pos2.lon - pos1.lon);
        //2 nokta arasındaki mesafeyi yarıçapa oranına göre hesapla
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(pos1.lat)) * Math.cos(Math.toRadians(pos2.lat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        //Aradaki mesafeyi metre cinsinden hesapla
        double distance = R * c * 1000;
        double height = pos1.alt - pos2.alt;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }

    ArrayList<CommandData> getChanges() {
        ArrayList<CommandData> res = new ArrayList();
        synchronized (changes) {
            while (!changes.isEmpty()) {
                res.add((CommandData) changes.poll());
            }
        }
        return res;
    }
}
