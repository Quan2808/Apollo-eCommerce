import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/data/models/store/store_model.dart';

class StoreCategoryModel {
  int id;
  String name;
  String heroImage;
  String squareImage;
  StoreModel? store;
  StoreCategoryModel? parentStoreCategory;
  List<ProductModel> productList;

  StoreCategoryModel({
    required this.id,
    required this.name,
    required this.heroImage,
    required this.squareImage,
    this.store,
    this.parentStoreCategory,
    this.productList = const [],
  });

  static StoreCategoryModel empty() {
    return StoreCategoryModel(
      id: 0,
      name: '',
      heroImage: '',
      squareImage: '',
      store: null,
      parentStoreCategory: null,
      productList: [],
    );
  }

  factory StoreCategoryModel.fromJson(Map<String, dynamic> json) {
    return StoreCategoryModel(
      id: json['id'] ?? 0,
      name: json['name'] ?? '',
      heroImage: json['heroImage'] ?? '',
      squareImage: json['squareImage'] ?? '',
      store: json['store'] != null ? StoreModel.fromJson(json['store']) : null,
      parentStoreCategory: json['parentStoreCategory'] != null
          ? StoreCategoryModel.fromJson(json['parentStoreCategory'])
          : null,
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
      'heroImage': heroImage,
      'squareImage': squareImage,
      'store': store?.toJson(),
      'parentStoreCategory': parentStoreCategory?.toJson(),
      'productList': productList.map((e) => e.toJson()).toList(),
    };
  }
}
