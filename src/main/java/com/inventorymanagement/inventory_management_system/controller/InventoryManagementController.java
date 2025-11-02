package com.inventorymanagement.inventory_management_system.controller;

import com.inventorymanagement.inventory_management_system.model.Inventory;
import com.inventorymanagement.inventory_management_system.repository.InventoryManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryManagementController {

    @Autowired
    private InventoryManagementRepository inventoryManagementRepo;

    @GetMapping
    public List<Inventory> getAll() {
        return inventoryManagementRepo.findAll();
    }

    @GetMapping("/{myId}")
    public Inventory getInventoryById(@PathVariable Integer myId) {
        return inventoryManagementRepo.findById(myId).orElse(null);
    }

    @PostMapping
    public ResponseEntity<?> addInventory(@RequestBody Inventory inventory) {
        try{
            Inventory savedInventory = inventoryManagementRepo.save(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInventory);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while adding inventory: " + e.getMessage());
        }
    }

}
