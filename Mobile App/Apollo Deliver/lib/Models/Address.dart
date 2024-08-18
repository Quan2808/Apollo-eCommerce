import 'package:apollodeliver/Models/Admin.dart';
import 'package:apollodeliver/Models/Shipper.dart';
import 'package:apollodeliver/Models/ShopOrder.dart';
import 'package:apollodeliver/Models/User.dart';

class Address {
  int? id;
  String? district;
  String? ward;
  String? city;
  String? street;
  User? user;
  Shipper? shipper;
  Admin? admin;
  Set<ShopOrder>? shopOrders;

  Address({
    this.id,
    this.district,
    this.ward,
    this.city,
    this.street,
    this.user,
    this.shipper,
    this.admin,
    this.shopOrders,
  });

  factory Address.fromJson(Map<String, dynamic> json) {
    return Address(
      id: json['id'],
      district: json['district'],
      ward: json['ward'],
      city: json['city'],
      street: json['street'],
      user: json['user'] != null ? User.fromJson(json['user']) : null,
      shipper: json['shipper'] != null ? Shipper.fromJson(json['shipper']) : null,
      admin: json['admin'] != null ? Admin.fromJson(json['admin']) : null,
      shopOrders: (json['shopOrders'] as List?)
          ?.map((item) => ShopOrder.fromJson(item))
          .toSet(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'district': district,
    'ward': ward,
    'city': city,
    'street': street,
    'user': user?.toJson(),
    'shipper': shipper?.toJson(),
    'admin': admin?.toJson(),
    'shopOrders': shopOrders?.map((item) => item.toJson()).toList(),
  };
}
