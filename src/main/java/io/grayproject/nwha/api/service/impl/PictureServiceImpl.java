package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.service.PictureService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.security.Principal;
import java.util.Iterator;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Service
public class PictureServiceImpl implements PictureService {
    private static final String REMOTE_URL = "https://api.nwha.grayproject.io";

    @SneakyThrows
    public InputStream compressFile(MultipartFile multipartFile) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        BufferedImage image = ImageIO.read(multipartFile.getInputStream());
        ByteArrayOutputStream os = new ByteArrayOutputStream((int) multipartFile.getSize());

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension);
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.25f);
        writer.write(null, new IIOImage(image, null, null), param);

        byte[] bytes = os.toByteArray();

        os.close();
        ios.close();
        writer.dispose();

        return new ByteArrayInputStream(bytes);
    }

    @Override
    @SneakyThrows
    public File getPicture(String directoryName, String name) {
        String pathString = String.format("src/main/resources/pics/%s/%s", directoryName, name);
        File file = new File(pathString);
        if (file.exists()) return file;
        throw new RuntimeException();
    }

    @Override
    @SneakyThrows
    public String uploadPicture(Principal principal, MultipartFile multipartFile) {
        InputStream inputStream = checkNeedsCompression(multipartFile)
                ? compressFile(multipartFile)
                : multipartFile.getInputStream();

        String pictureName = createPictureName(principal, multipartFile);
        File file = new File(String.format("src/main/resources/pics/%s", pictureName));
        FileUtils.copyInputStreamToFile(inputStream, file);
        inputStream.close();

        return String.format("/%s/picture/%s", REMOTE_URL, pictureName);
    }

    private boolean checkNeedsCompression(MultipartFile multipartFile) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        return extension != null
                && (extension.equals("png") || extension.equals("jpg"))
                && (multipartFile.getSize() / 1048576) > 1;
    }

    private String createPictureName(Principal principal, MultipartFile multipartFile) {
        String name = RandomStringUtils.random(8, true, true);
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        return String.format("%s/%s.%s", principal.getName(), name, extension);
    }
}
