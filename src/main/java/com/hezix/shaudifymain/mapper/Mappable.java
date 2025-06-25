package com.hezix.shaudifymain.mapper;

import java.util.List;
import java.util.Set;

public interface Mappable<T,F> {
    T toEntity(F f);
    F toDto(T t);
}
