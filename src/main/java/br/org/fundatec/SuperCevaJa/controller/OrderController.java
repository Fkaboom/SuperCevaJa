package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.dto.order.OrderDTO;
import br.org.fundatec.SuperCevaJa.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cevaja/api/v1/pedidos")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public BigDecimal addOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.addOrder(orderDTO);
    }
}
