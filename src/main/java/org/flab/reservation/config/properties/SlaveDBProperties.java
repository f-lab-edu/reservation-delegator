package org.flab.reservation.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "database.slave")
public class SlaveDBProperties extends DBProperties {
}
