package org.counter.reader;

import java.io.IOException;
import java.util.List;

public interface IpAddressFileReader extends AutoCloseable {

    List<String> readLines() throws IOException;
}
