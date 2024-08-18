import 'package:apollodeliver/Models/Image.dart';
import 'package:apollodeliver/Models/Product.dart';
import 'package:apollodeliver/Models/Store.dart';
import 'package:apollodeliver/Models/Video.dart';

class StoreCategory {
  int? id;
  String? name;
  String? heroImage;
  String? squareImage;
  Store? store;
  StoreCategory? parentStoreCategory;
  List<Product>? productList;
  List<Image>? imageList;
  List<Video>? videoList;

  StoreCategory({
    this.id,
    this.name,
    this.heroImage,
    this.squareImage,
    this.store,
    this.parentStoreCategory,
    this.productList,
    this.imageList,
    this.videoList,
  });

  factory StoreCategory.fromJson(Map<String, dynamic> json) {
    return StoreCategory(
      id: json['id'],
      name: json['name'],
      heroImage: json['heroImage'],
      squareImage: json['squareImage'],
      store: json['store'] != null ? Store.fromJson(json['store']) : null,
      parentStoreCategory: json['parentStoreCategory'] != null
          ? StoreCategory.fromJson(json['parentStoreCategory'])
          : null,
      productList: (json['productList'] as List<dynamic>?)
          ?.map((item) => Product.fromJson(item))
          .toList(),
      imageList: (json['imageList'] as List<dynamic>?)
          ?.map((item) => Image.fromJson(item))
          .toList(),
      videoList: (json['videoList'] as List<dynamic>?)
          ?.map((item) => Video.fromJson(item))
          .toList(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
    'heroImage': heroImage,
    'squareImage': squareImage,
    'store': store?.toJson(),
    'parentStoreCategory': parentStoreCategory?.toJson(),
    'productList': productList?.map((item) => item.toJson()).toList(),
    'imageList': imageList?.map((item) => item.toJson()).toList(),
    'videoList': videoList?.map((item) => item.toJson()).toList(),
  };
}
