package com.apollo.utilities;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EntityDTOConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    // Convert Entity to DTO
    public static <E, D> D convertToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    // Convert DTO to Entity
    public static <E, D> E convertToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    // Convert List of Entity to List of DTO
    public static <E, D> List<D> convertListToDto(List<E> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(entity -> convertToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

    // Convert List of DTO to List of Entity
    public static <E, D> List<E> convertListToEntity(List<D> dtos, Class<E> entityClass) {
        return dtos.stream()
                .map(dto -> convertToEntity(dto, entityClass))
                .collect(Collectors.toList());
    }

    // Update Entity with DTO values (for updates)
    public static <E, D> void updateEntity(E entity, D dto) {
        modelMapper.map(dto, entity);
    }
}
