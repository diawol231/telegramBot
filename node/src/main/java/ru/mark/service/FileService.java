package ru.mark.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mark.entity.AppDocument;
import ru.mark.entity.AppPhoto;

public interface FileService {
    AppDocument processDoc(Message telegramMessage);
    AppPhoto processPhoto(Message telegramMessage);
}
