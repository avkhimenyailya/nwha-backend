package io.grayproject.nwha.api.controller;

import io.grayproject.nwha.api.service.PictureService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.security.Principal;
import java.time.Duration;

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
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (extension != null && (extension.equals("jpg")
                || extension.equals("gif")
                || extension.equals("png"))) {
            return pictureService.uploadPicture(principal, multipartFile);
        }
        throw new RuntimeException();
    }

    @GetMapping("/{username}/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String username,
                                             @PathVariable String filename,
                                             HttpServletRequest request) throws IOException {
        log.info("Pic request {}/{}", username, filename);
        // Load image as Resource
        Path imagePath
                = Path.of(String.format("src/main/resources/pics/%s/%s", username, filename)).normalize();
        Resource resource = new UrlResource(imagePath.toUri());

        // Determine image's media type
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Set cache control headers to enable browser caching
        CacheControl cacheControl = CacheControl.maxAge(Duration.ofDays(30)).cachePublic();

        // Prepare response
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .cacheControl(cacheControl)
                .body(resource);
    }
}
