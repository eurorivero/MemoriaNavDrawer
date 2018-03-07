package com.example.eurorivero.memoria.Ranking;

import java.util.List;

/**
 * Created by euror on 13/02/2018.
 */

public interface DAOinterface<T> {
    long save(T type);
    void update(T type);
    void delete(T type);
    T get(long id);
    List<T> getAll();
}
