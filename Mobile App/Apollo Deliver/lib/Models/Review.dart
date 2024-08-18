import 'package:apollodeliver/Models/Comment.dart';
import 'package:apollodeliver/Models/User.dart';
import 'package:apollodeliver/Models/Variant.dart';

class Review {
  int? id;
  int? star;
  String? title;
  String? content;
  String? image01;
  String? image02;
  String? image03;
  String? image04;
  String? image05;
  String? video;
  bool? verifiedShop;
  bool? verifiedAdmin;
  DateTime? createAt;
  DateTime? updateAt;
  User? customer;
  User? shipper;
  Variant? variant;
  List<Comment>? commentList;

  Review({
    this.id,
    this.star,
    this.title,
    this.content,
    this.image01,
    this.image02,
    this.image03,
    this.image04,
    this.image05,
    this.video,
    this.verifiedShop,
    this.verifiedAdmin,
    this.createAt,
    this.updateAt,
    this.customer,
    this.shipper,
    this.variant,
    this.commentList,
  });

  factory Review.fromJson(Map<String, dynamic> json) {
    return Review(
      id: json['id'],
      star: json['star'],
      title: json['title'],
      content: json['content'],
      image01: json['image01'],
      image02: json['image02'],
      image03: json['image03'],
      image04: json['image04'],
      image05: json['image05'],
      video: json['video'],
      verifiedShop: json['verifiedShop'],
      verifiedAdmin: json['verifiedAdmin'],
      createAt: json['createAt'] != null ? DateTime.parse(json['createAt']) : null,
      updateAt: json['updateAt'] != null ? DateTime.parse(json['updateAt']) : null,
      customer: json['customer'] != null ? User.fromJson(json['customer']) : null,
      shipper: json['shipper'] != null ? User.fromJson(json['shipper']) : null,
      variant: json['variant'] != null ? Variant.fromJson(json['variant']) : null,
      commentList: (json['commentList'] as List?)
          ?.map((item) => Comment.fromJson(item))
          .toList(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'star': star,
    'title': title,
    'content': content,
    'image01': image01,
    'image02': image02,
    'image03': image03,
    'image04': image04,
    'image05': image05,
    'video': video,
    'verifiedShop': verifiedShop,
    'verifiedAdmin': verifiedAdmin,
    'createAt': createAt?.toIso8601String(),
    'updateAt': updateAt?.toIso8601String(),
    'customer': customer?.toJson(),
    'shipper': shipper?.toJson(),
    'variant': variant?.toJson(),
    'commentList': commentList?.map((item) => item.toJson()).toList(),
  };
}
