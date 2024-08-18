package com.apollo.controller;

import com.apollo.dto.OrderDeliveryDTO;
import com.apollo.dto.ShopOrderDto;
import com.apollo.entity.OrderDelivery;
import com.apollo.entity.Shipper;
import com.apollo.entity.ShopOrder;
import com.apollo.repository.OrderDeliveryRepository;
import com.apollo.repository.ShipperRepository;
import com.apollo.repository.ShopOrderRepository;
import com.apollo.service.OrderDeliveryService;
import com.apollo.service.ShopOrderService;
import com.apollo.service.impl.OrderDeliveryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/delivery")
public class OrderDeliveryController {
    @Autowired
    private OrderDeliveryService orderDeliveryService;

    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired
    private ShopOrderService orderService;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private ShopOrderService shopOrderService;
    @Autowired
    private OrderDeliveryRepository orderDeliveryRepository;

    @PostMapping("/submit/{id}")
    public @ResponseBody ShopOrder submitOrder(@PathVariable Long id) {
        shopOrderService.acceptOrder(id);
        return new ShopOrder();
    }


    @PostMapping("/save")
    public @ResponseBody OrderDeliveryDTO saveOrderDelivery(@RequestParam Long orderId, @RequestParam String shipperEmail) {
        ShopOrder shopOrder = shopOrderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("ShopOrder not found for this id :: " + orderId));
        Shipper shipper = shipperRepository.findByEmail(shipperEmail);
        if (shipper == null) {
            throw new ResourceNotFoundException("Shipper not found for this email :: " + shipperEmail);
        }
        OrderDeliveryDTO orderDelivery = orderDeliveryService.saveOrderDelivery(shopOrder, shipper);
        return orderDelivery;
    }


    @PostMapping("/changestatus")
    public @ResponseBody OrderDelivery changeOrderStatus(@RequestParam Long orderId, @RequestParam String newStatus, @RequestParam String inducement) {
        System.out.println("Request received to change status of orderId " + orderId + " to " + newStatus);//log
        if (!newStatus.equals("COMPLETED") && !newStatus.equals("CANCEL")) {
            throw new IllegalArgumentException("Invalid status: " + newStatus);
        }

        OrderDelivery updatedOrderDelivery = orderDeliveryService.changeStatus(orderId, newStatus, inducement);
        return updatedOrderDelivery;
    }

    @GetMapping("/orders")
    public List<ShopOrderDto> getAllShopOrders() {
        return orderService.getAllShopOrders();
    }

    @GetMapping("/orders/{orderId}")
    public ShopOrderDto getShopOrderById(@PathVariable Long orderId) {
        return orderService.getShopOrder(orderId);
    }

    @GetMapping("/shipper/{id}")
    public Shipper getShipper(@PathVariable Long id) {
        return shipperRepository.findById(id).get();
    }

    @GetMapping("/shippers")
    public List<Shipper> getAllShippers() {
        return shipperRepository.findAll();
    }

    @GetMapping("/orderdelivery")
    public List<OrderDeliveryDTO> getAllOrderDelivery() {
        return orderDeliveryService.findAllOrderDelivery();
    }

    @GetMapping("/orderdelivery/{orderId}")
    public OrderDeliveryDTO getOrderDeliveryById(@PathVariable Long orderId) {
        return orderDeliveryService.findOrderDeliveryById(orderId);
    }
}