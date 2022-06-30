package ru.otus.integration.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.otus.integration.gateway.CaterpillarButterflyGateway;
import ru.otus.integration.service.CaterpillarGenerator;

import java.util.concurrent.ForkJoinPool;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunner {
    private final CaterpillarButterflyGateway caterpillarButterflyGateway;
    private final CaterpillarGenerator caterpillarGenerator;

    public void run() {
        var pool = ForkJoinPool.commonPool();

        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            pool.execute(() -> {
                var caterpillars = caterpillarGenerator.generateCaterpillars();
                var butterflies = caterpillarButterflyGateway.transform(caterpillars);
                log.info(butterflies + " transformed");
            });
        }
    }
}
