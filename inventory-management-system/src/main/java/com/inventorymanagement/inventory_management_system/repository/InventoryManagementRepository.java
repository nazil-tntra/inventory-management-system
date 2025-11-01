package com.inventorymanagement.inventory_management_system.repository;

import com.inventorymanagement.inventory_management_system.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryManagementRepository extends JpaRepository<Inventory, Integer> {
}
