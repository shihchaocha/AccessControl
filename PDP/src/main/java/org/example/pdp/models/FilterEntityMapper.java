package org.example.pdp.models;

import org.example.models.FilterEntityDTO;

public class FilterEntityMapper {

    public static FilterEntityDTO toDTO(FilterEntity entity) {
        return new FilterEntityDTO(
                entity.getId(),
                entity.getFilterOrder(),
                entity.getName(),
                entity.getClassName()
        );
    }

    public static FilterEntity toEntity(FilterEntityDTO dto) {
        FilterEntity entity = new FilterEntity();
        entity.setId(dto.getId());
        entity.setFilterOrder(dto.getOrder());
        entity.setName(dto.getName());
        entity.setClassName(dto.getClassName());
        return entity;
    }
}

