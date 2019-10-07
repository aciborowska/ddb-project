package com.pharmsaler.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmsaler.model.PurchaseOrder;
import com.pharmsaler.model.OrderPosition;

public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {

	List<OrderPosition> findByOrder(PurchaseOrder order);

}
