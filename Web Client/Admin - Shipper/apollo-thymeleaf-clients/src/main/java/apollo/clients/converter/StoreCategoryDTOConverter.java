package apollo.clients.converter;

import apollo.clients.dto.catalog.StoreCategoryDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StoreCategoryDTOConverter implements Converter<String, StoreCategoryDTO> {

    @Override
    public StoreCategoryDTO convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        StoreCategoryDTO parentStoreCategory = new StoreCategoryDTO();
        parentStoreCategory.setId(Long.parseLong(source));
        return parentStoreCategory;
    }
}

