import 'package:apollodeliver/Models/Address.dart';
import 'package:apollodeliver/Models/OrderDelivery.dart';
import 'package:apollodeliver/Models/PaymentMethod.dart';
import 'package:apollodeliver/Models/ShippingMethod.dart';
import 'package:apollodeliver/Models/User.dart';
import 'package:apollodeliver/Models/Variant.dart';

class ShopOrder {
  final int? id;
  late String status;
  final String orderDate;
  final DateTime deliveryDate;
  final int quantity;
  final double? orderTotal;
  final User user;
  final Variant variant;
  final Address address;
  final PaymentMethod paymentMethod;
  final ShippingMethod shippingMethod;
  final Set<OrderDelivery>? orderDeliveries;  // Added field

  ShopOrder({
    this.id,
    required this.status,
    required this.orderDate,
    required this.deliveryDate,
    required this.quantity,
    this.orderTotal,
    required this.user,
    required this.variant,
    required this.address,
    required this.paymentMethod,
    required this.shippingMethod,
    this.orderDeliveries,  // Added parameter
  });

  factory ShopOrder.fromJson(Map<String, dynamic> json) {
    return ShopOrder(
      id: json['id'] as int?,  // Add `as int?` to allow null
      status: json['status'] ?? 'UNKNOWN',  // Provide a default value if status is null
      orderDate: json['orderDate'] ?? '',  // Provide a default value
      deliveryDate: json['deliveryDate'] != null
          ? DateTime.fromMillisecondsSinceEpoch(json['deliveryDate'])
          : DateTime.now(),  // Handle null for deliveryDate
      quantity: json['quantity'] ?? 0,  // Provide a default value if quantity is null
      orderTotal: json['orderTotal']?.toDouble(),  // Handle null conversion to double
      user: User.fromJson(json['user'] ?? {}),
      variant: Variant.fromJson(json['variant'] ?? {}),
      address: Address.fromJson(json['address'] ?? {}),
      paymentMethod: PaymentMethod.fromJson(json['paymentMethod'] ?? {}),
      shippingMethod: ShippingMethod.fromJson(json['shippingMethod'] ?? {}),
      orderDeliveries: (json['orderDeliveries'] as List<dynamic>?)
          ?.map((item) => OrderDelivery.fromJson(item as Map<String, dynamic>))
          .toSet(),
    );
  }


  Map<String, dynamic> toJson() => {
    'id': id,
    'status': status,
    'orderDate': orderDate,
    'deliveryDate': deliveryDate.toIso8601String(),
    'quantity': quantity,
    'orderTotal': orderTotal,
    'user': user.toJson(),
    'variant': variant.toJson(),
    'address': address.toJson(),
    'paymentMethod': paymentMethod.toJson(),
    'shippingMethod': shippingMethod.toJson(),
    'orderDeliveries': orderDeliveries?.map((e) => e.toJson()).toList(),
  };
}
