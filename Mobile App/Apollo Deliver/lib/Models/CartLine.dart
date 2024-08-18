import 'package:apollodeliver/Models/Cart.dart';
import 'package:apollodeliver/Models/Variant.dart';

class CartLine {
  int? id;
  int? quantity;
  Cart? cart;
  Variant? variant;

  CartLine({this.id, this.quantity, this.cart, this.variant});

  factory CartLine.fromJson(Map<String, dynamic> json) {
    return CartLine(
      id: json['id'],
      quantity: json['quantity'],
      cart: json['cart'] != null ? Cart.fromJson(json['cart']) : null,
      variant: json['variant'] != null ? Variant.fromJson(json['variant']) : null,
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'quantity': quantity,
    'cart': cart?.toJson(),
    'variant': variant?.toJson(),
  };
}
