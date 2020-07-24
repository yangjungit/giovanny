package com.h3c.giovanny.config;

public enum RabbitDirectExchangeNameEnum {

    /**
     * ex_reply
     */
    EXCHANGE_EXREPLY("ex_reply"),
    /**
     * ex_direct
     */
    EXCHANGE_EXDIRECT("ex_direct"),
    /**
     * ex_broadcastrequest
     */
    CONTRACE_BROADCAST("ex_broadcastrequest");

    private String exchangeName;

    RabbitDirectExchangeNameEnum(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }
}
