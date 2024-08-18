import 'package:apolloshop/data/response/address/address_response.dart';
import 'package:apolloshop/data/response/payment_method/payment_method_response.dart';
import 'package:apolloshop/data/response/shipping_method/shipping_method_response.dart';

class OrderResponse {
  final AddressResponse address;
  final PaymentMethodResponse paymentMethod;
  final ShippingMethodResponse shippingMethod;

  OrderResponse({
    required this.address,
    required this.paymentMethod,
    required this.shippingMethod,
  });

  factory OrderResponse.fromJson(Map<String, dynamic> json) {
    return OrderResponse(
      address: AddressResponse.fromJson(json['address'] ?? {}),
      paymentMethod:
          PaymentMethodResponse.fromJson(json['paymentMethod'] ?? {}),
      shippingMethod:
          ShippingMethodResponse.fromJson(json['shippingMethod'] ?? {}),
    );
  }
}
