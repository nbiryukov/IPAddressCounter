package org.counter.ipcounter.reader;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IpAddressReaderImplTest {

    private final String pathToFile = "src/test/resources/ip_addresses_test";
    private final int batchSize = 1000;

    @Test
    void readLinesExpectedCountLines() {
        File file = new File(pathToFile);
        String absolutePath = file.getAbsolutePath();

        int expectedCountLines = 4000;
        try (IpAddressFileReader reader = new IpAddressReaderImpl(absolutePath, batchSize)) {
            boolean hasNext = true;
            int countLines = 0;
            while (hasNext) {
                List<String> lines = reader.readLines();
                countLines += lines.size();
                if (lines.size() < batchSize) {
                    hasNext = false;
                }
            }

            assertEquals(expectedCountLines, countLines);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void readLinesIllegalBatchSize() {
        File file = new File(pathToFile);
        String absolutePath = file.getAbsolutePath();

        assertThrows(IllegalArgumentException.class, () -> {
            new IpAddressReaderImpl(absolutePath, -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new IpAddressReaderImpl(absolutePath, 10001);
        });
    }

    @Test
    void readLinesIllegalFileName() {

        assertThrows(IllegalArgumentException.class, () -> {
            new IpAddressReaderImpl(null, batchSize);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new IpAddressReaderImpl("", batchSize);
        });
    }
}
