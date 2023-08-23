import java.awt.*;
import java.util.ArrayList;

public class Path extends SimObjectBase{
    public ArrayList<Position> points;

    public Path(int id){
        super(id);
        points = new ArrayList<>();
    }

    public void addPoint(Position pos){
        points.add(pos);
    }
    public void removePoint(int index){
        if(index >= points.size() || index < 0){
            System.out.println("Path::removePoint Fail -> index out of Range");
            return;
        }
        points.remove(index);
    }

    public Position[] getPointsAsArray(){
        Position[] points = new Position[this.points.size()];
        for (int i = 0; i<points.length; i++){
            points[i] = this.points.get(i);
        }
        return  points;
    }




}
