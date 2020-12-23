package ru.tg.farm.common.configuration;


import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import static java.lang.String.format;

@Configuration
@AutoConfigureBefore
public class PersistenceConfiguration {

    @Value("${db.name}")
    private String databaseName;

    @Value("${db.username}")
    private String databaseUsername;

    @Value("${db.password}")
    private String databasePassword;

    @Value("${cloud.postgres.instance}")
    private String postgresInstance;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Bean
    @Primary
    public DataSource dataSource() {
        var postgresInstance = getPostgresInstance();

        return DataSourceBuilder
                .create()
                .username(databaseUsername)
                .password(databasePassword)
                .url(format("jdbc:postgresql://%s:%s/%s", postgresInstance.getHost(), postgresInstance.getPort(), databaseName))
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    private ServiceInstance getPostgresInstance() {
        return discoveryClient.getInstances(postgresInstance)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unable to discover a Postgres instance"));
    }
}
