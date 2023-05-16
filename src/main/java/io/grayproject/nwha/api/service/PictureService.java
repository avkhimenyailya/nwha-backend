package io.grayproject.nwha.api.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.security.Principal;

/**
 * @author Ilya Avkhimenya
 */
public interface PictureService {

    InputStream compressFile(MultipartFile multipartFile);

    File getPicture(String username, String name);

    String uploadPicture(Principal principal, MultipartFile multipartFile);
}
