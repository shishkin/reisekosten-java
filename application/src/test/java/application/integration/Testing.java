package application.integration;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class Testing {
    @Autowired
    Pipeline pipeline;

    @Autowired
    ReactiveMongoTemplate db;

    public <Response> Response sendAsync(Command<Response> request) {
        return pipeline.send(request);
    }

    public <T> Mono<Void> addAsync(T obj) {
        return db.save(obj).then();
    }

    public <T> Mono<T> loadAsync(int id, Class<T> tClass) {
        return db.findById(id, tClass);
    }
}
