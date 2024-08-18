package com.apollo.service;

import com.apollo.entity.Bullet;

import java.util.List;

public interface BulletService {
    List<Bullet> createBullet(List<String> bulletlist, Long productId);

}
