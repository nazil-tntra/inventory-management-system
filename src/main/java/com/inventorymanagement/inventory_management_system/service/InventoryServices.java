package com.inventorymanagement.inventory_management_system.service;

import com.inventorymanagement.inventory_management_system.model.Inventory;
import com.inventorymanagement.inventory_management_system.repository.InventoryManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryServices {

    @Autowired
    private InventoryManagementRepository inventoryManagementRepo;

    public Inventory updateInventoryDetails(Integer id, Inventory updatedInventory) {
        Inventory existing = inventoryManagementRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        existing.setName(updatedInventory.getName());
        existing.setQuantity(updatedInventory.getQuantity());
        existing.setSelling_price(updatedInventory.getSelling_price());
        existing.setPurchase_price(updatedInventory.getPurchase_price());
        existing.setDescription(updatedInventory.getDescription());

        Double baseSellingPrice = updatedInventory.getSelling_price();
        Double adjustedPrice = applyDynamicPricing(baseSellingPrice, updatedInventory.getQuantity());
        existing.setSelling_price(adjustedPrice);

        Inventory saved = inventoryManagementRepo.save(existing);

        return saved;
    }

    private Double applyDynamicPricing(Double basePrice, int quantity) {
        Double finalPrice;
        if (quantity < 5) {
            finalPrice = basePrice * 1.10;
        } else if (quantity > 20) {
            finalPrice = basePrice * 0.95;
        } else {
            finalPrice = basePrice;
        }
        return Math.round(finalPrice * 100.0) / 100.0;
    }


    public Inventory updateInventoryPartially(Integer id, Inventory updatedInventory) {
        Inventory existing = inventoryManagementRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (updatedInventory.getName() != null) {
            existing.setName(updatedInventory.getName());
        }
        if (updatedInventory.getQuantity() != null) {
            existing.setQuantity(updatedInventory.getQuantity());
            
            Double baseSellingPrice = existing.getSelling_price();
            int qty = existing.getQuantity();

            Double adjustedPrice = applyDynamicPricing(baseSellingPrice, qty);
            existing.setSelling_price(adjustedPrice);
        }
        if (updatedInventory.getSelling_price() != null) {
            existing.setSelling_price(updatedInventory.getSelling_price());

        }if (updatedInventory.getPurchase_price() != null) {
            existing.setPurchase_price(updatedInventory.getPurchase_price());
        }
        if (updatedInventory.getDescription() != null) {
            existing.setDescription(updatedInventory.getDescription());
        }

        return inventoryManagementRepo.save(existing);
    }


    public Map<String, Double> calculateInventoryValuation() {
        Double purchaseValue = inventoryManagementRepo.getTotalPurchaseValue();
        Double sellingValue = inventoryManagementRepo.getTotalSellingValue();

        Map<String, Double> result = new HashMap<>();
        result.put("totalPurchaseValue", purchaseValue != null ? purchaseValue : 0.0);
        result.put("totalSellingValue", sellingValue != null ? sellingValue : 0.0);
        result.put("potentialProfit", (sellingValue != null && purchaseValue != null)
                ? (sellingValue - purchaseValue)
                : 0.0);

        return result;
    }

}
