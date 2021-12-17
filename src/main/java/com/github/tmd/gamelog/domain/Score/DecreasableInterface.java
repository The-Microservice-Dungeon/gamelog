package com.github.tmd.gamelog.domain.Score;

/**
 * The Score is decreasable
 * @param <T>
 */
public interface DecreasableInterface<T> {
    public void decrease(T value);
}
