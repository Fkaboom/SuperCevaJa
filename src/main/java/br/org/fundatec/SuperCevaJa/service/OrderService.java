package br.org.fundatec.SuperCevaJa.service;

import br.org.fundatec.SuperCevaJa.dto.order.OrderDTO;
import br.org.fundatec.SuperCevaJa.model.BeerTypeModel;
import br.org.fundatec.SuperCevaJa.model.order.beer.BeerModel;
import br.org.fundatec.SuperCevaJa.model.order.beer.OrderModel;
import br.org.fundatec.SuperCevaJa.model.UserModel;
import br.org.fundatec.SuperCevaJa.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class OrderService {
    private final UserService userService;
    private final BeerTypeService beerTypeService;

    public OrderService(UserService userService, UserRepository userRepository, BeerTypeService beerTypeService) {
        this.userService = userService;
        this.beerTypeService = beerTypeService;
    }

    public BigDecimal addOrder(OrderDTO orderDTO) {

        OrderModel orderModel = convertToModel(orderDTO);

        underageUser(orderModel);

        BigDecimal finalPrice = applyDiscounts(orderModel);

        return finalPrice;

    }

    private void underageUser(OrderModel orderModel){
        UserModel userModel = userService.getUserByLogin(orderModel.getLogin());

        LocalDate currentDate = LocalDate.now();

        int userAge = Period.between(userModel.getBirthDate(), currentDate).getYears();

        if (userAge < 18) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Underage user: " + userModel.getLogin());
        }
    }


    //Adicionar Clima
    private BigDecimal applyDiscounts(OrderModel orderModel){

        BigDecimal totalPrice = calculateTotalPrice(orderModel);


        //ARRUMAR TAMANHO LISTA DE PEDIDO (PRODUTOS QUANT.)
        if (orderModel.getBeersOrder().size() >= 10){
            totalPrice = totalPrice.
                    subtract(totalPrice.multiply(BigDecimal.valueOf(0.1)));
        }

        return totalPrice;
    }

    private BigDecimal calculateTotalPrice(OrderModel orderModel) {
        List<BeerModel> beersOrder = orderModel.getBeersOrder();

        BigDecimal totalPrice = beersOrder.stream()
                .map(beerModel -> {
                    BeerTypeModel beerTypeModel = beerTypeService.findByName(beerModel.getNameType());
                    BigDecimal price = beerTypeModel.getPrice();
                    BigDecimal quantity = beerModel.getQuantity();
                    return price.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalPrice;
    }

    private OrderModel convertToModel(OrderDTO orderDTO) {
        OrderModel orderModel = new OrderModel();

        orderModel.setBeersOrder(orderDTO.getBeersOrder());
        orderModel.setLogin(orderDTO.getLogin());

        return orderModel;
    }
}