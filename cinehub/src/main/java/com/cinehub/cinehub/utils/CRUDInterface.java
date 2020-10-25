package com.cinehub.cinehub.utils;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public interface CRUDInterface<T, S> {
    List<T> selectAll();
    Optional<T> selectByKey(S key) throws ItemNotFoundException;
    void insertItem(T toInsert) throws NotAuthorizatedException;
    void updateItem(T toUpdate) throws ItemNotFoundException, NotAuthorizatedException;
    T deleteByKey(S key) throws ItemNotFoundException, NotAuthorizatedException;
}
