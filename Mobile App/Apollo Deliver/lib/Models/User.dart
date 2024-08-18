import 'package:apollodeliver/Models/Address.dart';
import 'package:apollodeliver/Models/Cart.dart';
import 'package:apollodeliver/Models/PaymentMethod.dart';
import 'package:apollodeliver/Models/Review.dart';
import 'package:apollodeliver/Models/ShopOrder.dart';

class User {
  int? id;
  String? clientName;
  String? password;
  bool? enabled;
  Set<Address>? address;
  String? email;
  String? phoneNumber;
  String? role;
  Cart? cart;
  Set<ShopOrder>? shopOrders;
  List<Review>? reviewList;
  Set<PaymentMethod>? paymentMethods;

  User({
    this.id,
    this.clientName,
    this.password,
    this.enabled,
    this.address,
    this.email,
    this.phoneNumber,
    this.role,
    this.cart,
    this.shopOrders,
    this.reviewList,
    this.paymentMethods,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      clientName: json['clientName'],
      password: json['password'],
      enabled: json['enabled'],
      address: (json['address'] as List?)
          ?.map((item) => Address.fromJson(item))
          .toSet(),
      email: json['email'],
      phoneNumber: json['phoneNumber'],
      role: json['role'],
      cart: json['cart'] != null ? Cart.fromJson(json['cart']) : null,
      shopOrders: (json['shopOrders'] as List?)
          ?.map((item) => ShopOrder.fromJson(item))
          .toSet(),
      reviewList: (json['reviewList'] as List?)
          ?.map((item) => Review.fromJson(item))
          .toList(),
      paymentMethods: (json['paymentMethods'] as List?)
          ?.map((item) => PaymentMethod.fromJson(item))
          .toSet(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'clientName': clientName,
    'password': password,
    'enabled': enabled,
    'address': address?.map((item) => item.toJson()).toList(),
    'email': email,
    'phoneNumber': phoneNumber,
    'role': role,
    'cart': cart?.toJson(),
    'shopOrders': shopOrders?.map((item) => item.toJson()).toList(),
    'reviewList': reviewList?.map((item) => item.toJson()).toList(),
    'paymentMethods': paymentMethods?.map((item) => item.toJson()).toList(),
  };
}
