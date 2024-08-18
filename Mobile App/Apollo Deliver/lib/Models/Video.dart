import 'package:apollodeliver/Models/StoreCategory.dart';
import 'package:apollodeliver/Models/Variant.dart';

class Video {
  int? id;
  String? videoPath;
  Variant? variant;
  StoreCategory? storeCategory;

  Video({
    this.id,
    this.videoPath,
    this.variant,
    this.storeCategory,
  });

  factory Video.fromJson(Map<String, dynamic> json) {
    return Video(
      id: json['id'],
      videoPath: json['videoPath'],
      variant: json['variant'] != null ? Variant.fromJson(json['variant']) : null,
      storeCategory: json['storeCategory'] != null ? StoreCategory.fromJson(json['storeCategory']) : null,
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'videoPath': videoPath,
    'variant': variant?.toJson(),
    'storeCategory': storeCategory?.toJson(),
  };
}
