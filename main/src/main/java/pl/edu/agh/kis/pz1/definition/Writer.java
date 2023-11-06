package pl.edu.agh.kis.pz1.definition;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static pl.edu.agh.kis.pz1.util.Utils.DELAY;

/**
 * Class representing a Writer in the Reader-Writer classic Multi-threading problem
 */
public class Writer extends Thread {

    private int writerId;
    private Library library;

    /**
     * Constructor for Writer class
     *
     * @param library Writer's library
     * @param id      Writer's ID
     */
    public Writer(Library library, int id) {
        this.library = library;
        this.writerId = id;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep((int) ((Math.random()) * DELAY) + DELAY + 600);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            library.write(writerId);
        }
    }


}