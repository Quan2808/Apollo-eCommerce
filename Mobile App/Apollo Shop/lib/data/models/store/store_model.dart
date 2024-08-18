import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/data/models/store/store_category_model.dart';

class StoreModel {
  int id;
  String name;
  String logo;
  String homeImage;
  String dealsImage;
  String dealsSquareImage;
  String interactiveImage;
  List<StoreCategoryModel> storeCategoryList;
  List<ProductModel> productList;

  StoreModel({
    required this.id,
    required this.name,
    required this.logo,
    required this.homeImage,
    required this.dealsImage,
    required this.dealsSquareImage,
    required this.interactiveImage,
    this.storeCategoryList = const [],
    this.productList = const [],
  });

  static StoreModel empty() {
    return StoreModel(
      id: 0,
      name: '',
      logo: '',
      homeImage: '',
      dealsImage: '',
      dealsSquareImage: '',
      interactiveImage: '',
      storeCategoryList: [],
      productList: [],
    );
  }

  factory StoreModel.fromJson(Map<String, dynamic> json) {
    return StoreModel(
      id: json['id'] ?? 0,
      name: json['name'] ?? '',
      logo: json['logo'] ?? '',
      homeImage: json['homeImage'] ?? '',
      dealsImage: json['dealsImage'] ?? '',
      dealsSquareImage: json['dealsSquareImage'] ?? '',
      interactiveImage: json['interactiveImage'] ?? '',
      storeCategoryList: json['storeCategoryList'] != null
          ? (json['storeCategoryList'] as List)
              .map((e) => StoreCategoryModel.fromJson(e))
              .toList()
          : [],
      productList: json['productList'] != null
          ? (json['productList'] as List)
              .map((e) => ProductModel.fromJson(e))
              .toList()
          : [],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'logo': logo,
      'homeImage': homeImage,
      'dealsImage': dealsImage,
      'dealsSquareImage': dealsSquareImage,
      'interactiveImage': interactiveImage,
      'storeCategoryList': storeCategoryList.map((e) => e.toJson()).toList(),
      'productList': productList.map((e) => e.toJson()).toList(),
    };
  }
}
