package org.counter.ipcounter;

import org.counter.ipcounter.converter.IpAddressConverter;
import org.counter.ipcounter.converter.IpAddressConverterImpl;
import org.counter.ipcounter.counter.IpCounter;
import org.counter.ipcounter.counter.IpCounterImpl;
import org.counter.ipcounter.reader.IpAddressFileReader;
import org.counter.ipcounter.reader.IpAddressReaderImpl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    private final static int NUMBER_INPUT_ARGUMENTS = 2;
    private final static int ORDER_INPUT_FILE_ARGUMENT = 0;
    private final static int ORDER_INPUT_BATCH_SIZE_ARGUMENT = 1;


    public static void main(String[] args) {
        checkInputArguments(args);

        IpCounter counter = new IpCounterImpl();

        int batchSize = Integer.parseInt(args[ORDER_INPUT_BATCH_SIZE_ARGUMENT]);

        AtomicLong countLines = new AtomicLong(0);
        int countTasks = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(countTasks);
        try (IpAddressFileReader reader = new IpAddressReaderImpl(args[ORDER_INPUT_FILE_ARGUMENT], batchSize)) {
            Runnable runnableTask = () -> {
                IpAddressConverter converter = new IpAddressConverterImpl();
                try {
                    boolean hasNext = true;
                    while (hasNext) {
                        List<String> lines = reader.readLines();
                        countLines.addAndGet(lines.size());
                        List<Long> ipLongs = converter.toLongFromString(lines);
                        counter.count(ipLongs);
                        if (lines.size() < batchSize) {
                            hasNext = false;
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Ошибка чтения файла", e);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Ошибка конвертирования строки в число", e);
                }
            };

            executorService.invokeAll(Collections.nCopies(countTasks, Executors.callable(runnableTask)));
            executorService.shutdown();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка работы программы", e);
        }

        System.out.println("Count unique ip addresses: " + counter.getCountUniqueIp());
        System.out.println("Count lines in file: " + countLines.get());
    }

    private static void checkInputArguments(String[] args) {
        if (args.length != NUMBER_INPUT_ARGUMENTS) {
            throw new IllegalArgumentException("Должно быть 2 аргумента");
        }

        File inputFile = new File(args[ORDER_INPUT_FILE_ARGUMENT]);
        if (!inputFile.exists()) {
            throw new IllegalArgumentException("Не существует файла по пути: " + args[ORDER_INPUT_FILE_ARGUMENT]);
        }

        try {
            Integer.parseInt(args[ORDER_INPUT_BATCH_SIZE_ARGUMENT]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Второй аргумент должен быть целым числом", e);
        }
    }
}
