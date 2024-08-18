package com.apollo.controller;

import com.apollo.dto.order_sumary.OrderSummaryDTO;
import com.apollo.payload.request.AddressRequest;
import com.apollo.payload.request.PaymentMethodRequest;
import com.apollo.payload.request.ShopOrderRequest;
import com.apollo.payload.response.AddressResponse;
import com.apollo.payload.response.PaymentMethodResponse;
import com.apollo.payload.response.ShippingMethodResponse;
import com.apollo.payload.response.ShopOrderResponse;
import com.apollo.service.AddressService;
import com.apollo.service.PaymentMethodService;
import com.apollo.service.ShippingMethodService;
import com.apollo.service.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/payments")
public class PaymentController {

    private final ShopOrderService shopOrderService;

    private final AddressService addressService;

    private final ShippingMethodService shippingMethodService;

    private final PaymentMethodService paymentMethodService;

    public PaymentController(ShopOrderService shopOrderService, AddressService addressService, ShippingMethodService shippingMethodService, PaymentMethodService paymentMethodService) {
        this.shopOrderService = shopOrderService;
        this.addressService = addressService;
        this.shippingMethodService = shippingMethodService;
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("/orders/{userId}")
    private ResponseEntity<List<OrderSummaryDTO>> findShopOrder(@PathVariable("userId") Long userId){
        List<OrderSummaryDTO> shopOrderResponses = shopOrderService.getShopOrders(userId);
        return new ResponseEntity<>(shopOrderResponses, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<List<ShopOrderResponse>> createShopOrder(@RequestBody List<ShopOrderRequest> shopOrderRequest) {
        List<ShopOrderResponse> shopOrderResponse = shopOrderService.createShopOrder(shopOrderRequest);
        return new ResponseEntity<>(shopOrderResponse, HttpStatus.CREATED);
    }

    @GetMapping("/address/{userId}")
    private ResponseEntity<List<AddressResponse>> findAddress(@PathVariable("userId") Long userId){
        List<AddressResponse> addressResponse = addressService.findAddress(userId);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @GetMapping("/shipping-method")
    private ResponseEntity<List<ShippingMethodResponse>> findShippingMethod(){
        List<ShippingMethodResponse> shippingMethodResponseList = shippingMethodService.findShippingMethod();
        return new ResponseEntity<>(shippingMethodResponseList, HttpStatus.OK);
    }

    @GetMapping("/payment-method/{userId}")
    private ResponseEntity<List<PaymentMethodResponse>> findPaymentMethod(@PathVariable("userId") Long userId){
        List<PaymentMethodResponse> paymentMethodResponseList = paymentMethodService.findPaymentMethod(userId);
        return new ResponseEntity<>(paymentMethodResponseList, HttpStatus.OK);
    }

    @PostMapping("/address")
    private ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest addressRequest){
        AddressResponse addressResponse = addressService.createAddress(addressRequest);
        return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
    }

    @PostMapping("/payment-method")
    private ResponseEntity<PaymentMethodResponse> createPaymentMethod(@RequestBody PaymentMethodRequest paymentMethodRequest){
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.createPaymentMethod(paymentMethodRequest);
        return new ResponseEntity<>(paymentMethodResponse, HttpStatus.CREATED);
    }

    @PutMapping("/address")
    private ResponseEntity<AddressResponse> editAddress(@RequestBody AddressRequest addressRequest){
        AddressResponse addressResponse = addressService.updateAddress(addressRequest);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @PutMapping("/payment-method")
    private ResponseEntity<PaymentMethodResponse> editPaymentMethod(@RequestBody PaymentMethodRequest paymentMethodRequest){
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.updatePaymentMethod(paymentMethodRequest);
        return new ResponseEntity<>(paymentMethodResponse, HttpStatus.OK);
    }
}
