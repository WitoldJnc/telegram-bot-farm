package ru.tg.farm.panty.destiny.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Table(name = "schedule_status")
@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String code;

    Boolean status;
}
