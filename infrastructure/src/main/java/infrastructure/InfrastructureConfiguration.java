package infrastructure;

import domain.services.SystemClock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
@ComponentScan
@EnableReactiveMongoRepositories
public class InfrastructureConfiguration {

    @Bean
    public SystemClock clock() {
        return SystemClock.Default;
    }

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean initData() {
        var factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource("data.json")});
        return factory;
    }
}
