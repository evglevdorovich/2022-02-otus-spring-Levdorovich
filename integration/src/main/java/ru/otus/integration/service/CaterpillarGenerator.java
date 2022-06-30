package ru.otus.integration.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Butterfly;
import ru.otus.integration.domain.ButterflyStage;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaterpillarGenerator {
    public static final String[] CATERPILLAR_NAMES = {"Olivia", "Emma", "Charlotte", "Amelia", "Ava",
            "Sophia", "Isabella", "Mia"};
    private static final int MAX_AGE = 17;
    public List<Butterfly> generateCaterpillars() {
        var caterpillars = new ArrayList<Butterfly>();
        for (int i = 0; i < 10; i++) {
            var age = RandomUtils.nextInt(0, MAX_AGE);
            var nameIndex = RandomUtils.nextInt(0, CATERPILLAR_NAMES.length);
            caterpillars.add(new Butterfly(age, CATERPILLAR_NAMES[nameIndex], ButterflyStage.CATERPILLAR));
        }

        return caterpillars;
    }

}
