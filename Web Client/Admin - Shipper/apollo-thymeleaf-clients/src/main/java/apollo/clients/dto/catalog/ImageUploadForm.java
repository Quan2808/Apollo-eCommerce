package apollo.clients.dto.catalog;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadForm {
    private Long variantId;
    private MultipartFile[] files;

    // Getters and Setters
    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
