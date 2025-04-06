package com.hezix.shaudifymain.mapper;

public interface Mapper<T,F> {
    T toEntity(F f);
    F toDto(T t);
}
