package com.apollo.service.impl;

import com.apollo.converter.impl.OptionValueConverterImpl;
import com.apollo.dto.OptionValueDTO;
import com.apollo.entity.OptionTable;
import com.apollo.entity.OptionValue;
import com.apollo.entity.Variant;
import com.apollo.repository.OptionTableRepository;
import com.apollo.repository.OptionValueRepository;
import com.apollo.repository.VariantRepository;
import com.apollo.service.OptionValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OptionValueServiceImpl implements OptionValueService {
    private final VariantRepository variantRepository;
    private final OptionValueRepository optionValueRepository;
    private final OptionTableRepository optionTableRepository;
    private final OptionValueConverterImpl optionValueConverterImpl;


    @Autowired
    public OptionValueServiceImpl(
            VariantRepository variantRepository,
            OptionValueRepository optionValueRepository,
            OptionTableRepository optionTableRepository,
            OptionValueConverterImpl optionValueConverterImpl
    ) {
        this.variantRepository = variantRepository;
        this.optionValueRepository = optionValueRepository;
        this.optionTableRepository = optionTableRepository;
        this.optionValueConverterImpl = optionValueConverterImpl;
    }
    @Override
    public List<OptionValueDTO> getOptionValuesByVariantId(Long variant_id) {
        Variant variant= variantRepository.findById(variant_id).orElse(null);
        return optionValueConverterImpl.entitiesToDTOs(variant.getOptionValues());
    }

    @Override
    public List<OptionValueDTO> createOptionValue(List<OptionValueDTO> optionValueDto, Long option_id) {
        List<OptionValue> optionValue = optionValueConverterImpl.dtosToEntities(optionValueDto);
        OptionTable option = optionTableRepository.findById(option_id).orElse(null);

        if (option == null) {
            throw new IllegalArgumentException("OptionTable không tồn tại với ID: " + option_id);
        }

        for (OptionValue ele : optionValue) {
            ele.setOptionTable(option);
            optionValueRepository.save(ele);
        }
        List<OptionValue> optionValueList = option.getOptionValues();
        return optionValueConverterImpl.entitiesToDTOs(optionValueList);
    }

    public List<OptionValueDTO> getOptionValuesByOptionId(Long optionId) {
        OptionTable option = optionTableRepository.findById(optionId).orElse(null);
        if (option == null) {
            throw new IllegalArgumentException("OptionTable không tồn tại với ID: " + optionId);
        }
        List<OptionValue> optionValues = option.getOptionValues();
        return optionValueConverterImpl.entitiesToDTOs(optionValues);
    }

    // Phương thức mới để lấy OptionValues dựa trên productId
    public List<OptionValueDTO> getOptionValuesByProductId(Long productId) {
        // Tìm tất cả OptionTable thuộc về Product
        List<OptionTable> optionTables = optionTableRepository.findByProductId(productId);

        // Tập hợp tất cả OptionValues từ các OptionTable này
        List<OptionValue> optionValues = new ArrayList<>();
        for (OptionTable optionTable : optionTables) {
            optionValues.addAll(optionTable.getOptionValues());
        }

        // Chuyển đổi các OptionValues thành DTOs
        return optionValueConverterImpl.entitiesToDTOs(optionValues);
    }
}
