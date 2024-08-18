import 'package:apolloshop/data/models/product/product_model.dart';

class CategoryModel {
  int id;
  String attribute;
  String image;
  CategoryModel? parentCategory;
  List<ProductModel> products;

  CategoryModel({
    required this.id,
    required this.attribute,
    required this.image,
    this.parentCategory,
    this.products = const [],
  });

  static CategoryModel empty() {
    return CategoryModel(
      id: 0,
      attribute: '',
      image: '',
      parentCategory: null,
      products: [],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'attribute': attribute,
      'image': image,
      'parentCategory': parentCategory?.toJson(),
      'products': products.map((e) => e.toJson()).toList(),
    };
  }

  factory CategoryModel.fromJson(Map<String, dynamic> json) {
    return CategoryModel(
      id: json['id'] ?? 0,
      attribute: json['attribute'] ?? '',
      image: json['image'] ?? '',
      parentCategory: json['parentCategory'] != null
          ? CategoryModel.fromJson(json['parentCategory'])
          : null,
      products: json['products'] != null
          ? (json['products'] as List)
              .map((e) => ProductModel.fromJson(e))
              .toList()
          : [],
    );
  }
}
