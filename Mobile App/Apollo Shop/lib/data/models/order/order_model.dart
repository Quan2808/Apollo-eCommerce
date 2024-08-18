import 'package:apolloshop/data/models/order/order_item_model.dart';

class OrderModel {
  final int id;
  final DateTime orderDate;
  final String status;
  final double orderTotal;
  final List<OrderItemModel> orderItems;

  OrderModel({
    required this.id,
    required this.orderDate,
    required this.status,
    required this.orderTotal,
    required this.orderItems,
  });

  factory OrderModel.fromJson(Map<String, dynamic> json) {
    return OrderModel(
      id: json['id'] ?? 0,
      orderDate: DateTime.parse(json['orderDate'] ?? ''),
      status: json['status'] ?? '',
      orderTotal: (json['orderTotal'] as num?)?.toDouble() ?? 0.0,
      orderItems: (json['orderItems'] as List<dynamic>? ?? [])
          .map((item) => OrderItemModel.fromJson(item as Map<String, dynamic>))
          .toList(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'orderDate': orderDate.toIso8601String(),
      'status': status,
      'orderTotal': orderTotal,
      'orderItems': orderItems.map((item) => item.toJson()).toList(),
    };
  }
}
