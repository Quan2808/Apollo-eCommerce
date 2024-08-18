package com.apollo.converter.impl;

import com.apollo.converter.StoreCategoryConverter;
import com.apollo.dto.StoreCategoryDTO;
import com.apollo.entity.StoreCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreCategoryConverterImpl implements StoreCategoryConverter {

    @Override
    public List<StoreCategoryDTO> convertEntitiesToDTOs(List<StoreCategory> element) {
        return element.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StoreCategoryDTO convertEntityToDTO(StoreCategory element) {
        StoreCategoryDTO result = new StoreCategoryDTO();
        BeanUtils.copyProperties(element, result);

        // Gán thủ công storeId từ entity Store sang DTO
        if (element.getStore() != null) {
            result.setStoreId(element.getStore().getId());
        }

        return result;
    }

    @Override
    public StoreCategory dtoToEntity(StoreCategoryDTO element) {
        StoreCategory result = new StoreCategory();
        BeanUtils.copyProperties(element, result);
        // Bạn có thể cần thêm logic để chuyển đổi storeId thành thực thể Store nếu cần thiết.
        return result;
    }
}
