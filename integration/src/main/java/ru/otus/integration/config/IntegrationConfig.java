package ru.otus.integration.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.integration.domain.Butterfly;
import ru.otus.integration.service.MetamorphosisService;

@IntegrationComponentScan
@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class IntegrationConfig {

    private final MetamorphosisService metamorphosisService;

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public QueueChannel caterpillarChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel butterflyChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow caterpillarButterflyFlow() {
        return IntegrationFlows.from(caterpillarChannel())
                .split()
                .log()
                .<Butterfly, Boolean>route(Butterfly::readyForTransform, m ->
                        m.subFlowMapping(true, sf ->
                                    sf.handle(Butterfly.class, (p, h) -> metamorphosisService.transform(p)))
                        .subFlowMapping(false, sf ->
                                    sf.handle(Butterfly.class, (p, h) -> metamorphosisService.increaseAge(p))))
                .log()
                .aggregate()
                .get();
    }

}
