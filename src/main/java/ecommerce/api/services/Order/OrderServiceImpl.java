package ecommerce.api.services.Order;
import ecommerce.api.entities.Client.Client;
import ecommerce.api.entities.Order.*;
import ecommerce.api.repositories.ClientRepository;
import ecommerce.api.repositories.OrderRepository;
import ecommerce.api.services.Client.ClientService;
import ecommerce.api.services.Client.ClientServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    @Override
    public OrderShowData saveOrder(OrderSaveData orderSaveData) { return null; }
        /*Order order = new Order();
        var client = this.clientRepository.findById(orderSaveData.clientId()).orElseThrow(EntityNotFoundException::new);
        if (client.isActive()) { order.setClient(client); }
        else { throw new }
        }
        this.orderRepository.save(order);
        return new OrderShowData(order);
    }*/

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
    public Page<OrderShowData> findOrderByDateAndActive(Boolean active, String date, Pageable paging) throws EntityNotFoundException {
        LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return this.orderRepository.findAllByDateAndActive(date1, active, paging).map(OrderShowData::new);
    }

    @Override
    public Page<OrderShowData>findOrderBetweenDatesAndActive(Boolean active, String date1, String date2, Pageable paging) throws EntityNotFoundException {
        LocalDate dateBefore = LocalDate.parse(date1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate dateAfter = LocalDate.parse(date2, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return this.orderRepository.findAllBetweenDatesAndActive(dateBefore, dateAfter, active, paging).map(OrderShowData::new);
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

