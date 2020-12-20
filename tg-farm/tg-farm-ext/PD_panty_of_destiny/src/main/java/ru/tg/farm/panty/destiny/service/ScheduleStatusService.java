package ru.tg.farm.panty.destiny.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tg.farm.panty.destiny.model.ScheduleStatus;

public interface ScheduleStatusService extends JpaRepository<ScheduleStatus, Integer> {
    ScheduleStatus getScheduleStatusByCode(String code);
}
