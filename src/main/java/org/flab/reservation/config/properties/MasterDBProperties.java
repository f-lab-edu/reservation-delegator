package org.flab.reservation.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "database.master")
public class MasterDBProperties extends DBProperties {
}
