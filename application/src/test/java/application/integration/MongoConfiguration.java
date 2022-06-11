package application.integration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

@Configuration
public class MongoConfiguration extends AbstractReactiveMongoConfiguration {
    final MongoDBContainer mongo =
            new MongoDBContainer(DockerImageName.parse("mongo:5.0.9"))
                    .withTmpFs(Map.of("/data/db", "rw"));

    public MongoConfiguration() {
    }

    @Override
    public @NotNull MongoClient reactiveMongoClient() {
        mongo.start();
        return MongoClients.create(mongo.getConnectionString());
    }

    @Override
    protected @NotNull String getDatabaseName() {
        return "test";
    }
}
