package org.counter.counter;

import java.util.BitSet;
import java.util.List;

/**
 * Счетчик ip адресов основанный на BitSet(массив битов)
 */
public class IpCounterImpl implements IpCounter {

    private long countUniqueIp = 0;

    // 4294967293-4294967294 - покрываемые индексы
    private final BitSet heightSet;
    // 2147483647-4294967292 - покрываемые индексы
    private final BitSet middleSet;
    // 0-2147483646 - покрываемые индексы
    private final BitSet lowSet;

    public IpCounterImpl() {
        heightSet = new BitSet(2);
        middleSet = new BitSet(Integer.MAX_VALUE);
        lowSet = new BitSet(Integer.MAX_VALUE);
    }

    private final static long START_VALUE_IN_MIDDLE_SET = 2147483647L;
    private final static long END_VALUE_IN_MIDDLE_SET = 4294967293L;

    /**
     * Добавить адреса к счетчику
     *
     * @param ipAddresses список ip адресов в десятичной форме
     */
    @Override
    public void count(List<Long> ipAddresses) {
        ipAddresses.stream().forEach((ip) -> {
            int intIp;

            var currentSet = lowSet;
            if (ip > END_VALUE_IN_MIDDLE_SET) {
                currentSet = heightSet;
                intIp = (int) (ip - (long) Integer.MAX_VALUE - (long) Integer.MAX_VALUE + 1);
            } else if (ip >= START_VALUE_IN_MIDDLE_SET) {
                currentSet = middleSet;
                intIp = (int) (ip - (long) Integer.MAX_VALUE);
            } else {
                long tempLong = ip;
                intIp = (int) tempLong;
            }

            if (!currentSet.get(intIp)) {
                countUniqueIp++;
                currentSet.set(intIp);
            }
        });

    }

    @Override
    public long getCountUniqueIp() {
        return countUniqueIp;
    }
}
