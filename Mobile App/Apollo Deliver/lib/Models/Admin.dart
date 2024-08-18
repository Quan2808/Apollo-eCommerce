import 'package:apollodeliver/Models/Address.dart';
import 'package:apollodeliver/Models/Comment.dart';
import 'package:apollodeliver/Models/Store.dart';

class Admin {
  int? id;
  String? adminName;
  String? email;
  String? password;
  double? balance;
  String? role;
  Set<Address>? address;
  List<Store>? storeList;
  List<Comment>? storeComments;

  Admin({
    this.id,
    this.adminName,
    this.email,
    this.password,
    this.balance,
    this.role,
    this.address,
    this.storeList,
    this.storeComments,
  });

  factory Admin.fromJson(Map<String, dynamic> json) {
    return Admin(
      id: json['id'],
      adminName: json['adminName'],
      email: json['email'],
      password: json['password'],
      balance: json['balance'],
      role: json['role'],
      address: (json['address'] as List?)?.map((item) => Address.fromJson(item)).toSet(),
      storeList: (json['storeList'] as List?)?.map((item) => Store.fromJson(item)).toList(),
      storeComments: (json['storeComments'] as List?)?.map((item) => Comment.fromJson(item)).toList(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'adminName': adminName,
    'email': email,
    'password': password,
    'balance': balance,
    'role': role,
    'address': address?.map((item) => item.toJson()).toList(),
    'storeList': storeList?.map((item) => item.toJson()).toList(),
    'storeComments': storeComments?.map((item) => item.toJson()).toList(),
  };
}