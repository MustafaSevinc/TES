package TES.Server.SimObjects;

import TES.Server.Datas.GeoPositionData;

import java.util.ArrayList;

public class Path extends SimObjectBase{
    public ArrayList<GeoPositionData> points;

    public Path(int id, GeoPositionData geoPosition){
        super(id,geoPosition);
        points = new ArrayList<>();
    }


    public void addPoint(GeoPositionData pos){
        points.add(pos);
    }
    public void removePoint(int index){
        if(index >= points.size() || index < 0){
            System.out.println("TESServer.Path::removePoint Fail -> index out of Range");
            return;
        }
        points.remove(index);
    }
}
