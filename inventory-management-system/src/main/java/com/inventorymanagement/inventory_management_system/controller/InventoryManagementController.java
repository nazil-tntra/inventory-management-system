package com.inventorymanagement.inventory_management_system.controller;

import com.inventorymanagement.inventory_management_system.model.Inventory;
import com.inventorymanagement.inventory_management_system.repository.InventoryManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryManagementController {

    @Autowired
    private InventoryManagementRepository inventoryManagementRepo;


    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryManagementRepo.save(inventory);
    }
}
