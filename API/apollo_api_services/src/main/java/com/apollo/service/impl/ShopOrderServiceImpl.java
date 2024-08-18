package com.apollo.service.impl;

import com.apollo.converter.ShopOrderConverter;
import com.apollo.dto.ShopOrderDto;
import com.apollo.dto.order_sumary.OrderSummaryDTO;
import com.apollo.entity.*;
import com.apollo.payload.request.ShopOrderRequest;
import com.apollo.payload.response.ShopOrderResponse;
import com.apollo.repository.*;
import com.apollo.service.EmailService;
import com.apollo.service.ShopOrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShopOrderServiceImpl implements ShopOrderService {

    private final ShopOrderRepository shopOrderRepository;

    private final ShopOrderConverter shopOrderConverter;

    private final UserRepository userRepository;

    private final VariantRepository variantRepository;

    private final AddressRepository addressRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    private final ShippingMethodRepository shippingMethodRepository;

    private final EmailService emailService;

    public ShopOrderServiceImpl(ShopOrderRepository shopOrderRepository, ShopOrderConverter shopOrderConverter, UserRepository userRepository, VariantRepository variantRepository, AddressRepository addressRepository, PaymentMethodRepository paymentMethodRepository, ShippingMethodRepository shippingMethodRepository, EmailService emailService) {
        this.shopOrderRepository = shopOrderRepository;
        this.shopOrderConverter = shopOrderConverter;
        this.userRepository = userRepository;
        this.variantRepository = variantRepository;
        this.addressRepository = addressRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.shippingMethodRepository = shippingMethodRepository;
        this.emailService = emailService;
    }

    @Override
    public List<OrderSummaryDTO> getShopOrders(Long userId) {
//        List<ShopOrder> shopOrders = shopOrderRepository.findAllByUserIdOrderByIdDesc(userId);
//        return shopOrders.stream()
//                .map(this::convertToSummaryDTO)
//                .collect(Collectors.toList());
        return shopOrderRepository.findOrderSummariesByUserId(userId);
    }

    @Override
    public List<ShopOrderDto> getAllShopOrders() {
        List<ShopOrder> shopOrders = shopOrderRepository.findAll();
        return shopOrderConverter.entitiesToDTOs(shopOrders);
    }

    @Override
    public ShopOrderDto getShopOrder(Long shopOrderId) {
        ShopOrder shopOrder = shopOrderRepository.findById(shopOrderId).get();
        return shopOrderConverter.entityToDTO(shopOrder);
    }

    @Override
    public List<ShopOrderResponse> createShopOrder(List<ShopOrderRequest> shopOrderRequest) {
        List<ShopOrderResponse> responses = new ArrayList<>();

        for (ShopOrderRequest request : shopOrderRequest) {
            ShopOrder order = new ShopOrder();

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUser(user);

            order.setOrderDate(LocalDateTime.now().toString());

            Address address = addressRepository.findById(request.getAddressId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            order.setAddress(address);

            PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                    .orElseThrow(() -> new RuntimeException("Payment method not found"));
            order.setPaymentMethod(paymentMethod);

            ShippingMethod shippingMethod = shippingMethodRepository.findById(request.getShippingMethodId())
                    .orElseThrow(() -> new RuntimeException("Shipping method not found"));
            order.setShippingMethod(shippingMethod);

            Variant variant = variantRepository.findById(request.getVariantId())
                    .orElseThrow(() -> new RuntimeException("Variant not found"));
            order.setVariant(variant);

            order.setQuantity(request.getQuantity());

            // Calculate and set order total
            order.setOrderTotal(request.getQuantity() * variant.getPrice() + shippingMethod.getPrice());

            // Set initial status
            order.setStatus("PENDING");

            // Save the order
            ShopOrder savedOrder = shopOrderRepository.save(order);

            // Send payment email
            emailService.sendPaymentEmail(user);

            // Convert to response and add to list
            responses.add(shopOrderConverter.convertToDto(savedOrder));
        }
        return responses;
    }

    @Override
    public ShopOrderResponse acceptOrder(Long id) {
        ShopOrder order = shopOrderRepository.findById(id).get();
        order.setStatus("ACCEPTED");
        order.setDeliveryDate(new Date());
        shopOrderRepository.save(order);
        return shopOrderConverter.convertToDto(order);
    }

    private OrderSummaryDTO convertToSummaryDTO(ShopOrder order) {
        OrderSummaryDTO dto = new OrderSummaryDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setOrderTotal(order.getOrderTotal());
        dto.setVariantName(order.getVariant().getName());
        dto.setQuantity(order.getQuantity());
        dto.setPrice(order.getVariant().getPrice());
        return dto;
    }
}
