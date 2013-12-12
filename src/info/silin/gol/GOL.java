package info.silin.gol;

import processing.core.PApplet;
import processing.event.KeyEvent;

public class GOL extends PApplet {

    private int w = 64;
    private int h = 64;

    private Cell[][] cells = new Cell[w][h];

    private boolean running = true;
    private boolean rendering = true;

    private int factor = 10;

    @Override
    public void setup() {
        initCells();
        size(w * factor, h * factor);
        background(10);
        smooth();
    }

    @Override
    public void draw() {
        background(10);
        if (running) {
            updateCells();
        }
        if (rendering) {
            drawCells();
        }
        fill(255);
        text("fps: " + frameRate, 20, 20);
    }

    private void updateCells() {
        switchState();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
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
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Cell cell = cells[x][y];
                setFill(cell);
                rect(x * factor, y * factor, x * factor + factor, y * factor + factor);
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
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                cells[x][y].switchToNextState();
            }
        }
    }

    public int aliveNeighbors(int x, int y) {
        int aliveNeighbors = 0;

        for (int xOffset = -1; xOffset < 2; xOffset++) {
            for (int yOffset = -1; yOffset < 2; yOffset++) {
                if (!(0 == xOffset && 0 == yOffset)) {
                    int x_coord = costrainCoord(x, xOffset, w);
                    int y_coord = costrainCoord(y, yOffset, h);
                    if (State.ALIVE == cells[x_coord][y_coord].getState()) {
                        aliveNeighbors++;
                    }
                }
            }
        }
        return aliveNeighbors;
    }

    private int costrainCoord(int origin, int offset, int limit) {
        int x_coord = origin + offset;
        if (x_coord < 0) {
            x_coord = limit - x_coord;
        }
        if (x_coord > w - 1) {
            x_coord = x_coord % limit;
        }
        return x_coord;
    }

    private void initCells() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                cells[x][y] = new Cell();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        char key = event.getKey();
        if (key == 'p') {
            running = !running;
        }
        if(key == 'o'){
            rendering = !rendering;
        }
        if(key == 'r'){
            initCells();
        }
        super.keyPressed(event);
    }

    static public void main(String[] args) {
        PApplet.main(new String[]{"info.silin.gol.GOL"});
    }
}
