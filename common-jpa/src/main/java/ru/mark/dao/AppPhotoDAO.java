package ru.mark.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.mark.entity.AppPhoto;

public interface AppPhotoDAO extends JpaRepository<AppPhoto, Long> {
}
