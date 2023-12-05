package ecommerce.api.services.Order;

import ecommerce.api.entities.Order.OrderSaveData;
import ecommerce.api.entities.Order.OrderShowData;
import ecommerce.api.entities.Order.OrderUpdateData;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderShowData saveOrder(OrderSaveData OrderSaveData);
    OrderShowData findOrderById(Boolean active, Long id) throws EntityNotFoundException;
    OrderShowData findOrderByClientName(Boolean active, String clientName) throws EntityNotFoundException;
    Page<OrderShowData> findOrderByDateAndActive(Boolean active, String date, Pageable paging) throws EntityNotFoundException;
    Page<OrderShowData> findOrderBetweenDatesAndActive(Boolean active, String date1, String date2, Pageable paging) throws EntityNotFoundException;
    Page<OrderShowData> findAllOrdersByActive(Boolean active, Pageable paging);
    OrderShowData updateOrder(OrderUpdateData OrderUpdateData) throws EntityNotFoundException;
    boolean hideOrder(Long id) throws EntityNotFoundException;
    boolean reactiveOrder(Long id) throws EntityNotFoundException;
    boolean toggleOrder(Long id) throws EntityNotFoundException;
    boolean deleteOrder(Long id) throws EntityNotFoundException;
}

