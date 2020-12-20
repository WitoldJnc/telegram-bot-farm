package ru.tg.farm.panty.destiny.service;

public interface HoroSignService {
    void truncate();
    void add(String code);
    Integer getFill();
    Boolean contains(String code);
}
