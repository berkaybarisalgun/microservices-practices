package com.berkaybarisalgun.invertoryservice.service;

import com.berkaybarisalgun.invertoryservice.model.Inventory;
import com.berkaybarisalgun.invertoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvertoryService {

    private final InventoryRepository repository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) throws Exception {
        Optional<Inventory> inventory=repository.findBySkuCode(skuCode);
        int stock=0;
        if(inventory.isPresent()){
            stock=inventory.get().getQuantity();
        }
        else{
            throw new Exception("not found");
        }
        return stock>0;
    }

}
