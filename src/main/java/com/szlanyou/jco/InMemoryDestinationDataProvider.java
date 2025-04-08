package com.szlanyou.jco;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InMemoryDestinationDataProvider implements DestinationDataProvider {

    private final Map<String, Properties> destinations = new HashMap<>();

    public void addDestination(String name, Properties properties) {
        destinations.put(name, properties);
    }

    @Override
    public Properties getDestinationProperties(String destinationName) {
        return destinations.get(destinationName);
    }

    @Override
    public void setDestinationDataEventListener(DestinationDataEventListener eventListener) {
        // no-op
    }

    @Override
    public boolean supportsEvents() {
        return false;
    }
}
