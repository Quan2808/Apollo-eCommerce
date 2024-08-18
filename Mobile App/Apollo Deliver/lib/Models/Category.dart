import 'package:apollodeliver/Models/Product.dart';

class Category {
  int? id;
  String? attribute;
  Category? parentCategory;
  List<Product>? products;

  Category({this.id, this.attribute, this.parentCategory, this.products});

  factory Category.fromJson(Map<String, dynamic> json) {
    return Category(
      id: json['id'],
      attribute: json['attribute'],
      parentCategory: json['parentCategory'] != null ? Category.fromJson(json['parentCategory']) : null,
      products: (json['products'] as List?)?.map((item) => Product.fromJson(item)).toList(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'attribute': attribute,
    'parentCategory': parentCategory?.toJson(),
    'products': products?.map((item) => item.toJson()).toList(),
  };
}