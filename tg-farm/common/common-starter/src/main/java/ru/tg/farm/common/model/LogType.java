package ru.tg.farm.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum LogType {
    INFO("info"), ERROR("error");

    private String type;
}
