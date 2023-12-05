package ecommerce.api.controllers;
import ecommerce.api.entities.Order.*;
import ecommerce.api.services.Order.OrderService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/")
    @Transactional
    public ResponseEntity<OrderShowData> saveOrder(@RequestBody @Valid OrderSaveData orderSaveData){
        var order = this.orderService.saveOrder(orderSaveData);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrderShowData> findorderByIdByActive(@PathVariable Long id) {
        try {
            var order = this.orderService.findOrderById(true, id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/inactive/id/{id}")
    public ResponseEntity<OrderShowData> findorderByIdByInactive(@PathVariable Long id) {
        try {
            var order = this.orderService.findOrderById(false, id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<OrderShowData> findorderByClientNameByActive(@PathVariable String name) {
        try {
            var order = this.orderService.findOrderByClientName(true, name);
            return ResponseEntity.ok(order);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/inactive/name/{name}")
    public ResponseEntity<OrderShowData> findOrderByClientNameByInactive(@PathVariable String name) {
        try {
            var order = this.orderService.findOrderByClientName(false, name);
            return ResponseEntity.ok(order);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<Page<OrderShowData>> findOrderByDateByActive(@PathVariable String date, Pageable paging) {
        try {
            return ResponseEntity.ok(orderService.findOrderByDateAndActive(true, date, paging));
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/inactive/date/{date}")
    public ResponseEntity<Page<OrderShowData>> findorderByEmailByInactive(@PathVariable String date, Pageable paging) {
        try {
            return ResponseEntity.ok(orderService.findOrderByDateAndActive(false, date, paging));
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/date1/{date1}/date2/{date2}")
    public ResponseEntity<Page<OrderShowData>> findOrderBetweenDatesByActive(@PathVariable String date1, @PathVariable String date2, Pageable paging) {
        try {
            return ResponseEntity.ok(orderService.findOrderBetweenDatesAndActive(true, date1, date2, paging));
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("inactive/date1/{date1}/date2/{date2}")
    public ResponseEntity<Page<OrderShowData>> findOrderBetweenDatesByInactive(@PathVariable String date1, @PathVariable String date2, Pageable paging) {
        try {
            return ResponseEntity.ok(orderService.findOrderBetweenDatesAndActive(false, date1, date2, paging));
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/all")
    ResponseEntity<Page<OrderShowData>> findOrderListByActive (@PageableDefault(size = 3) Pageable paging) {
        return ResponseEntity.ok(orderService.findAllOrdersByActive(true, paging));
    }

    @GetMapping("/inactive/all")
    ResponseEntity<Page<OrderShowData>> findOrderListByInactive (@PageableDefault(size = 3) Pageable paging) {
        return ResponseEntity.ok(orderService.findAllOrdersByActive(false, paging));
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<OrderShowData> updateorder(@RequestBody @Valid OrderUpdateData orderUpdateData){
        var result = this.orderService.updateOrder(orderUpdateData);
        if (result != null) { return ResponseEntity.ok(result);}
        else {return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
    }

    @PutMapping("/hide/{id}")
    @Transactional
    public ResponseEntity<OrderShowData> hideOrder(@PathVariable Long id){
        var result = this.orderService.hideOrder(id);
        if (result) { return ResponseEntity.ok().build();}
        else {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @PutMapping("/reactive/{id}")
    @Transactional
    public ResponseEntity<OrderShowData> reactiveOrder(@PathVariable Long id){
        var result = this.orderService.reactiveOrder(id);
        if (result) { return ResponseEntity.ok().build();}
        else {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> toggleOrder(@PathVariable Long id){
        var toggledOrder = orderService.toggleOrder(id);
        if (toggledOrder) { return ResponseEntity.ok(toggledOrder);
        }  else { return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
