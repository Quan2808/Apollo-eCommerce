import 'package:apollodeliver/Models/ShopOrder.dart';

class ShippingMethod {
  int? id;
  Set<ShopOrder>? shopOrderSet;
  String? name;
  double? price;

  ShippingMethod({
    this.id,
    this.shopOrderSet,
    this.name,
    this.price,
  });

  factory ShippingMethod.fromJson(Map<String, dynamic> json) {
    return ShippingMethod(
      id: json['id'],
      shopOrderSet: (json['shopOrderSet'] as List<dynamic>?)
          ?.map((item) => ShopOrder.fromJson(item as Map<String, dynamic>))
          .toSet(),
      name: json['name'],
      price: json['price']?.toDouble(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'shopOrderSet': shopOrderSet?.map((order) => order.toJson()).toList(),
    'name': name,
    'price': price,
  };
}
