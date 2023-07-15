package br.org.fundatec.SuperCevaJa.dto.order;

import br.org.fundatec.SuperCevaJa.model.order.beer.BeerModel;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    private String login;

    private List<BeerModel> beersOrder;


}
