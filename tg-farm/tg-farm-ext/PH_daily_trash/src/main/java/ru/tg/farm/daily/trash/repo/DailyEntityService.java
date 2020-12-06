package ru.tg.farm.daily.trash.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tg.farm.daily.trash.model.DailyEntity;

import java.util.List;
import java.util.Optional;

public interface DailyEntityService extends JpaRepository<DailyEntity, Long> {

    List<DailyEntity> findAllByStatusIsTrue();

    List<DailyEntity> findAllByStatusIsFalse();

    Optional<DailyEntity> findFirstByStatusIsFalse();
}
