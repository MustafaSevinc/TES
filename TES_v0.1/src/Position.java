public class Position {
    public double x;
    public double y;
    public double z;

    public Position(double x,double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public void add(Position pos){
        this.x += pos.x;
        this.y += pos.y;
        this.z += pos.z;
    }

}
