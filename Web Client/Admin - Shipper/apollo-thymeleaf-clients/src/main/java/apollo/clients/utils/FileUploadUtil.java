package apollo.clients.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadUtil {

    private static final String BASE_UPLOAD_DIR = "src/main/resources/static/media/main/";

    public static String saveFile(MultipartFile file, String objectType, String prefix) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String fileName = file.getOriginalFilename();
        String fileExtension = "";
        if (fileName != null && fileName.contains(".")) {
            fileExtension = fileName.substring(fileName.lastIndexOf("."));
        }

        String sanitizedPrefix = sanitizeFileName(prefix);
        String newFileName = sanitizedPrefix + "_" + UUID.randomUUID() + fileExtension;

        Path uploadPath = Paths.get(BASE_UPLOAD_DIR + objectType);
        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(newFileName);
        Files.write(filePath, file.getBytes());

        return "media/main/" + objectType + "/" + newFileName;
    }

    public static boolean deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }

        try {
            Path fullPath = Paths.get(BASE_UPLOAD_DIR).resolve(filePath.replace("media/main/", ""));
            return Files.deleteIfExists(fullPath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}