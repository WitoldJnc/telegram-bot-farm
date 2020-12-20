package ru.tg.farm.panty.destiny.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "man_pants")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManPants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String url;

    @Column(name = "hasUsing", columnDefinition = "boolean default false")
    Boolean hasUsing = false;

    Date dt;
}
