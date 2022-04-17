package org.counter.ipcounter.converter;

import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IpAddressConverterImplTest {

    @Test
    void testConvert() throws UnknownHostException {
        IpAddressConverter converter = new IpAddressConverterImpl();

        assertEquals(List.of(2_705_829_362L), converter.toLongFromString(List.of("161.71.173.242")));
        assertEquals(List.of(4_294_967_295L), converter.toLongFromString(List.of("255.255.255.255")));
        assertEquals(List.of(16_843_009L), converter.toLongFromString(List.of("1.1.1.1")));
    }
}
