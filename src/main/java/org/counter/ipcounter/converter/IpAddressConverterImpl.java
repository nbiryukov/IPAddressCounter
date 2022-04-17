package org.counter.ipcounter.converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Конвертер ip адреса из строкового представления в десятичное
 */
public class IpAddressConverterImpl implements IpAddressConverter {

    // разделитель ip адреса на его составные части
    private final String IP_ADDRESS_SEPARATOR = "\\.";

    @Override
    public List<Long> toLongFromString(List<String> ipAddresses) {
        return ipAddresses.stream().map(ipAddress -> {
            String[] parts = ipAddress.split(IP_ADDRESS_SEPARATOR);
            return Long.parseLong(parts[0]) << 24 |
                    Long.parseLong(parts[1]) << 16 |
                    Long.parseLong(parts[2]) << 8 |
                    Long.parseLong(parts[3]);
        }).collect(Collectors.toList());
    }
}
