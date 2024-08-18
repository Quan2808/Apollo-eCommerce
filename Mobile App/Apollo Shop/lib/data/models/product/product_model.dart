import 'package:apolloshop/data/models/category/category_model.dart';
import 'package:apolloshop/data/models/store/store_category_model.dart';
import 'package:apolloshop/data/models/store/store_model.dart';
import 'package:apolloshop/data/models/product/variant_model.dart';

class ProductModel {
  int id;
  String title;
  String description;
  String thumbnail;
  String status;
  CategoryModel? category;
  StoreCategoryModel? storeCategory;
  StoreModel? store;
  List<VariantModel> variants;

  ProductModel({
    required this.id,
    required this.title,
    required this.description,
    required this.thumbnail,
    required this.status,
    this.category,
    this.storeCategory,
    this.store,
    this.variants = const [],
  });

  static ProductModel empty() {
    return ProductModel(
      id: 0,
      title: '',
      description: '',
      thumbnail: '',
      status: '',
      category: null,
      storeCategory: null,
      store: null,
      variants: [],
    );
  }

  factory ProductModel.fromJson(Map<String, dynamic> json) {
    String thumbnail = json['mainPicture'] ?? '';

    if (thumbnail.contains('localhost')) {
      thumbnail = thumbnail.replaceFirst('localhost', '10.0.2.2');
    }

    return ProductModel(
      id: json['id'] ?? 0,
      title: json['title'] ?? '',
      description: json['description'] ?? '',
      thumbnail: thumbnail,
      status: json['status'] ?? '',
      category: json['category'] != null
          ? CategoryModel.fromJson(json['category'])
          : null,
      storeCategory: json['storeCategory'] != null
          ? StoreCategoryModel.fromJson(json['storeCategory'])
          : null,
      store: json['store'] != null ? StoreModel.fromJson(json['store']) : null,
      variants: json['variants'] != null
          ? (json['variants'] as List)
              .map((e) => VariantModel.fromJson(e))
              .toList()
          : [],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'mainPicture': thumbnail,
      'status': status,
      'category': category?.toJson(),
      'storeCategory': storeCategory?.toJson(),
      'store': store?.toJson(),
      'variants': variants.map((e) => e.toJson()).toList(),
    };
  }
}
