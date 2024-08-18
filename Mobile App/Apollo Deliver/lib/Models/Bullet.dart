import 'package:apollodeliver/Models/Product.dart';

class Bullet {
  int? id;
  String? name;
  Product? product;

  Bullet({this.id, this.name, this.product});

  factory Bullet.fromJson(Map<String, dynamic> json) {
    return Bullet(
      id: json['id'],
      name: json['name'],
      product: json['product'] != null ? Product.fromJson(json['product']) : null,
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
    'product': product?.toJson(),
  };
}