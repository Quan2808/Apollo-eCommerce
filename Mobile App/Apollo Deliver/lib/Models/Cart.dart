import 'package:apollodeliver/Models/CartLine.dart';
import 'package:apollodeliver/Models/SaveForLater.dart';
import 'package:apollodeliver/Models/User.dart';

class Cart {
  int? id;
  User? user;
  List<CartLine>? cartLines;
  List<SaveForLater>? saveForLaterList;

  Cart({this.id, this.user, this.cartLines, this.saveForLaterList});

  factory Cart.fromJson(Map<String, dynamic> json) {
    return Cart(
      id: json['id'],
      user: json['user'] != null ? User.fromJson(json['user']) : null,
      cartLines: (json['cartLines'] as List?)?.map((item) => CartLine.fromJson(item)).toList(),
      saveForLaterList: (json['saveForLaterList'] as List?)?.map((item) => SaveForLater.fromJson(item)).toList(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'user': user?.toJson(),
    'cartLines': cartLines?.map((item) => item.toJson()).toList(),
    'saveForLaterList': saveForLaterList?.map((item) => item.toJson()).toList(),
  };
}
