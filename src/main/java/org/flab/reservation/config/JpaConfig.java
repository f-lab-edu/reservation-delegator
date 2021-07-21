package org.flab.reservation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("org.flab.reservation.repository")
@EnableTransactionManagement
public class JpaConfig {

}
