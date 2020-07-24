package com.h3c.giovanny.domain.mongodb;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class IniInfo {

    private Map<String, Long> temperature;

    private Map<String, Long> powerInfo;

    public IniInfo() {
        this.temperature = new HashMap<>(1);
        temperature.put("temperature", 100L);
        this.powerInfo = new HashMap<>(1);
        powerInfo.put("powerInfo", 90L);
    }
}
