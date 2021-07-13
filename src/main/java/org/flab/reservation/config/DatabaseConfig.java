package org.flab.reservation.config;

import com.zaxxer.hikari.HikariDataSource;
import org.flab.reservation.config.properties.DBProperties;
import org.flab.reservation.config.properties.MasterDBProperties;
import org.flab.reservation.config.properties.SlaveDBProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({MasterDBProperties.class, SlaveDBProperties.class})
public class DatabaseConfig {
    
    @Bean
    public DataSource routingDataSource(MasterDBProperties masterDBProperties, SlaveDBProperties slaveDBProperties) {
        Map<Object, Object> targets = new HashMap<>();
        targets.put(DBType.MASTER, createDataSource(masterDBProperties));
        targets.put(DBType.SLAVE, createDataSource(slaveDBProperties));
        
        RoutingDataSource dataSource = new RoutingDataSource();
        dataSource.setTargetDataSources(targets);
        dataSource.setDefaultTargetDataSource(targets.get(DBType.MASTER));
        
        return dataSource;
    }
    
    public static DataSource createDataSource(DBProperties properties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setConnectionTimeout(properties.getConnectionTimeout());
        dataSource.setMaximumPoolSize(properties.getMaxConnection());
        dataSource.setMinimumIdle(properties.getMinConnection());
        dataSource.setIdleTimeout(properties.getIdleTimeout());
        dataSource.setReadOnly(properties.isReadOnly());
        
        return dataSource;
    }
    
    enum DBType {
        MASTER, SLAVE
    }
    
    private static class RoutingDataSource extends AbstractRoutingDataSource {
        
        @Override
        protected Object determineCurrentLookupKey() {
            if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
                return DBType.SLAVE;
            }
            
            return DBType.MASTER;
        }
    }
}
