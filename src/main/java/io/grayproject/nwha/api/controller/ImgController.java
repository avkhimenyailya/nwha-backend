package io.grayproject.nwha.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@RestController
@RequestMapping("/img")
public class ImgController {

    @GetMapping(value = "/{name}", produces = {
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE
    })
    public ResponseEntity<InputStreamResource> getFileFromFtpServerByPath(@PathVariable String name)
            throws IOException {
        File file = new File("images/" + name);
        String mimeType = Files.probeContentType(file.toPath());
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity
                .ok()
                .contentType(MediaType.asMediaType(MimeType.valueOf(mimeType)))
                .body(inputStreamResource);
    }
}
