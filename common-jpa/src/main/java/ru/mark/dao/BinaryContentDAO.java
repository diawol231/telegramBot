package ru.mark.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mark.entity.BinaryContent;

public interface BinaryContentDAO extends JpaRepository<BinaryContent, Long> {

}
