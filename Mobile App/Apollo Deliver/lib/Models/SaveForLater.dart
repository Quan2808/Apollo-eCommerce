import 'package:apollodeliver/Models/Cart.dart';
import 'package:apollodeliver/Models/Variant.dart';


class SaveForLater {
  int? id;
  int? quantity;
  Cart? cart;
  Variant? variant;

  SaveForLater({this.id, this.quantity, this.cart, this.variant});

  factory SaveForLater.fromJson(Map<String, dynamic> json) {
    return SaveForLater(
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