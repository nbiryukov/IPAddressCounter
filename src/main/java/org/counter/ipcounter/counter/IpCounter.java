package org.counter.ipcounter.counter;

import java.util.List;

public interface IpCounter {

    void count(List<Long> ipAddresses);

    long getCountUniqueIp();
}
