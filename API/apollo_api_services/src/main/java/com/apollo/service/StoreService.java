package com.apollo.service;

import com.apollo.dto.StoreDTO;
import com.apollo.payload.request.AddStoreRequest;

import java.util.List;

public interface StoreService {

    List<StoreDTO> getAllStores();

    StoreDTO findStore(Long id);

    StoreDTO createStore(Long sellerId, AddStoreRequest storeDto);

    StoreDTO deleteStore(Long id);
}
