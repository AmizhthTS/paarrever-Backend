package com.paarr.service.implementation;
import com.paarr.dto.*; 
import com.paarr.entity.StoreModel; 
import com.paarr.repository.StoreRepository; 
import com.paarr.service.StoreService; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.domain.*; 
import org.springframework.stereotype.Service; 
import java.util.List; 
import java.util.stream.Collectors;
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Override
    public ResponseDTO save(StoreDTO storeDTO) {
        StoreModel storeModel = null;

        if (storeDTO.getId() != null && storeDTO.getId() > 0) {
            storeModel = storeRepository.findByIdAndActive(storeDTO.getId(), true);
            if (storeModel == null) throw new RuntimeException("Store not found");
        }

        if (storeModel == null) {
            storeModel = new StoreModel();
            storeModel.setActive(true);
        }

        // Map fields
        storeModel.setStoreName(storeDTO.getStoreName());
        storeModel.setAddress(storeDTO.getAddress());
        storeModel.setMainArea(storeDTO.getMainArea());
        storeModel.setSubArea(storeDTO.getSubArea());
        storeModel.setContactNumber(storeDTO.getContactNumber());
        storeModel.setOpeningTime(storeDTO.getOpeningTime());
        storeModel.setClosingTime(storeDTO.getClosingTime());
        storeModel.setMapLink(storeDTO.getMapLink());

        storeRepository.save(storeModel);

        ResponseDTO response = new ResponseDTO();
        response.setResponseStatus("Success");
        response.setResponseMessage(storeDTO.getId() != null ? "Updated Successfully" : "Saved Successfully");

        return response;
    }

    @Override
    public StorePageDTO list(StorePageDTO storePageDTO) {
        Pageable paging = PageRequest.of(
            Math.max(storePageDTO.getPageNumber() - 1, 0),
            storePageDTO.getListSize() > 0 ? storePageDTO.getListSize() : 25,
            Sort.by("storeName").ascending()
        );

        Page<StoreModel> page;
        if (storePageDTO.getSearchString() != null && !storePageDTO.getSearchString().isEmpty()) {
            page = storeRepository.findByStoreNameContainsIgnoreCaseAndActive(storePageDTO.getSearchString(), true, paging);
        } else {
            page = storeRepository.findAll(paging);
        }

        List<StoreDTO> storeDTOList = page.stream().map(this::mapToDTO).collect(Collectors.toList());

        storePageDTO.setStores(storeDTOList);
        storePageDTO.setCount(page.getTotalElements());
        storePageDTO.setTotalPages(page.getTotalPages());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseStatus("Success");
        responseDTO.setResponseMessage("List Fetched");
        storePageDTO.setResponse(responseDTO);

        return storePageDTO;
    }

    @Override
    public StoreDTO get(long id) {
        StoreModel storeModel = storeRepository.findByIdAndActive(id, true);
        if (storeModel == null) throw new RuntimeException("Store not found");
        return mapToDTO(storeModel);
    }

    @Override
    public ResponseDTO delete(long id) {
        StoreModel storeModel = storeRepository.findByIdAndActive(id, true);
        if (storeModel == null) throw new RuntimeException("Store not found");

        storeModel.setActive(false);
        storeRepository.save(storeModel);

        ResponseDTO response = new ResponseDTO();
        response.setResponseStatus("Success");
        response.setResponseMessage("Deleted Successfully");
        return response;
    }

    @Override
    public List<StoreDTO> getByMainArea(String mainArea) {
        return storeRepository.findByMainAreaContainsIgnoreCaseAndActive(mainArea, true, Pageable.unpaged())
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<StoreDTO> getBySubArea(String subArea) {
        return storeRepository.findBySubAreaContainsIgnoreCaseAndActive(subArea, true, Pageable.unpaged())
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private StoreDTO mapToDTO(StoreModel model) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(model.getId());
        storeDTO.setStoreName(model.getStoreName());
        storeDTO.setAddress(model.getAddress());
        storeDTO.setMainArea(model.getMainArea());
        storeDTO.setSubArea(model.getSubArea());
        storeDTO.setContactNumber(model.getContactNumber());
        storeDTO.setOpeningTime(model.getOpeningTime());
        storeDTO.setClosingTime(model.getClosingTime());
        storeDTO.setMapLink(model.getMapLink());
        return storeDTO;
    }
}
