package ru.otus.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Butterfly {
    private static final int AGE_FOR_TRANSFORMING_TO_IMAGO = 10;
    private int age;
    private String name;
    private ButterflyStage butterflyStage;

    public boolean readyForTransform(){
        return age >= AGE_FOR_TRANSFORMING_TO_IMAGO;
    }
}
