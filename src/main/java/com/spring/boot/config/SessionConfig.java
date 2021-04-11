package com.spring.boot.config;

import io.lettuce.core.ReadFrom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

@Configuration
public class SessionConfig implements EnvironmentPostProcessor {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {


        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(
            "172.25.209.3",
            6380
        );

        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    @Bean
    public ConfigureRedisAction configureRedisAction() {
        System.out.println("ELASTIC_CACHE ENABLED");
        return ConfigureRedisAction.NO_OP;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("call");
    }
}
