package com.inventorymanagement.inventory_management_system.controller;

import com.inventorymanagement.inventory_management_system.model.Inventory;
import com.inventorymanagement.inventory_management_system.repository.InventoryManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.inventorymanagement.inventory_management_system.service.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryManagementController {

    private final InventoryManagementRepository inventoryManagementRepo;
    private final InventoryServices inventoryServices;

    @Autowired
    public InventoryManagementController(InventoryManagementRepository inventoryManagementRepo,
                                         InventoryServices inventoryServices) {
        this.inventoryManagementRepo = inventoryManagementRepo;
        this.inventoryServices = inventoryServices;
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        List<Inventory> inventories = inventoryManagementRepo.findAll();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @GetMapping("/{myId}")
    public ResponseEntity<?> getInventoryById(@PathVariable Integer myId) {
        Optional<Inventory> inventory = inventoryManagementRepo.findById(myId);
        if (inventory.isPresent()) {
            return ResponseEntity.ok(inventory.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Inventory not found with ID : " + myId);
        }
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInventoryItem(@PathVariable Integer id, @RequestBody Inventory inventory) {
        try{
            Inventory updated = inventoryServices.updateInventoryDetails(id, inventory);
            return ResponseEntity.ok(updated);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while updating inventory: " + e.getMessage());
        }
    }

}
