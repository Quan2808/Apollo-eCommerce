package com.apollo.service.impl;

import com.apollo.converter.impl.OptionTableConverterImpl;
import com.apollo.dto.OptionTableDTO;
import com.apollo.entity.OptionTable;
import com.apollo.entity.Product;
import com.apollo.repository.OptionTableRepository;
import com.apollo.repository.ProductRepository;
import com.apollo.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {
    private final OptionTableRepository optionTableRepository;
    private final ProductRepository productRepository;
    private final OptionTableConverterImpl optionTableConverter;

    @Autowired
    public OptionServiceImpl (OptionTableRepository optionTableRepository,
                              ProductRepository productRepository,
                              OptionTableConverterImpl optionTableConverter) {
        this.optionTableRepository = optionTableRepository;
        this.productRepository = productRepository;
        this.optionTableConverter=optionTableConverter;
    }
    @Override
    public List<OptionTableDTO> createOption(List<OptionTableDTO> optionDto, Long product_id) {
        Product product = productRepository.findById(product_id).orElse(new Product());
        List<OptionTable> options = optionTableConverter.dtoToEntities(optionDto);
        for(OptionTable element: options){
            element.setProduct(product);
            optionTableRepository.save(element);
        }
        List<OptionTable> optionTableList = product.getOptionTables();

        return optionTableConverter.entitiesToDTOs(optionTableList) ;
    }
    public List<OptionTableDTO> getOptionsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        List<OptionTable> optionTableList = product.getOptionTables();
        return optionTableConverter.entitiesToDTOs(optionTableList);
    }

    // Kiểm tra xem optionId có thuộc productId không
    public boolean isOptionIdBelongsToProductId(Long optionId, Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return false;
        }
        return product.getOptionTables().stream()
                .anyMatch(optionTable -> optionTable.getId().equals(optionId));
    }
}
