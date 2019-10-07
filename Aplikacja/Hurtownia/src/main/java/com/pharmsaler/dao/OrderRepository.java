package com.pharmsaler.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmsaler.model.Pharmacy;
import com.pharmsaler.model.PurchaseOrder;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {

	List<PurchaseOrder> findByPharmacy(Pharmacy pharmacy);
}
