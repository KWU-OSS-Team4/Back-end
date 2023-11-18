package com.witheat.WithEatServer.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
// 이건 안들어가나
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@Getter
@Setter
public class RedisProperties {
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.host}")
    private String host;

    public int getPort() { return this.port; }
    public String getHost() { return this.host; }
}