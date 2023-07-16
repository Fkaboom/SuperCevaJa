package br.org.fundatec.SuperCevaJa.service;

import br.org.fundatec.SuperCevaJa.dto.order.OrderDTO;
import br.org.fundatec.SuperCevaJa.integration.WeatherIntegrationService;
import br.org.fundatec.SuperCevaJa.model.BeerTypeModel;
import br.org.fundatec.SuperCevaJa.model.order.beer.BeerModel;
import br.org.fundatec.SuperCevaJa.model.order.beer.OrderModel;
import br.org.fundatec.SuperCevaJa.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final WeatherIntegrationService weatherIntegrationService;

    public OrderService(UserService userService, BeerTypeService beerTypeService, WeatherIntegrationService weatherIntegrationService) {
        this.userService = userService;
        this.beerTypeService = beerTypeService;
        this.weatherIntegrationService = weatherIntegrationService;
    }

    public ResponseEntity<String> addOrder(OrderDTO orderDTO) {
        OrderModel orderModel = convertToModel(orderDTO);
        try {
            underageUser(orderModel);

        } catch (Exception e) {
            System.err.println("Error checking Order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }

        try {
            BigDecimal finalPrice = applyDiscounts(orderModel);
            return ResponseEntity.ok(finalPrice.toString());
        } catch (Exception e) {
            System.err.println("Error checking Beer Type: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }


    }

    private void underageUser(OrderModel orderModel) {
        UserModel userModel = userService.getUserByLogin(orderModel.getUserLogin());

        LocalDate currentDate = LocalDate.now();
        int userAge = Period.between(userModel.getBirthDate(), currentDate).getYears();
        final int ageOfMajority = 18;

        if (userAge < ageOfMajority) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Underage user " + userModel.getLogin());
        }
    }

    private BigDecimal applyDiscounts(OrderModel orderModel) {

        BigDecimal totalPrice = calculateTotalPrice(orderModel);

        int quantityItems = calculateQuantityItems(orderModel.getBeersOrder());
        int numberOfItemsForDiscount = 10;
        BigDecimal discountByNumberOfItems = BigDecimal.valueOf(0.1);

        BigDecimal discountValue = BigDecimal.ZERO;

        if (quantityItems >= numberOfItemsForDiscount) {
            discountValue = totalPrice.multiply(discountByNumberOfItems);
        }

        double tempC = weatherIntegrationService.findCity().getTemp_c();
        double temperatureForDiscount = 22;
        BigDecimal discountByTemperature = BigDecimal.valueOf(0.15);

        if (tempC <= temperatureForDiscount) {
            discountValue = discountValue.add(totalPrice.multiply(discountByTemperature));
        }

        BigDecimal finalPrice = totalPrice.subtract(discountValue);

        return finalPrice;


    }

    private int calculateQuantityItems(List<BeerModel> beersOrder) {
        int quantityItems = beersOrder.stream()
                .map(beerModel -> beerModel.getQuantity().intValue())
                .reduce(0, Integer::sum);
        return quantityItems;
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
        orderModel.setUserLogin(orderDTO.getLogin());

        return orderModel;
    }
}