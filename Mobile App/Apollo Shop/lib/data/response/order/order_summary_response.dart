import 'order_item_summary_response.dart';

class OrderSummaryResponse {
  final int id;
  final String orderDate;
  final String status;
  final double orderTotal;
  final List<OrderItemSummaryResponse> orderItems;

  OrderSummaryResponse({
    required this.id,
    required this.orderDate,
    required this.status,
    required this.orderTotal,
    required this.orderItems,
  });

  factory OrderSummaryResponse.fromJson(Map<String, dynamic> json) {
    return OrderSummaryResponse(
      id: json['id'] ?? 0,
      orderDate: json['orderDate'] ?? '',
      status: json['status'] ?? '',
      orderTotal: (json['orderTotal'] as num?)?.toDouble() ?? 0.0,
      orderItems: (json['orderItems'] as List<dynamic>?)
              ?.map((item) => OrderItemSummaryResponse.fromJson(
                  item as Map<String, dynamic>))
              .toList() ??
          [],
    );
  }
}
