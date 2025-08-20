package com.paarr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.paarr.dto.*;
import com.paarr.service.StoreService;

import java.util.List;

@RestController
@RequestMapping(value = "/store")
@CrossOrigin(origins = "*")
public class StoreController {

    @Autowired
    StoreService storeService;

    @PostMapping(value = "/save", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResponseDTO> save(@RequestBody StoreDTO storeDTO) {
        ResponseDTO response = storeService.save(storeDTO);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/list", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<StorePageDTO> list(@RequestBody StorePageDTO storePageDTO) {
        storePageDTO = storeService.list(storePageDTO);
        return new ResponseEntity<StorePageDTO>(storePageDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<StoreDTO> get(@RequestParam long id) {
        StoreDTO storeDTO = storeService.get(id);
        return new ResponseEntity<StoreDTO>(storeDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseDTO> delete(@RequestParam long id) {
        ResponseDTO responseDTO = storeService.delete(id);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

//    @GetMapping(value = "/get-by-area")
//    public ResponseEntity<List<StoreDTO>> getByArea(@RequestParam String areaName) {
//        List<StoreDTO> stores = storeService.getByAreaName(areaName);
//        return new ResponseEntity<List<StoreDTO>>(stores, HttpStatus.OK);
//    }
    @GetMapping(value = "/get-by-main-area")
    public ResponseEntity<ResponseDTO> getByMainArea(@RequestParam String mainArea) {
        List<StoreDTO> stores = storeService.getByMainArea(mainArea);
        ResponseDTO response = new ResponseDTO();
        response.setResponseStatus("Success");

        if (stores.isEmpty()) {
            response.setResponseMessage("No stores found in " + mainArea);
            response.setResponse(stores); // empty []
        } else {
            response.setResponseMessage("Stores fetched");
            response.setResponse(stores); // store list here
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/get-by-sub-area")
    public ResponseEntity<List<StoreDTO>> getBySubArea(@RequestParam String subArea) {
        List<StoreDTO> stores = storeService.getBySubArea(subArea);
        return new ResponseEntity<>(stores, HttpStatus.OK);
    }
}
