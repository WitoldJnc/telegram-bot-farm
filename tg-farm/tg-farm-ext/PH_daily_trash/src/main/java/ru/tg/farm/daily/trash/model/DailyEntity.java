package ru.tg.farm.daily.trash.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class DailyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "status", columnDefinition = "boolean default false")
    private Boolean status = false;

    private String url;

    public DailyEntity(String title) {
        this.title = title;
    }

    public DailyEntity(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
