package info.silin.gol;

import processing.core.PApplet;

public class GOL extends PApplet {

    private Cell[][] cells;

    @Override
    public void setup() {
        cells = new Cell[100][100];

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                cells[x][y] = new Cell();
            }
        }

        size(400, 400);
        background(10);
        smooth();
    }


    @Override
    public void draw() {

        updateCells();
        drawCells();
        System.out.println("fps:" + frameRate);

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                cells[x][y].switchToNextState();
            }
        }
    }

    private void updateCells() {
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                Cell cell = cells[x][y];
//                System.out.println("x: " + x + " y: " + y + "cell: " + cell);
                int aliveNeighbors = aliveNeighbors(x, y);
                if (State.ALIVE == cell.getState()) {
//                    System.out.println("x: " + x + " y: " + y + "alive neighbors: " + aliveNeighbors);
                    if (aliveNeighbors < 2) {
                    cell.setNextState(State.DEAD);
                    } else if (aliveNeighbors > 3) {
                    cell.setNextState(State.DEAD);
                    }
                }
                if (State.DEAD == cell.getState()) {
//                    if (aliveNeighbors == 2) {
//                        cell.setNextState(State.ALIVE);
//                    }
                    if (aliveNeighbors == 3) {
                        cell.setNextState(State.ALIVE);
                    }
                }
            }
        }
    }

    private void drawCells() {
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                Cell cell = cells[x][y];

                boolean alive = State.ALIVE == cell.getState();
                    stroke(1.0f, 0.0f, 0.0f);
                if (alive) {
                    fill(200);
                } else {
                    fill(100);
                }
                rect(x * 4, y * 4, x * 4 + 4, y * 4 + 4);
            }
        }
    }

    public int aliveNeighbors(int x, int y) {
        int aliveNeighbors = 0;

        if (x > 0 && y > 0 && x < 98 && y < 98 ) {
            // TODO wrap coords
            for (int xOffset = -1; xOffset < 2; xOffset++) {
                for (int yOffset = -1; yOffset < 2; yOffset++) {
                    if (!(0 == xOffset && 0 == yOffset)) {
                        if (State.ALIVE == cells[x + xOffset][y + yOffset].getState()) {
                            aliveNeighbors++;
                        }
                    }
                }
            }
        }

        return aliveNeighbors;
    }

    static public void main(String[] args) {
        PApplet.main(new String[]{"info.silin.gol.GOL"});
    }
}
