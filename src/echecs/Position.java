package echecs;



/**
 *
 * @author samuel
 */
public class Position {
    private int x,y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private String getCharForNumber(int i) {
        return i >= 0 && i < 26 ? String.valueOf((char)(i + 97)) : null;
    }
    
    @Override
    public String toString(){
        return getCharForNumber(x)+(y+1);        
    }
    
    public boolean equals(Object o){
        if (o==null) return false;
        
        if (!(o instanceof Position)){
           return false;
        }
        
        if (((Position)o).x != x) return false;
        if (((Position)o).y != y) return false;
        
        return true;
        
    }
    
}