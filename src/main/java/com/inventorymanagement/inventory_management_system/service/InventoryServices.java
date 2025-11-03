package com.inventorymanagement.inventory_management_system.service;

import com.inventorymanagement.inventory_management_system.model.Inventory;
import com.inventorymanagement.inventory_management_system.repository.InventoryManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServices {

    @Autowired
    private InventoryManagementRepository inventoryManagementRepo;

    public Inventory updateInventoryDetails(Integer id, Inventory updatedInventory) {
        Inventory existing = inventoryManagementRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        existing.setName(updatedInventory.getName());
        existing.setQuantity(updatedInventory.getQuantity());
        existing.setPrice(updatedInventory.getPrice());
        existing.setDescription(updatedInventory.getDescription());

        Inventory saved = inventoryManagementRepo.save(existing);

        return saved;
    }



}
