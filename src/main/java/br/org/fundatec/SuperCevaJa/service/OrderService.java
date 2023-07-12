package br.org.fundatec.SuperCevaJa.service;

import br.org.fundatec.SuperCevaJa.dto.order.OrderDTO;
import br.org.fundatec.SuperCevaJa.model.order.beer.OrderModel;
import br.org.fundatec.SuperCevaJa.model.UserModel;
import br.org.fundatec.SuperCevaJa.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
public class OrderService {
    private final UserService userService;
    private UserRepository userRepository;
    private final BeerTypeService beerTypeService;

    public OrderService(UserService userService, UserRepository userRepository, BeerTypeService beerTypeService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.beerTypeService = beerTypeService;
    }

    //TERMINAR
    public BigDecimal addOrder(OrderDTO orderDTO) {

        OrderModel orderModel = convertToModel(orderDTO);

        underageUser(orderModel);

        OrderModel orderDiscountsApplied = applyDiscounts(orderModel);

        return BigDecimal.valueOf(10);

    }

    private OrderModel convertToModel(OrderDTO orderDTO) {
        OrderModel orderModel = new OrderModel();

        orderModel.setBeersOrder(orderDTO.getBeersOrder());
        orderModel.setLogin(orderDTO.getLogin());

       return orderModel;
    }

    private void underageUser(OrderModel orderModel){
        UserModel userModel = userRepository.findByLogin(orderModel.getLogin());

        LocalDate currentDate = LocalDate.now();

        int userAge = Period.between(userModel.getBirthDate(), currentDate).getYears();

        if (userAge < 18) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Underage user: " + userModel.getLogin());
        }
    }


// TERMINAR
    private OrderModel applyDiscounts(OrderModel orderModel){
    return orderModel;
    }
}