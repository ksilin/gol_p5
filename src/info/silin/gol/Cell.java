package info.silin.gol;

public class Cell {

    private State state;
    private State nextState;

    public Cell() {
        state = Math.random() < 0.5 ? State.ALIVE : State.DEAD;
        nextState = state;
    }

    public void switchToNextState() {
        state = nextState;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getNextState() {
        return nextState;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }
}
