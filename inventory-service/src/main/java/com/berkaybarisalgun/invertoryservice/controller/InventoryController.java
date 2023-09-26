package com.berkaybarisalgun.invertoryservice.controller;

import com.berkaybarisalgun.invertoryservice.dto.InventoryResponse;
import com.berkaybarisalgun.invertoryservice.service.InvertoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InvertoryService service;

    //http://localhost:8082/api/inventory/iphone-13,iphone13-red

    //http://localhost:8082/api/inventory?sku-code=iphone-13&sku-code=iphone13-red
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return service.isInStock(skuCode);
    }

}
