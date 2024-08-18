package com.apollo.service.impl;

import com.apollo.converter.SaveForLaterConverter;
import com.apollo.dto.SaveForLaterDTO;
import com.apollo.entity.Cart;
import com.apollo.entity.SaveForLater;
import com.apollo.entity.Variant;
import com.apollo.payload.request.SaveForLaterRequest;
import com.apollo.repository.CartRepository;
import com.apollo.repository.SaveForLaterRepository;
import com.apollo.repository.VariantRepository;
import com.apollo.service.SaveForLaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveForLaterServiceImpl implements SaveForLaterService {
    private final CartRepository cartRepository;
    private final SaveForLaterRepository saveForLaterRepository;
    private final SaveForLaterConverter saveForLaterConverter;
    private final VariantRepository variantRepository;

    @Autowired
    public SaveForLaterServiceImpl(CartRepository cartRepository, SaveForLaterRepository saveForLaterRepository, SaveForLaterConverter saveForLaterConverter, VariantRepository variantRepository) {
        this.cartRepository = cartRepository;
        this.saveForLaterRepository = saveForLaterRepository;
        this.saveForLaterConverter = saveForLaterConverter;
        this.variantRepository = variantRepository;
    }

    @Override
    public List<SaveForLaterDTO> findSaveForLaterByCartId(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        List<SaveForLater> saveForLaters = saveForLaterRepository.findSaveForLaterByCart(cart);
        List<SaveForLaterDTO> saveForLaterDTOS = saveForLaterConverter.convertEntitiesToDtos(saveForLaters);
        return saveForLaterDTOS;
    }

    @Override
    public void removeSaveForLater(Long saveForLaterId) {
        saveForLaterRepository.deleteById(saveForLaterId);
    }

    @Override
    public SaveForLaterDTO addSaveForLater(SaveForLaterRequest saveForLaterRequest) {
        SaveForLater saveForLater = new SaveForLater();
        Cart cart = cartRepository.findById(saveForLaterRequest.getCartId()).orElse(null);
        Variant variant = variantRepository.findById(saveForLaterRequest.getVariantId()).orElse(null);
        saveForLater.setCart(cart);
        saveForLater.setVariant(variant);
        saveForLater.setQuanity(saveForLaterRequest.getQuantity());
        saveForLaterRepository.save(saveForLater);
        SaveForLaterDTO saveForLaterDto = saveForLaterConverter.convertEntityToDto(saveForLater);
        return saveForLaterDto;
    }
}
