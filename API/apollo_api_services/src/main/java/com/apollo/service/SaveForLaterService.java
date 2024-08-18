package com.apollo.service;

import com.apollo.dto.SaveForLaterDTO;
import com.apollo.payload.request.SaveForLaterRequest;

import java.util.List;

public interface SaveForLaterService {
    List<SaveForLaterDTO> findSaveForLaterByCartId (Long cartId);
    void removeSaveForLater(Long saveForLaterId);
    SaveForLaterDTO addSaveForLater(SaveForLaterRequest saveForLaterRequest);
}
