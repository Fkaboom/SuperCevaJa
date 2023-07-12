package br.org.fundatec.SuperCevaJa.repository;

import br.org.fundatec.SuperCevaJa.model.order.beer.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<OrderModel,Long> {
}
