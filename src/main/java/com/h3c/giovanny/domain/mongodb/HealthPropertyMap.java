package com.h3c.giovanny.domain.mongodb;

import lombok.Data;

import java.time.Clock;

@Data
public class HealthPropertyMap {

    private long value = 100;

    private Long updateTime = Clock.systemDefaultZone().millis();

}
