package org.counter.ipcounter.counter;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IpCounterImplTest {

    @Test
    void testCommonWork() {
        List<Long> ips = new ArrayList<>();
        ips.add(4294967291L);
        ips.add(2147483640L);
        ips.add(2147483640L);
        ips.add(2147483648L);

        int expectedUniqueCount = 3;

        IpCounter counter = new IpCounterImpl();
        counter.count(ips);

        assertEquals(expectedUniqueCount, counter.getCountUniqueIp());
    }

    @Test
    void testCountInHighSet() {
        List<Long> ips = new ArrayList<>();

        // верхняя граница верхнего сета
        ips.add(4294967295L);
        ips.add(4294967295L);
        // нижняя граница верхнего сета
        ips.add(4294967294L);
        ips.add(4294967294L);

        int expectedUniqueCount = 2;

        IpCounter counter = new IpCounterImpl();
        counter.count(ips);

        assertEquals(expectedUniqueCount, counter.getCountUniqueIp());
    }

    @Test
    void testCountInMiddleSet() {
        List<Long> ips = new ArrayList<>();

        // нижняя граница среднего сета
        ips.add(2147483647L);
        ips.add(2147483647L);

        // верхняя граница среднего сета
        ips.add(4294967293L);
        ips.add(4294967293L);

        int expectedUniqueCount = 2;

        IpCounter counter = new IpCounterImpl();
        counter.count(ips);

        assertEquals(expectedUniqueCount, counter.getCountUniqueIp());
    }

    @Test
    void testCountInLowSet() {
        List<Long> ips = new ArrayList<>();

        // нижняя нижняя граница
        ips.add(0L);
        ips.add(0L);

        // верхняя граница нижнего сета
        ips.add(2147483646L);
        ips.add(2147483646L);

        int expectedUniqueCount = 2;

        IpCounter counter = new IpCounterImpl();
        counter.count(ips);

        assertEquals(expectedUniqueCount, counter.getCountUniqueIp());
    }
}
