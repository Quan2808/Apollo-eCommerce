package apollo.clients.request;

import apollo.clients.dto.catalog.VariantImageDTO;

import java.util.List;

public class ImageCreateRequest {

    private List<VariantImageDTO> imageDtoList;
    private Long variantId;

    // Constructor, Getters, and Setters

    public ImageCreateRequest(List<VariantImageDTO> imageDtoList, Long variantId) {
        this.imageDtoList = imageDtoList;
        this.variantId = variantId;
    }

    public List<VariantImageDTO> getImageDtoList() {
        return imageDtoList;
    }

    public void setImageDtoList(List<VariantImageDTO> imageDtoList) {
        this.imageDtoList = imageDtoList;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }
}

