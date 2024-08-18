import 'package:apolloshop/data/models/cart/cart_item_model.dart';
import 'package:apolloshop/data/models/user/user_model.dart';

class CartModel {
  int id;
  UserModel? user;
  List<CartItemModel> cartLines;

  CartModel({
    required this.id,
    this.user,
    this.cartLines = const [],
  });

  static CartModel empty() {
    return CartModel(
      id: 0,
      user: null,
      cartLines: [],
    );
  }

  factory CartModel.fromJson(Map<String, dynamic> json) {
    return CartModel(
      id: json['id'] ?? 0,
      user: json['user'] != null ? UserModel.fromJson(json['user']) : null,
      cartLines: json['cartLines'] != null
          ? (json['cartLines'] as List)
              .map((e) => CartItemModel.fromJson(e))
              .toList()
          : [],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'user': user?.toJson(),
      'cartLines': cartLines.map((e) => e.toJson()).toList(),
    };
  }

  CartModel copyWith({
    int? id,
    UserModel? user,
    List<CartItemModel>? cartLines,
  }) {
    return CartModel(
      id: id ?? this.id,
      user: user ?? this.user,
      cartLines: cartLines ?? this.cartLines,
    );
  }
}
