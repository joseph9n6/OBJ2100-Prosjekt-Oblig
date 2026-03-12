package elevator.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class ElevatorModel {
    private final int minFloor;
    private final int maxFloor;

    private int currentFloor;
    private ElevatorState state = ElevatorState.IDLE;

    private final Deque<Integer> queue = new ArrayDeque<>();

    public ElevatorModel(int minFloor, int maxFloor, int startFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.currentFloor = startFloor;
    }

    // Enkel default (endre maxFloor til deres antall etasjer)
    public ElevatorModel() {
        this(1, 4, 1);
    }

    public synchronized void requestFloor(int floor) {
        // range-sjekk
        if (floor < minFloor || floor > maxFloor) return;

        // Hvis request er samme etasje:
        // - hvis vi ikke beveger oss, åpne dør
        if (floor == currentFloor) {
            if (!isMoving()) state = ElevatorState.DOOR_OPEN;
            return;
        }

        // Duplikatkontroll
        if (!queue.contains(floor)) {
            queue.addLast(floor);
        }
    }

    public synchronized ElevatorSnapshot snapshot() {
        return new ElevatorSnapshot(currentFloor, state, queue.peekFirst());
    }

    // Kalles “hver tick”
    public synchronized void step() {
        Integer target = queue.peekFirst();

        // Ingen target
        if (target == null) {
            // Ikke overstyr DOOR_OPEN til IDLE
            if (state != ElevatorState.DOOR_OPEN) {
                state = ElevatorState.IDLE;
            }
            return;
        }

        // Regel: ikke beveg hvis dør er åpen
        if (state == ElevatorState.DOOR_OPEN) return;

        // Hvis vi er fremme -> åpne dør og fjern request
        if (target == currentFloor) {
            queue.removeFirst();
            state = ElevatorState.DOOR_OPEN;
            return;
        }

        // Flytt én etasje mot target
        if (target > currentFloor) {
            state = ElevatorState.MOVING_UP;
            currentFloor++;
        } else {
            state = ElevatorState.MOVING_DOWN;
            currentFloor--;
        }

        // Etter flytt: dør lukket
        state = ElevatorState.DOOR_CLOSED;
    }

    public synchronized void closeDoor() {
        if (state == ElevatorState.DOOR_OPEN) state = ElevatorState.DOOR_CLOSED;
    }

    public synchronized boolean isMoving() {
        return state == ElevatorState.MOVING_UP || state == ElevatorState.MOVING_DOWN;
    }

    public synchronized int getCurrentFloor() {
        return currentFloor;
    }

    public synchronized ElevatorState getState() {
        return state;
    }
}