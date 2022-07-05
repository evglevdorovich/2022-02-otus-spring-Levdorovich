package ru.otus.integration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Butterfly;
import ru.otus.integration.domain.ButterflyStage;

@Service
@Slf4j
public class MetamorphosisService {
    public Butterfly transform(Butterfly caterpillar) {
        log.info(caterpillar + " transforming");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Butterfly(caterpillar.getAge(), caterpillar.getName(), ButterflyStage.IMAGO);
    }

    public Butterfly increaseAge(Butterfly caterpillar) {
        var increasedAge = caterpillar.getAge() + 1;
        log.info("increasing age on: " + caterpillar);
        return new Butterfly(increasedAge, caterpillar.getName(), caterpillar.getButterflyStage());
    }
}
