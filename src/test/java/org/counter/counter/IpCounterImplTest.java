package org.counter.counter;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IpCounterImplTest {

    @Test
    void testCount() {
        List<Long> ips = prepare();
        int expectedUniqueCount = 9;

        IpCounter counter = new IpCounterImpl();
        counter.count(ips);

        assertEquals(expectedUniqueCount, counter.getCountUniqueIp());
    }

    List<Long> prepare() {
        List<Long> ips = new ArrayList<>();

        // общая верхняя граница
        ips.add(4294967295L);
        ips.add(4294967295L);

        // общая нижняя граница
        ips.add(0L);
        ips.add(0L);

        // верхняя граница нижнего сета
        ips.add(2147483646L);
        ips.add(2147483646L);

        // нижняя граница среднего сета
        ips.add(2147483647L);
        ips.add(2147483647L);

        // верхняя граница среднего сета
        ips.add(4294967293L);
        ips.add(4294967293L);

        // нижняя граница верхнего сета
        ips.add(4294967294L);
        ips.add(4294967294L);

        // общие адреса
        ips.add(4294967291L);
        ips.add(2147483640L);
        ips.add(2147483640L);
        ips.add(2147483648L);

        return ips;
    }

}