package org.counter;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

    private final String pathToFile = "src/test/resources/ip_addresses_test";
    private final int batchSize = 1000;

    @Test
    void testCommon() throws IOException {
        File file = new File(pathToFile);
        String absolutePath = file.getAbsolutePath();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] args = {absolutePath, String.valueOf(batchSize)};
        Main.main(args);

        long expectedUniqueIp = getUniqueIpInFile();
        long expectedCountLinesInFile = 4000;
        assertEquals("Count unique ip addresses: " + expectedUniqueIp + System.lineSeparator()
                + "Count lines in file: " + expectedCountLinesInFile + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testWrongCountArguments() {
        String[] args = {String.valueOf(batchSize)};
        assertThrows(IllegalArgumentException.class, () -> Main.main(args));
    }

    long getUniqueIpInFile() throws IOException {
        File file = new File(pathToFile);
        String absolutePath = file.getAbsolutePath();
        Set<String> ips = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(absolutePath));
        String line;
        while ((line = reader.readLine()) != null) {
            ips.add(line);
        }
        return ips.size();
    }

}