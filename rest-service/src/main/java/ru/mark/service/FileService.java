package ru.mark.service;

import org.springframework.core.io.FileSystemResource;
import ru.mark.entity.AppDocument;
import ru.mark.entity.AppPhoto;
import ru.mark.entity.BinaryContent;

public interface FileService {
    AppDocument getDocument(String id);
    AppPhoto getPhoto(String id);
}
