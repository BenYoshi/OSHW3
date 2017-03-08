/**
 * Created by Benny on 3/5/2017.
 */
public class NT implements Runnable {
    private int id = 0;
    private static boolean choosingAdd[] = new boolean[10];
    private static int ticketAdd[] = new int[10];
    private static boolean choosingMult[] = new boolean[10];
    private static int ticketMult[] = new int[10];

    private static int counter = 0;
    private static int doubler = 1;

    public NT(int id) {
        this.id = id;
        new Thread(this).start();
    }

    @Override
    public void run() {
        increment();
        doubleDown();
    }

    private void increment() {
        choosingAdd[id] = true;
        ticketAdd[id] = id + 1;
        choosingAdd[id] = false;

        for (int i = 0; i < 10; i++) {
            if (i == id)
                continue;
            while (choosingAdd[i]);
            while (ticketAdd[i] != 0 && ticketAdd[i] < ticketAdd[id]);
            if (ticketAdd[i] == ticketAdd[id] && i < id) {
                while (ticketAdd[i] != 0);
            }
        }

        // critical section start
        counter++;
        System.out.println("Thread " + id + ": counter + 1 = " + counter);
        while (counter < 10);
        // end

        ticketAdd[id] = 0;
    }
    private void doubleDown() {
        choosingMult[id] = true;
        ticketMult[id] = id + 1;
        choosingMult[id] = false;

        for (int i = 0; i < 10; i++) {
            if (i == id)
                continue;
            while (choosingMult[i]);
            while (ticketMult[i] != 0 && ticketMult[i] < ticketMult[id]);
            if (ticketMult[i] == ticketMult[id] && i < id) {
                while (ticketMult[i] != 0);
            }
        }

        // critical section start
        doubler *= 2;
        System.out.println("Thread " + id + " x * 2 = " + doubler);
        // end

        ticketMult[id] = 0;
    }
}
