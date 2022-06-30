package ru.otus.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.domain.Butterfly;

import java.util.Collection;

@MessagingGateway
public interface CaterpillarButterflyGateway {

    @Gateway(requestChannel = "caterpillarChannel", replyChannel = "butterflyChannel")
    Collection<Butterfly> transform(Collection<Butterfly> caterpillars);
}
