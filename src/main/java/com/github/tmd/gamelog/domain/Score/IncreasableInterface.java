package com.github.tmd.gamelog.domain.Score;

/**
 * The Score is increasable
 * @param <T>
 */
public interface IncreasableInterface<T> {
    public void increase(T value);
}
