package org.counter;

import org.counter.converter.IpAddressConverter;
import org.counter.converter.IpAddressConverterImpl;
import org.counter.counter.IpCounter;
import org.counter.counter.IpCounterImpl;
import org.counter.reader.IpAddressFileReader;
import org.counter.reader.IpAddressReaderImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        checkInputArguments(args);

        IpCounter counter = new IpCounterImpl();
        IpAddressConverter converter = new IpAddressConverterImpl();

        int batchSize = Integer.parseInt(args[1]);
        long countLines = 0;
        try (IpAddressFileReader reader = new IpAddressReaderImpl(args[0], batchSize)) {
            boolean hasNext = true;
            while (hasNext) {
                List<String> lines = reader.readLines();
                countLines += lines.size();
                List<Long> ipLongs = converter.toLongFromString(lines);
                counter.count(ipLongs);
                if (lines.size() < batchSize) {
                    hasNext = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Count unique ip addresses: " + counter.getCountUniqueIp());
        System.out.println("Count lines in file: " + countLines);
    }

    private static void checkInputArguments(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Arguments should be 2");
        }
    }
}
