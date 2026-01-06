package com.congestion.tax.mapper;

public interface AbstractMapper<K, V> {
    K toDto(V entity);
    V toEntity(K dto);
}
