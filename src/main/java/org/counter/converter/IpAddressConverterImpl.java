package org.counter.converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Конвертер ip адреса из строкового представления в десятичное
 */
public class IpAddressConverterImpl implements IpAddressConverter {

    @Override
    public List<Long> toLongFromString(List<String> ipAddresses) {
        return ipAddresses.stream().map(ipAddress -> {
            String[] parts = ipAddress.split("\\.");
            return Long.parseLong(parts[0]) << 24 |
                    Long.parseLong(parts[1]) << 16 |
                    Long.parseLong(parts[2]) << 8 |
                    Long.parseLong(parts[3]);
        }).collect(Collectors.toList());
    }
}
