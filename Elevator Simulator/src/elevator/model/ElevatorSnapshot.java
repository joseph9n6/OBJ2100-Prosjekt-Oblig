package elevator.model;

/**
 * Immutable snapshot of elevator state for UI / logging / tests.
 */
public class ElevatorSnapshot {
    private final int currentFloor;
    private final ElevatorState state;
    private final Integer nextTarget; // kan være null

    public ElevatorSnapshot(int currentFloor, ElevatorState state, Integer nextTarget) {
        this.currentFloor = currentFloor;
        this.state = state;
        this.nextTarget = nextTarget;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorState getState() {
        return state;
    }

    public Integer getNextTarget() {
        return nextTarget;
    }

    @Override
    public String toString() {
        return "ElevatorSnapshot{" +
                "floor=" + currentFloor +
                ", state=" + state +
                ", nextTarget=" + nextTarget +
                '}';
    }
}