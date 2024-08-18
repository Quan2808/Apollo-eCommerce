import 'package:apollodeliver/Models/Address.dart';
import 'package:apollodeliver/Models/Review.dart';

class Shipper {
  int? id;
  String? shipperName;
  String? password;
  String? phoneNumber;
  bool? enabled;
  Set<Address>? address;
  String? email;
  String? role;
  List<Review>? reviewList;

  Shipper({
    this.id,
    this.shipperName,
    this.password,
    this.phoneNumber,
    this.enabled,
    this.address,
    this.email,
    this.role,
    this.reviewList,
  });

  factory Shipper.fromJson(Map<String, dynamic> json) {
    return Shipper(
      id: json['id'],
      shipperName: json['shipperName'],
      password: json['password'],
      phoneNumber: json['phoneNumber'],
      enabled: json['enabled'],
      address: (json['address'] as List<dynamic>?)
          ?.map((i) => Address.fromJson(i as Map<String, dynamic>))
          .toSet(),
      email: json['email'],
      role: json['role'],
      reviewList: (json['reviewList'] as List<dynamic>?)
          ?.map((i) => Review.fromJson(i as Map<String, dynamic>))
          .toList(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'shipperName': shipperName,
    'password': password,
    'phoneNumber': phoneNumber,
    'enabled': enabled,
    'address': address?.map((a) => a.toJson()).toList(),
    'email': email,
    'role': role,
    'reviewList': reviewList?.map((r) => r.toJson()).toList(),
  };
}
