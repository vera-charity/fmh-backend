package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iteco.fmh.service.document.DocumentService;

@Tag(name = "Документы")
@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")
public class DocumentsController {

    private final DocumentService documentService;

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Загрузка документа")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadDocument(@RequestPart(name = "postcard_image") MultipartFile multipartFile) {
        return documentService.uploadDocument(multipartFile);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Удаление документа")
    @DeleteMapping("{id}")
    public void deleteDocument(@Parameter(description = "Идентификатор документа", required = true) @PathVariable("id") int id) {
        documentService.deleteDocument(id);
    }
}
