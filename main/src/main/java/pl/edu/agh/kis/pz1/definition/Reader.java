package pl.edu.agh.kis.pz1.definition;

import static pl.edu.agh.kis.pz1.util.Utils.DELAY;

/**
 * Class representing a Reader in the Reader-Writer classic Multi-threading problem
 */
public class Reader extends Thread {

    private int readerId;
    private Library library;

    /**
     * Constructor for Reader class
     *
     * @param library Reader's library
     * @param id      Reader's ID
     */
    public Reader(Library library, int id) {
        this.library = library;
        this.readerId = id;
    }

    @Override
    public void run() {

        for (; ; ) {
            try {
                Thread.sleep((int) ((Math.random()) * DELAY) + DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            }
            library.read(readerId);
        }


    }

    //region getters setters
    public int getReaderId() {
        return readerId;
    }
    //endregion
}