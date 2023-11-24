package ecommerce.api.services.Order;
import ecommerce.api.entities.Order.*;
import ecommerce.api.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public OrderShowData saveOrder(OrderSaveData orderSaveData) {
        Order order = new Order(orderSaveData);
        this.orderRepository.save(order);
        return new OrderShowData(order);
    }

    @Override
    public OrderShowData findOrderById(Boolean active, Long id)
            throws EntityNotFoundException {
        var orderShowData = this.orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new OrderShowData(orderShowData);
    }

    @Override
    public OrderShowData findOrderByClientName(Boolean active, String clientName)
            throws EntityNotFoundException {
        var orderShowData = this.orderRepository.findByClientNameAndActive(clientName, active).orElseThrow(EntityNotFoundException::new);
        return new OrderShowData(orderShowData);
    }

    @Override
    public Page<OrderShowData> findAllOrdersByActive(Boolean active, Pageable paging) {
        return this.orderRepository.findAllOrdersByActive(active, paging).map(OrderShowData::new);
    }

    @Override
    public OrderShowData updateOrder(OrderUpdateData orderUpdateData)
            throws EntityNotFoundException {
        var order = this.orderRepository.findById(orderUpdateData.id())
                .orElseThrow(EntityNotFoundException::new);
        if (order.isActive()) {
                order.setProductList(orderUpdateData.productList());
            }
            this.orderRepository.save(order);
            return new OrderShowData(order);
        }

    @Override
    public boolean hideOrder(Long id) throws EntityNotFoundException {
        var order = this.orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (order.isActive()) {
            order.setActive(false);
            this.orderRepository.save(order);
            return true;
        } else { return false; }
    }

    @Override
    public boolean reactiveOrder(Long id) throws EntityNotFoundException {
        var order = this.orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (order.isActive()) {
            order.setActive(true);
            this.orderRepository.save(order);
            return true;
        } else { return false; }
    }

    @Override
    public boolean toggleOrder(Long id) throws EntityNotFoundException {
        var order = this.orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (order != null) {
            order.setActive(!order.isActive());
            this.orderRepository.save(order);
            return true;
        } else { return false; }
    }

    @Override
    public boolean deleteOrder(Long id) throws EntityNotFoundException {
        var order = this.orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (order != null) { this.orderRepository.delete(order);
        return true;
        } else { return false; }
    }
    }

