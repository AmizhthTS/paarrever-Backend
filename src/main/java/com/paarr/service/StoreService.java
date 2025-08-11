package com.paarr.service;

import com.paarr.dto.StoreDTO;
import com.paarr.dto.StorePageDTO;
import com.paarr.dto.ResponseDTO;
import java.util.List;

public interface StoreService {
    ResponseDTO save(StoreDTO storeDTO);
    StorePageDTO list(StorePageDTO storePageDTO);
    StoreDTO get(long id);
    ResponseDTO delete(long id);
    List<StoreDTO> getByAreaName(String areaName);
}
