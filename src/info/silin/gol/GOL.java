package info.silin.gol;

import processing.core.PApplet;

public class GOL extends PApplet {

    private Cell[][] cells = new Cell[100][100];

    @Override
    public void setup() {

        initCells();
        size(400, 400);
        background(10);
        smooth();
    }

    @Override
    public void draw() {

        updateCells();
        drawCells();
        switchState();

        System.out.println("fps:" + frameRate);
    }

    private void updateCells() {
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
//                System.out.println("x: " + x + " y: " + y + "cell: " + cell);
                applyRules(x, y);
            }
        }
    }

    private void applyRules(int x, int y) {

        Cell cell = cells[x][y];
        int aliveNeighbors = aliveNeighbors(x, y);

        if (State.ALIVE == cell.getState()) {

            if (aliveNeighbors < 2) {
            cell.setNextState(State.DEAD);
            } else if (aliveNeighbors > 3) {
            cell.setNextState(State.DEAD);
            }
        }
        if (State.DEAD == cell.getState()) {

            if (aliveNeighbors == 3) {
                cell.setNextState(State.ALIVE);
            }
        }
    }

    private void drawCells() {
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                Cell cell = cells[x][y];
                setFill(cell);
                rect(x * 4, y * 4, x * 4 + 4, y * 4 + 4);
            }
        }
    }

    private void setFill(Cell cell) {
        boolean alive = State.ALIVE == cell.getState();
        if (alive) {
            fill(204, 120, 50);
        } else {
            fill(50, 70, 90);
        }
    }

    private void switchState() {
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                cells[x][y].switchToNextState();
            }
        }
    }

    public int aliveNeighbors(int x, int y) {
        int aliveNeighbors = 0;

            for (int xOffset = -1; xOffset < 2; xOffset++) {
                for (int yOffset = -1; yOffset < 2; yOffset++) {
                    if (!(0 == xOffset && 0 == yOffset)) {

                        int x_coord = x + xOffset;
                        if(x_coord < 0){
                            x_coord = 100 - x_coord;
                        }
                        if(x_coord > 100 - 1){
                            x_coord = x_coord/100;
                        }
                        int y_coord = y + yOffset;
                        if(y_coord < 0){
                            y_coord = 100 - x_coord;
                        }
                        if(y_coord > 100 - 1){
                            y_coord = y_coord % 100;
                        }
                        if (State.ALIVE == cells[x_coord][y_coord].getState()) {
                            aliveNeighbors++;
                        }
                    }
                }
        }
        return aliveNeighbors;
    }

    private void initCells() {
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                cells[x][y] = new Cell();
            }
        }
    }

    static public void main(String[] args) {
        PApplet.main(new String[]{"info.silin.gol.GOL"});
    }
}
