package br.org.fundatec.SuperCevaJa.dto.order;

import br.org.fundatec.SuperCevaJa.model.order.beer.BeerModel;

import java.util.List;

public class OrderDTO {

    private String login;

    private List<BeerModel> beersOrder;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<BeerModel> getBeersOrder() {
        return beersOrder;
    }

    public void setBeersOrder(List<BeerModel> beersOrder) {
        this.beersOrder = beersOrder;
    }
}
