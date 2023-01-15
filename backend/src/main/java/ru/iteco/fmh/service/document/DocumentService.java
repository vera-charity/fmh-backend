package ru.iteco.fmh.service.document;

import org.springframework.web.multipart.MultipartFile;

import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;

import ru.iteco.fmh.dto.document.DocumentInfoDto;
import ru.iteco.fmh.model.document.DocumentStatus;

import java.util.List;

/**
 * сервис для работы с документами
 */
public interface DocumentService {
    /**
     * возвращает список всех документов
     */
    List<DocumentInfoDto> getDocumentInfo(String host, List<DocumentStatus> status);

    /**
     * сохраняет документ в деректорию, возвращает путь хранения документа.
     *
     * @param multipartFile документ для загрузки
     * @return родительская деректория + имя файла
     */
    String uploadDocument(MultipartFile multipartFile);

    DocumentCreationDtoRs createDocument(DocumentCreationDtoRq documentCreationDtoRqq);
}
