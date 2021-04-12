package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration("rootConfig")
@ComponentScan({"service", "controller", "utils"})
@EnableJpaRepositories("repository")
@EnableTransactionManagement
public class RootConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
