package pl.edu.agh.kis.pz1.definition;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static pl.edu.agh.kis.pz1.util.Utils.*;

/**
 * Class representing a library room
 * Readers and Writers may enter this room according to the rules
 * given in Readers-Writers classic multi-threading problem.
 */
public class Library {
    private static final Logger logger = Logger.getLogger(Library.class.getName());
    private boolean isWriterWorking = false;
    private int numberOfReadersInLibrary;
    private Map<Integer, Boolean> isVisited;

    /**
     * Upon creating the library the number of readers is set to zero.
     * Also map isVisited is being created.
     * Map isVisited tells if Reader had entered and left the library.
     * After writer enters the library all readers values in map are again set to false.
     * By doing this we can ensure that writers won't be starved.
     */
    public Library() {
        this.numberOfReadersInLibrary = 0;
        isVisited = new HashMap<>();
        IntStream.range(0, MAX_READER_NUMBER).forEachOrdered(n -> isVisited.put(n, false));

    }

    /**
     * Main reading method which is responsible for all Reader actions in the Library and in queue.
     *
     * @param readerId represents a Reader who enters the library
     */
    public void read(int readerId) {
        synchronized (this) {

            while (canReaderJoin(readerId)) {

                try {
                    logger.info(() -> "[INFO - READER WAITING]----------------Reader " + readerId + " is waiting to join the Library----------------");
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            newReader(readerId);

        }

        try {
            Thread.sleep((int) ((Math.random()) * DELAY) + 2000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        }

        endOfReading(readerId);

    }

    /**
     * Main reading method which is responsible for all Writer actions in the Library and in queue.
     *
     * @param writerId represents a Writer who enters the library
     */
    public synchronized void write(int writerId) {
        while (this.numberOfReadersInLibrary != 0 || isWriterWorking) {
            try {
                logger.info(() -> "[INFO - WRITER WAITING]----------------Writer " + writerId + " is waiting to join the Library----------------");
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
        isWriterWorking = true;
        logger.info(() -> "[INFO - WRITER]----------------Writer with ID " + writerId + " begins writing.----------------");

        try {
            Thread.sleep((int) ((Math.random()) * DELAY) + 2500L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        }
        endOfWriting(writerId);
    }

    /**
     * Method runs everytime the Reader enters the library room
     *
     * @param readerId represents a Reader
     */
    private synchronized void newReader(int readerId) {
        isVisited.replace(readerId, true);
        this.numberOfReadersInLibrary++;
        logger.info(() -> "[INFO - READER]----------------Reader with ID " + readerId + " begins reading, current number of READERS: " + numberOfReadersInLibrary + "----------------");
    }

    /**
     * Method runs everytime the Reader leaves the library room
     *
     * @param readerId represents a Reader
     */
    private synchronized void endOfReading(int readerId) {
        this.numberOfReadersInLibrary--;
        logger.info(() -> "[INFO - READER]----------------Reader with ID " + readerId + " ends reading, current number of READERS: " + numberOfReadersInLibrary + "----------------");
        if (this.numberOfReadersInLibrary == 0) {
            this.notifyAll();
        }
    }

    /**
     * Method runs everytime the Writer leaves the library room
     *
     * @param writerId represents a Reader
     */
    private synchronized void endOfWriting(int writerId) {
        isVisited.clear();
        IntStream.range(0, MAX_READER_NUMBER).forEachOrdered(n -> isVisited.put(n, false));
        logger.info(() -> "[INFO - WRITER]----------------Writer with ID " + writerId + " ends writing.----------------");
        isWriterWorking = false;
        this.notifyAll();
    }

    /**
     * Method tells us if the Reader can join the library
     *
     * @param readerId represents a Reader
     */
    private synchronized boolean canReaderJoin(int readerId) {
        return this.numberOfReadersInLibrary >= MAX_LIBRARY_READERS || isVisited.get(readerId) || isWriterWorking;
    }


    //region getters setters
    public boolean isWriterWorking() {
        return isWriterWorking;
    }

    public Map<Integer, Boolean> getIsVisited() {
        return isVisited;
    }
    //endregion
}
