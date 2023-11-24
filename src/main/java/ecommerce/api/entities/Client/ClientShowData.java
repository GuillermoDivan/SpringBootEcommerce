package ecommerce.api.entities.Client;
import ecommerce.api.entities.Order.Order;

import java.util.List;

public record ClientShowData(Long id, String name, String email, List<Order> orders) {

    public ClientShowData(Client client) {
        this(client.getId(), client.getName(), client.getEmail(), client.getOrders());}
    }

