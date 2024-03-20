import java.awt.*;


public class MazeCell {

    private Point location;
    private boolean hasTopWall;
    private boolean hasBotWall;
    private boolean hasLeftWall;
    private boolean hasRightWall;
    private boolean isS;
    private boolean isF;

    public MazeCell(Point location, boolean start){
        this.location = location;
        hasBotWall = true;
        hasTopWall = true;
        hasLeftWall = true;
        hasRightWall = true;
        isS = start;
        isF = false;
    }

    public void setMazeWalls(boolean left, boolean right, boolean top, boolean bot){
        this.hasRightWall = right;
        this.hasTopWall = top;
        this.hasLeftWall = left;
        this.hasBotWall = bot;
        if (hasBotWall && hasRightWall && hasLeftWall && hasTopWall){
            System.out.println("Error, cell cannot be created as there is no possible movement into or out of the cell");
            System.exit(-9);
        }
    }

    public void swapWall(char id, boolean opp){
        if(opp) {
            switch (id) {
                case 'L':
                    this.hasRightWall = !this.hasRightWall;
                    break;
                case 'R':
                    this.hasLeftWall = !this.hasLeftWall;
                    break;
                case 'T':
                    this.hasBotWall = !this.hasBotWall;
                    break;
                case 'B':
                    this.hasTopWall = !this.hasTopWall;
                    break;
            }
        }
        else {
            switch (id) {
                case 'L':
                    this.hasLeftWall = !this.hasLeftWall;
                    break;
                case 'R':
                    this.hasRightWall = !this.hasRightWall;
                    break;
                case 'T':
                    this.hasTopWall = !this.hasTopWall;
                    break;
                case 'B':
                    this.hasBotWall = !this.hasBotWall;
                    break;
            }
        }
    }

    public void setFinish(boolean finish){
        isF = finish;
    }

    public void setStart(boolean start){
        isS = start;
        if (start){
            hasTopWall = false;
        }
    }

    public boolean isS(){
        return isS;
    }

    public boolean isF(){
        return isF;
    }

    public boolean hasBottomWall() {
        return hasBotWall;
    }
    public boolean hasTopWall() {
        return hasTopWall;
    }
    public boolean hasLeftWall() {
        return hasLeftWall;
    }
    public boolean hasRightWall() {
        return hasRightWall;
    }


}
