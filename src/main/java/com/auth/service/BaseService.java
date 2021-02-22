package com.auth.service;
;

import java.util.List;

public interface BaseService<T> {
    T addNew(T t);
    T update(Integer id,T t);
    Boolean deleteById(Integer id);
    List<T> findAll();
    T findById(Integer id);
}
