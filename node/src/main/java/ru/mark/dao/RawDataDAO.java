package ru.mark.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mark.entity.RawData;

public interface RawDataDAO extends JpaRepository<RawData, Long> {

}
