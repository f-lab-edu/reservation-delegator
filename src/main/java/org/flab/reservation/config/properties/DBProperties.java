package org.flab.reservation.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class DBProperties {
    
    private String url;
    private String username;
    private String password;
    private int connectionTimeout;
    private int maxConnection;
    private int minConnection;
    private int idleTimeout;
    private boolean readOnly;
}
