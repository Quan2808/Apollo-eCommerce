package com.apollo.service.impl;

import com.apollo.converter.*;
import com.apollo.dto.*;
import com.apollo.entity.*;
import com.apollo.repository.OrderDeliveryRepository;
import com.apollo.repository.ShipperRepository;
import com.apollo.repository.ShopOderRepository;
import com.apollo.repository.ShopOrderRepository;
import com.apollo.service.EmailService;
import com.apollo.service.OrderDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    @Autowired
    private OrderDeliveryConverter convert;
    @Autowired
    private OrderDeliveryRepository orderDeliveryRepository;
    @Autowired
    private ShopOderRepository shopOderRepository;
    @Autowired
    private ShopOrderRepository shopOrderRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private VariantConverter variantConverter;
    @Autowired
    private ShippingMethodConverter shippingMethodConverter;
    @Autowired
    private PaymentMethodConverter paymentMethodConverter;
    @Autowired
    private AddressConverter addressConverter;
    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private EmailService emailService;

    public OrderDeliveryDTO saveOrderDelivery(ShopOrder shopOrder, Shipper shipper) {
        Date date = new Date();
        OrderDelivery orderDelivery = new OrderDelivery();
        orderDelivery.setOrder(shopOrder);
        orderDelivery.setShipper(shipper);
        orderDelivery.setStatus("PENDING");
        orderDelivery.setAssignedDate(date);

        shopOrder.setStatus("ONGOING");
        shopOrder.setDeliveryDate(date);
        shopOderRepository.save(shopOrder);

        orderDeliveryRepository.save(orderDelivery);

        return convert.entityToDTO(orderDelivery);
    }

    public OrderDelivery changeStatus(Long orderId, String newStatus, String inducement) {
        OrderDelivery orderDelivery = orderDeliveryRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("OrderDelivery not found with id: " + orderId));

        ShopOrder shopOrder = orderDelivery.getOrder();
        if (shopOrder != null) {
            shopOrder.setStatus(newStatus);
            shopOderRepository.save(shopOrder);
        }

        orderDelivery.setStatus(newStatus);
        orderDelivery.setInducement(inducement);
        OrderDelivery updatedOrderDelivery = orderDeliveryRepository.save(orderDelivery);

        if ("COMPLETED".equalsIgnoreCase(newStatus)) {
            User user = shopOrder.getUser();
            emailService.sendDeliveryConfirmationEmail(user, shopOrder);
        }

        return updatedOrderDelivery;
    }

    public List<OrderDeliveryDTO> findAllOrderDelivery() {
        List<OrderDelivery> orderDeliveryList = orderDeliveryRepository.findAll();
        List<OrderDeliveryDTO> orderDeliveryDTOList = convert.entitiesToDTOs(orderDeliveryList);
        return orderDeliveryDTOList;
    }

    @Override
    public OrderDeliveryDTO findOrderDeliveryById(Long orderId) {
        OrderDelivery orderDelivery = orderDeliveryRepository.findById(orderId).get();
        OrderDeliveryDTO orderDeliveryDTO = convert.entityToDTO(orderDelivery);
        return orderDeliveryDTO;
    }

    @Override
    public List<OrderDelivery> getOrdersByShipperEmail(String shipperEmail) {
        Shipper shipper = shipperRepository.findByEmail(shipperEmail);
        if (shipper == null) {
            throw new ResourceNotFoundException("Shipper not found for this email :: " + shipperEmail);
        }
        return orderDeliveryRepository.findByShipper(shipper);
    }
}