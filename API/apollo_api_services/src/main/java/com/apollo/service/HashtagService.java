package com.apollo.service;

import com.apollo.entity.HashTag;

import java.util.List;

public interface HashtagService {
    List<HashTag> createHashtag(List<String> hashtagList, Long productId);
}
