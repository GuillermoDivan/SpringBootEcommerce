package ecommerce.api.entities.Product;

import ecommerce.api.entities.Order.Order;

import java.util.List;

public record ProductShowData(Long Id, String name, double price,
                              String description, List<Order> orderList) {

    public ProductShowData(Product product){
        this(product.getId(), product.getName(), product.getPrice(),
                product.getDescription(), product.getOrder());
    }
}

