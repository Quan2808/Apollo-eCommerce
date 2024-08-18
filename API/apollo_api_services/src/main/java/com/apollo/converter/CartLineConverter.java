package com.apollo.converter;

import com.apollo.dto.CartLineDTO;
import com.apollo.entity.CartLine;

import java.util.List;

public interface CartLineConverter {
    CartLine convertDtoToEntity(CartLineDTO cartLineDto);
    CartLineDTO convertEntityToDto(CartLine cartLine);
    List<CartLineDTO> convertEntitiesToDtos(List<CartLine> cartLines);

    List<CartLine> convertDtoToEntities(List<CartLineDTO> cartLineDTOS);
}
