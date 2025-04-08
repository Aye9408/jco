package com.szlanyou.jco;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sap.jco")
public class SapJcoProperties {

    private String ashost;

    private String sysnr;

    private String client;

    private String user;

    private String passwd;

    private String lang;

    private String poolCapacity;

    private String peakLimit;

    private String destinationName;
}
