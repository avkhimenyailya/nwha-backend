package io.grayproject.nwha.api.controller;

import io.grayproject.nwha.api.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/picture")
public class PictureController {
    private final PictureService pictureService;

    @GetMapping(value = "/{username}/{name}", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE
    })
    public InputStreamResource getPicture(@PathVariable String username,
                                          @PathVariable String name) {
        return pictureService.getPicture(username, name);
    }

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
}
