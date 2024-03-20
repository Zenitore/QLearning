

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Maze {
    private ArrayList<ArrayList<Character>> maze;
    private ArrayList<ArrayList<MazeCell>> mazeCells;
    private static final String end = "F";
    private static final String start = "S";
    private static final String wall = "|";
    private static final String roof = "-";
    private static final String path = "+";
    private static final String hole = " ";
    private int height;
    private int width;
    private Random r;
    private Point startPoint;

    public Maze(int width, int height){
        this.height = height;
        this.width = width;
        this.r = new Random();
    }

    public void generateMaze() {
        this.mazeCells = new ArrayList<ArrayList<MazeCell>>();
        for (int i = 0; i < this.height; i++) {
            this.mazeCells.add(new ArrayList<MazeCell>());
            for (int c = 0; c < this.width; c++) {
                this.mazeCells.get(i).add(new MazeCell(new Point(c, i), false));
            }
        }
        Stack<Point> stack = new Stack<Point>();
        int randSlocation = r.nextInt(width);
        stack.push(new Point(randSlocation, 0));
        this.mazeCells.get(0).add(new MazeCell(new Point(randSlocation,0), true));
        mazeCells.get(0).get(randSlocation).setStart(true);
        this.startPoint = new Point(randSlocation,0);
        ArrayList<Point> alreadySearched = new ArrayList<Point>();
        ArrayList<Point> toSearch = new ArrayList<Point>();
        Point exit = new Point();
        while (stack.size() > 0) {
            //PUSH NEW POTENTIALS ONTO THE STACK
            Point curLoc = stack.peek();
            int currX = (int) curLoc.getX();
            int currY = (int) curLoc.getY();
            Point above = new Point(currX, currY - 1);
            Point below = new Point(currX, currY + 1);
            Point left = new Point(currX - 1, currY);
            Point right = new Point(currX + 1, currY);
            char dir = ' ';
            ArrayList<Point> potentials = new ArrayList<Point>();
            if (!alreadySearched.contains(new Point(above))) {
                //Haven't searched the one above this one
                if (!(currY == 0)) {
                    potentials.add(new Point(above));
                }
            }
            if (!alreadySearched.contains(new Point(below))) {
                //Haven't searched the one below this one
                if (!(currY == this.height - 1)) {
                    potentials.add(new Point(below));
                }
            }
            if (!alreadySearched.contains(new Point(left))) {
                //Haven't searched the one to the left of this one
                if (!(currX == 0)) {
                    potentials.add(new Point(left));
                }
            }
            if (!alreadySearched.contains(new Point(right))) {
                //Haven't searched the one to the right of this one
                if (!(currX == this.width - 1)) {
                    potentials.add(new Point(right));
                }
            }
            if (potentials.size() != 0) {
                //No dead ends yet
                Point randomSelected = potentials.get(r.nextInt(potentials.size()));
                stack.push(randomSelected);
                if (randomSelected.equals(above)) {
                    dir = 'T';
                    try {
                        this.mazeCells.get(currY - 1).get(currX).swapWall(dir,true);
                        exit = curLoc;
                    } catch (IndexOutOfBoundsException impossible) {
                        //Do nothing, this is an impossible cell (meaning we are near a boundary), remove it from potential searches
                        stack.pop();
                    }
                } else if (randomSelected.equals(below)) {
                    dir = 'B';
                    try {
                        this.mazeCells.get(currY + 1).get(currX).swapWall(dir,true);
                        exit = curLoc;
                    } catch (IndexOutOfBoundsException impossible) {
                        //Do nothing, this is an impossible cell (meaning we are near a boundary), remove it from potential searches
                        stack.pop();
                    }
                } else if (randomSelected.equals(right)) {
                    dir = 'R';
                    try {
                        this.mazeCells.get(currY).get(currX + 1).swapWall(dir,true);
                        exit = curLoc;
                    } catch (IndexOutOfBoundsException impossible) {
                        //Do nothing, this is an impossible cell (meaning we are near a boundary), remove it from potential searches
                        stack.pop();
                    }
                } else if (randomSelected.equals(left)) {
                    dir = 'L';
                    try {
                        this.mazeCells.get(currY).get(currX - 1).swapWall(dir,true);
                        exit = curLoc;
                    } catch (IndexOutOfBoundsException impossible) {
                        //Do nothing, this is an impossible cell (meaning we are near a boundary), remove it from potential searches
                        stack.pop();
                    }
                }
                this.mazeCells.get(currY).get(currX).swapWall(dir,false);
            } else {
                stack.pop();//no potentials
            }
            alreadySearched.add(curLoc);
        }
        this.mazeCells.get((int) exit.getY()).get((int) exit.getX()).setFinish(true);
    }

    public String[][] getMaze() {
        this.generateMaze();
        int rowCounter = 0;
        int colCounter = 0;
        //This method interprets the arraylist of maze cell monstrosity into a nice string matrix and returns it.
        String[][] toReturn = new String[(this.height * 2) + 1][(this.width * 2) + 1];
        for (int r = 0; r < (this.height * 2); r += 2) {
            colCounter = 0;
            for (int c = 0; c < (this.width * 2); c += 2) {
                if(rowCounter >= this.height){
                    rowCounter = this.height-1;
                }
                if(colCounter >= this.width){
                    colCounter = this.width -1;
                }
                MazeCell curCell = this.mazeCells.get(rowCounter).get(colCounter);
                if (curCell.hasTopWall()) {
                    toReturn[r][c] = roof;
                    toReturn[r][c + 1] = roof;
                } else {
                    toReturn[r][c] = roof;
                    toReturn[r][c + 1] = hole;
                }
                if (curCell.hasLeftWall()) {
                    toReturn[r + 1][c] = wall;
                } else {
                    toReturn[r + 1][c] = hole;
                }
                if(r == (this.height * 2) -2) {
                    if (curCell.hasBottomWall()){
                        toReturn[r + 2][c] = roof;
                        toReturn[r + 2][c + 1] = roof;
                        toReturn[r + 2][c + 2] = wall;
                    } else {
                        toReturn[r + 2][c] = roof;
                        toReturn[r + 2][c + 1] = hole;
                        toReturn[r + 2][c + 2] = wall;
                    }
                }
                if(c == this.width * 2 - 2){
                    toReturn[r][c + 2] = wall;
                    toReturn[r + 1][c + 2] = wall;
                }
                if(c == 0){
                    toReturn[r][c] = wall;
                    toReturn[r+2][c] = wall;
                }
                toReturn[r + 1][c + 1] = path;
                if(curCell.isF()){
                    toReturn[r+1][c+1] = end;
                }
                if(curCell.isS()){
                    toReturn[r+1][c+1] = start;
                }
                colCounter++;
            }
            rowCounter++;
        }
        for(int r = 0; r < toReturn.length; r++){
            for(int c = 0; c < toReturn[0].length; c++){
                System.out.print(toReturn[r][c]);
            }
            System.out.print("\n");
        }
        return toReturn;
    }

    public Point getStart(){
        return this.startPoint;
    }

    public int getHeight() {
        return height;
    }
    public int getWidth(){
        return width;
    }
}

