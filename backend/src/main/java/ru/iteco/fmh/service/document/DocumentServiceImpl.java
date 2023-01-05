package ru.iteco.fmh.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.DocumentsRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.model.document.Document;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final ConversionService conversionService;
    private final UserRepository userRepository;
    private final DocumentsRepository documentsRepository;

    @Override
    public DocumentCreationDtoRs createDocument(DocumentCreationDtoRq documentCreationDtoRq) {
        Document document = conversionService.convert(documentCreationDtoRq, Document.class);
        String currentUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByLogin(currentUserLogin);
        document.setCreateDate(Instant.now());
        document.setUser(user);
        document = documentsRepository.save(document);
        return conversionService.convert(document, DocumentCreationDtoRs.class);
    }
}
