package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.definition.Library;
import pl.edu.agh.kis.pz1.definition.Reader;
import pl.edu.agh.kis.pz1.definition.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.edu.agh.kis.pz1.util.Utils.MAX_READER_NUMBER;
import static pl.edu.agh.kis.pz1.util.Utils.MAX_WRITER_NUMBER;

/**
 * Main class responsible for Running the Reader-Writer classic Multithreading problem code
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String... args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

        List<Writer> writers = new ArrayList<>();
        List<Reader> readers = new ArrayList<>();
        Library library = new Library();

        /* Creating instances of Readers */
        IntStream.range(0, MAX_READER_NUMBER).forEachOrdered(n -> readers.add(new Reader(library, n)));
        IntStream.range(0, MAX_READER_NUMBER).forEachOrdered(n -> logger.info("[INFO]----------------New reader with ID: " + n + " has been created!----------------"));

        /* Creating instances of Writers */
        IntStream.range(0, MAX_WRITER_NUMBER).forEachOrdered(n -> writers.add(new Writer(library, n)));
        IntStream.range(0, MAX_WRITER_NUMBER).forEachOrdered(n -> logger.info("[INFO]----------------New writer with ID: " + n + " has been created!----------------"));

        /*Running them in separate Threads*/
        readers.forEach(Thread::start);

        writers.forEach(Thread::start);
    }
}
