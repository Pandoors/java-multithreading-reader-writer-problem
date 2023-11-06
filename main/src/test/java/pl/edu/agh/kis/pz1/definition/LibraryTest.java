package pl.edu.agh.kis.pz1.definition;

import junit.framework.TestCase;

import java.util.concurrent.TimeUnit;

public class LibraryTest extends TestCase {
    /**
     * Test to see if reader has joined
     */
    public void testRead() {
        Library library = new Library();
        Reader testReader = new Reader(library, 0);
        library.read(0);
        assertFalse(library.isWriterWorking());

        assertTrue(library.getIsVisited().get(testReader.getReaderId()));
    }

    /**
     * Test to see if writer, while finishing working, sets the isWriterWorking flag to false
     */
    public void testWrite() {
        Library library = new Library();
        Writer testWriter = new Writer(library, 0);
        library.write(0);

        assertFalse(library.isWriterWorking());
    }

    /**
     * Test to see if users are added to map
     */
    public void testAreUsersCreated() {
        Library library = new Library();
        Reader testReader = new Reader(library, 0);
        Writer testWriter = new Writer(library, 1);
        Reader testReaderNext = new Reader(library, 2);
        testReaderNext.start();
        testReader.start();
        testWriter.start();

        assertNotNull(library.getIsVisited().get(testReader.getReaderId()));

    }


}