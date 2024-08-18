import 'package:apollodeliver/Models/ShopOrder.dart';
import 'package:apollodeliver/Models/User.dart';

class PaymentMethod {
  int? id;
  int? cartNumber;
  String? nameOnCard;
  String? expirationDate;
  bool? defaultPayment;
  Set<ShopOrder>? shopOrderSet;
  User? user;

  PaymentMethod({
    this.id,
    this.cartNumber,
    this.nameOnCard,
    this.expirationDate,
    this.defaultPayment,
    this.shopOrderSet,
    this.user,
  });

  factory PaymentMethod.fromJson(Map<String, dynamic> json) {
    return PaymentMethod(
      id: json['id'],
      cartNumber: json['cartNumber'],
      nameOnCard: json['nameOnCard'],
      expirationDate: json['expirationDate'],
      defaultPayment: json['defaultPayment'],
      shopOrderSet: (json['shopOrderSet'] as List<dynamic>?)
          ?.map((item) => ShopOrder.fromJson(item as Map<String, dynamic>))
          .toSet(),
      user: json['user'] != null ? User.fromJson(json['user']) : null,
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'cartNumber': cartNumber,
    'nameOnCard': nameOnCard,
    'expirationDate': expirationDate,
    'defaultPayment': defaultPayment,
    'shopOrderSet': shopOrderSet?.map((order) => order.toJson()).toList(),
    'user': user?.toJson(),
  };
}
