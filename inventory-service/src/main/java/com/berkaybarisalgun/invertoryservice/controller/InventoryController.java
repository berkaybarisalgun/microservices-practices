package com.berkaybarisalgun.invertoryservice.controller;

import com.berkaybarisalgun.invertoryservice.service.InvertoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InvertoryService service;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode) throws Exception {
        return service.isInStock(skuCode);
    }

}
