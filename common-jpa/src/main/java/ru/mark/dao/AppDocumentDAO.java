package ru.mark.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mark.entity.AppDocument;

public interface AppDocumentDAO extends JpaRepository<AppDocument, Long> {
}
