package org.counter.ipcounter.converter;

import java.util.List;

public interface IpAddressConverter {

    List<Long> toLongFromString(List<String> ipAddress);
}
