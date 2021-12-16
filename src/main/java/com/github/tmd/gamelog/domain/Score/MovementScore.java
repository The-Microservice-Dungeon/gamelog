package com.github.tmd.gamelog.domain.Score;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MovementScore implements IncreasableInterface<Integer>, DecreasableInterface<Integer> {

    @Setter
    private int value = 0;
    private final String name = "Movement";

    @Override
    public void increase(Integer value) {
        if(value <= 0) {
            throw new RuntimeException();
        }

        this.value += value;
    }

    @Override
    public void decrease(Integer value) {
        if(value <= 0) {
            throw new RuntimeException();
        }

        this.value -= value;
    }
}
