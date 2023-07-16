package ru.mark.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mark.entity.AppUser;

public interface AppUserDAO extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByTelegramUserId(Long id);
}
