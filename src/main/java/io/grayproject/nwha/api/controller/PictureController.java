package io.grayproject.nwha.api.controller;

import io.grayproject.nwha.api.service.PictureService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.time.Duration;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/picture")
public class PictureController {
    private final PictureService pictureService;

    @PostMapping("/upload")
    public String uploadPicture(Principal principal,
                                @RequestParam("file") MultipartFile multipartFile) {
        String extension = Objects.requireNonNull(FilenameUtils.getExtension(multipartFile.getOriginalFilename())).toLowerCase(Locale.ROOT);
        if (extension.equals("heic")
                || extension.equals("jpeg")
                || extension.equals("jpg")
                || extension.equals("gif")
                || extension.equals("png")) {
            return pictureService.uploadPicture(principal, multipartFile);
        }
        throw new RuntimeException();
    }

    @Cacheable(value = "images", key = "#filename")
    @GetMapping("/{username}/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String username,
                                             @PathVariable String filename) throws IOException {
        log.info("Pic request {}/{}", username, filename);
        // Load image as Resource
        Path imagePath = Path.of(String.format("src/main/resources/pics/%s/%s", username, filename));
        byte[] imageBytes = Files.readAllBytes(imagePath);
        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.DAYS);

        return ResponseEntity
                .ok()
                .cacheControl(cacheControl)
                .body(imageBytes);
    }
}
