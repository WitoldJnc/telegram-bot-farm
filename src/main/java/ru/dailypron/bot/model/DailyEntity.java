package ru.dailypron.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyEntity {

    private Integer id;
    private String title;

//    @Column(name = "status", columnDefinition = "boolean default false")
    private Boolean status = false;

    public DailyEntity(String title) {
        this.title = title;
    }
}
