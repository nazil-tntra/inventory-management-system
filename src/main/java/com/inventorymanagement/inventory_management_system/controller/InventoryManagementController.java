package com.inventorymanagement.inventory_management_system.controller;

import com.inventorymanagement.inventory_management_system.model.Inventory;
import com.inventorymanagement.inventory_management_system.repository.InventoryManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.inventorymanagement.inventory_management_system.service.*;

import java.util.List;
import java.util.Map;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getInventoryById(@PathVariable Integer id) {
        Optional<Inventory> inventory = inventoryManagementRepo.findById(id);
        if (inventory.isPresent()) {
            return ResponseEntity.ok(inventory.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Inventory not found with ID : " + id);
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

    @PostMapping("/add-multiple")
    public ResponseEntity<?> addMultipleInventories(@RequestBody List<Inventory> inventories) {
        try{
            List<Inventory> savedInventories = inventoryManagementRepo.saveAll(inventories);
            return  ResponseEntity.status(HttpStatus.CREATED).body(savedInventories);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while adding inventories: " + e.getMessage());
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

    @PatchMapping("{id}")
    public ResponseEntity<?> updateInventoryPatch(@PathVariable Integer id, @RequestBody Inventory inventory) {
        try{
            Inventory updated = inventoryServices.updateInventoryPartially(id, inventory);
            return ResponseEntity.ok(updated);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while updating inventory: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        Optional<Inventory> inventory = inventoryManagementRepo.findById(id);
        if (inventory.isPresent()) {
            inventoryManagementRepo.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Inventory not found with ID : " + id);
        }
    }

    @GetMapping("/valuation")
    public ResponseEntity<String> getInventoryValuation() {
        Map<String, Double> result = inventoryServices.calculateInventoryValuation();

        String responseMessage = String.format(
                "Total Inventory Valuation:\n" +
                        "- Purchase Value: ₹ %.2f\n" +
                        "- Selling Value: ₹ %.2f\n" +
                        "- Estimated Profit: ₹ %.2f",
                result.get("totalPurchaseValue"),
                result.get("totalSellingValue"),
                result.get("potentialProfit")
        );

        return ResponseEntity.ok(responseMessage);
    }
}
