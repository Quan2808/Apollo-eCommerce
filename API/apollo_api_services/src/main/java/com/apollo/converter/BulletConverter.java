package com.apollo.converter;

import com.apollo.dto.BulletDTO;
import com.apollo.entity.Bullet;

import java.util.List;

public interface BulletConverter {
    List<BulletDTO> entitiesToDTOs(List<Bullet> element);
    BulletDTO entityToDTO(Bullet element);
    Bullet dtoToEntity(BulletDTO element);
}
