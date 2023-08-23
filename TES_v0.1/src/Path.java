import java.util.ArrayList;

public class Path extends SimObjectBase{
    public ArrayList<GeoCoordinate> points;

    public Path(int id){
        super(id);
        points = new ArrayList<>();
    }

    public void addPoint(GeoCoordinate pos){
        points.add(pos);
    }
    public void removePoint(int index){
        if(index >= points.size() || index < 0){
            System.out.println("Path::removePoint Fail -> index out of Range");
            return;
        }
        points.remove(index);
    }

    public GeoCoordinate[] getPointsAsArray(){
        GeoCoordinate[] points = new GeoCoordinate[this.points.size()];
        for (int i = 0; i<points.length; i++){
            points[i] = this.points.get(i);
        }
        return  points;
    }




}
