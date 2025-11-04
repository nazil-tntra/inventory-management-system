package com.inventorymanagement.inventory_management_system.repository;

import com.inventorymanagement.inventory_management_system.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryManagementRepository extends JpaRepository<Inventory, Integer> {
    @Query("SELECT SUM(i.quantity * i.purchase_price) FROM Inventory i")
    Double getTotalPurchaseValue();

    @Query("SELECT SUM(i.quantity * i.selling_price) FROM Inventory i")
    Double getTotalSellingValue();


}
