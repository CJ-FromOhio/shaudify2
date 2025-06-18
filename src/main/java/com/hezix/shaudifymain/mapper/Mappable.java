package com.hezix.shaudifymain.mapper;

import java.util.List;

public interface Mappable<T,F> {
    T toEntity(F f);
    F toDto(T t);
    List<F> toDtoList(List<T> t);
    List<T> toEntityList(List<F> f);
}
