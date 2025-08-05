package com.hezix.shaudifymain.util.mapper;

import java.util.List;
import java.util.Set;

public interface Mappable<T,F> {
    T toEntity(F f);
    List<T> toEntityList(List<F> fList);
    F toDto(T t);
    List<F> toDtoList(List<T> tList);
}
