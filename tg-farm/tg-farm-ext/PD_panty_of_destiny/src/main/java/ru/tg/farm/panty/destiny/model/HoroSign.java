package ru.tg.farm.panty.destiny.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Table(name = "horo_sign")
@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoroSign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String code;
}
