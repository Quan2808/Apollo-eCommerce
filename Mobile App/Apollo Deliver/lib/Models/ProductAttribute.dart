import 'package:apollodeliver/Models/Product.dart';

class ProductAttribute {
  int? id;
  String? name;
  String? value;
  Product? product;

  ProductAttribute({
    this.id,
    this.name,
    this.value,
    this.product,
  });

  factory ProductAttribute.fromJson(Map<String, dynamic> json) {
    return ProductAttribute(
      id: json['id'],
      name: json['name'],
      value: json['value'],
      product: json['product'] != null ? Product.fromJson(json['product']) : null,
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
    'value': value,
    'product': product?.toJson(),
  };
}
