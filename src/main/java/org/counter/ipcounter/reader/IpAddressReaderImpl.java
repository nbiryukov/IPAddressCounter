package org.counter.ipcounter.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация интерфейса чтения ip адресов партиями из файла
 */
public class IpAddressReaderImpl implements IpAddressFileReader {

    private final int batchSize;
    private final BufferedReader reader;

    private final int MIN_BATCH_SIZE = 100;
    private final int MAX_BATCH_SIZE = 10000;

    /**
     * Конструктор IpAddressReaderImpl
     *
     * @param fileName  наименование файла вместе с путем
     * @param batchSize размер партии строк для чтения
     * @throws IOException
     */
    public IpAddressReaderImpl(String fileName, int batchSize) throws IOException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Empty fileName");
        }

        if (batchSize < MIN_BATCH_SIZE || batchSize > MAX_BATCH_SIZE) {
            throw new IllegalArgumentException("Not valid batch size, valid size between: " + MIN_BATCH_SIZE
                    + " and " + MAX_BATCH_SIZE);
        }

        this.batchSize = batchSize;
        this.reader = new BufferedReader(new FileReader(fileName));
    }

    /**
     * Чтение строк из файла
     * @return список строк
     * @throws IOException
     */
    @Override
    public synchronized List<String> readLines() throws IOException {
        List<String> lines = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            } else {
                return lines;
            }
        }
        return lines;
    }

    /**
     * Закрыть файл
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        this.reader.close();
    }
}
