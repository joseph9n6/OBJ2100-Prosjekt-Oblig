// FIKS KANSKJE FEIL.
package elevator.model;

public class ModelTestMain {
    public static void main(String[] args) throws InterruptedException {
        ElevatorModel model = new ElevatorModel(1, 4, 1);

        System.out.println("Start: " + model.snapshot());

        // Legg inn noen requests
        model.requestFloor(3);
        model.requestFloor(4);
        model.requestFloor(2);

        // Duplikat (skal ignoreres)
        model.requestFloor(3);

        // Simuler ticks
        for (int i = 0; i < 20; i++) {
            model.step();
            System.out.println("Tick " + i + ": " + model.snapshot());

            // Hvis døra åpner seg, vent litt og lukk
            if (model.isDoorOpen()) {
                System.out.println("  Door open -> waiting -> closing door");
                Thread.sleep(300);
                model.closeDoor();
            }

            Thread.sleep(200);
        }

        System.out.println("Done: " + model.snapshot());
    }
}