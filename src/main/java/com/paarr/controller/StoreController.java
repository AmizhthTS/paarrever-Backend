package com.paarr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.paarr.dto.*;
import com.paarr.service.StoreService;

import java.util.List;

@RestController
@RequestMapping(value = "/store")
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

    @GetMapping(value = "/get-by-area")
    public ResponseEntity<List<StoreDTO>> getByArea(@RequestParam String areaName) {
        List<StoreDTO> stores = storeService.getByAreaName(areaName);
        return new ResponseEntity<List<StoreDTO>>(stores, HttpStatus.OK);
    }
}
