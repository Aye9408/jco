package com.szlanyou.jco;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.Environment;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

@Component
public class SapUtils {

    private final SapJcoProperties props;
    private final static InMemoryDestinationDataProvider provider = new InMemoryDestinationDataProvider();
    private static boolean registered = false;

    public SapUtils(SapJcoProperties props) throws Exception {
        this.props = props;
        registerProviderOnce();
        registerDestination();
    }

    private void registerProviderOnce() throws Exception {
        if (!registered) {
            Environment.registerDestinationDataProvider(provider);
            registered = true;
        }
    }

    private void registerDestination() {
        Properties p = new Properties();
        p.setProperty(DestinationDataProvider.JCO_ASHOST, props.getAshost());
        p.setProperty(DestinationDataProvider.JCO_SYSNR, props.getSysnr());
        p.setProperty(DestinationDataProvider.JCO_CLIENT, props.getClient());
        p.setProperty(DestinationDataProvider.JCO_USER, props.getUser());
        p.setProperty(DestinationDataProvider.JCO_PASSWD, props.getPasswd());
        p.setProperty(DestinationDataProvider.JCO_LANG, props.getLang());
        p.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, props.getPoolCapacity());
        p.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, props.getPeakLimit());

        provider.addDestination(props.getDestinationName(), p);
    }

    public JCoDestination getJcoConnection() throws Exception {
        return JCoDestinationManager.getDestination(props.getDestinationName());
    }

    public JCoFunction getJCoFunction(JCoDestination jCoDestination, String functionName) throws JCoException {
        JCoFunctionTemplate functionTemplate = jCoDestination.getRepository().getFunctionTemplate(functionName);
        return functionTemplate.getFunction();
    }

    public String functionExecute(String functionName, Map<String, String> params) throws Exception {
        JCoDestination destination = getJcoConnection();
        JCoFunction function = getJCoFunction(destination, functionName);
        JCoParameterList importParameterList = function.getImportParameterList();
        for (String key : params.keySet()) {
            importParameterList.setValue(key, params.get(key));
        }
        function.execute(destination);

        return function.getExportParameterList().toString();
    }
}
