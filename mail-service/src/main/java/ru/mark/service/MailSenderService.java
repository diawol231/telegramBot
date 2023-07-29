package ru.mark.service;

import ru.mark.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
