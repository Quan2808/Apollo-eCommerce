import 'package:apollodeliver/Models/Product.dart';

class HashTag {
  int? id;
  String? name;
  Product? product;

  HashTag({this.id, this.name, this.product});

  factory HashTag.fromJson(Map<String, dynamic> json) {
    return HashTag(
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
