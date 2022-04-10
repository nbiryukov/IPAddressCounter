package org.counter.counter;

import java.util.List;

public interface IpCounter {

    void count(List<Long> ipAddresses);

    long getCountUniqueIp();
}
