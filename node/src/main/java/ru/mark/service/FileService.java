package ru.mark.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mark.entity.AppDocument;
import ru.mark.entity.AppPhoto;
import ru.mark.service.enums.LinkType;

public interface FileService {
    AppDocument processDoc(Message telegramMessage);
    AppPhoto processPhoto(Message telegramMessage);
    public String generateLink(Long docId, LinkType linkType);
}
