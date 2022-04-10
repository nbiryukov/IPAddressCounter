package org.counter.converter;

import java.net.UnknownHostException;
import java.util.List;

public interface IpAddressConverter {

    List<Long> toLongFromString(List<String> ipAddress) throws UnknownHostException;
}
