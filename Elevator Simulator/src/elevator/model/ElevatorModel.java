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

        // Ingen target i kø -> heisen er idle, men ikke hvis døra står åpen
        if (target == null) {
            if (state != ElevatorState.DOOR_OPEN) {
                state = ElevatorState.IDLE;
            }
            return;
        }

        // Heisen kan ikke bevege seg når døra er åpen
        if (state == ElevatorState.DOOR_OPEN) {
            return;
        }

        // Hvis heisen allerede er framme, åpne døra og fjern request
        if (target == currentFloor) {
            queue.removeFirst();
            state = ElevatorState.DOOR_OPEN;
            return;
        }

        // Flytt én etasje mot målet
        if (target > currentFloor) {
            state = ElevatorState.MOVING_UP;
            currentFloor++;
        } else {
            state = ElevatorState.MOVING_DOWN;
            currentFloor--;
        }
    }

    public synchronized void closeDoor() {
        if (state == ElevatorState.DOOR_OPEN) {
            state = queue.isEmpty() ? ElevatorState.IDLE : ElevatorState.DOOR_CLOSED;
        }
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

    public synchronized boolean isDoorOpen() {
        return state == ElevatorState.DOOR_OPEN;
    }
}