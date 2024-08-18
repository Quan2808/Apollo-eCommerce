import 'dart:convert';
import 'package:apollodeliver/Models/OrderDelivery.dart';
import 'package:apollodeliver/Models/ShopOrder.dart';
import 'package:http/http.dart' as http;

class DeliveryService {
  static const String baseUrl = 'http://10.0.2.2:9999/api/delivery';
  final String token;

  DeliveryService(this.token);

  Future<List<ShopOrder>> getAllOrders() async {
    final response = await http.get(
      Uri.parse('$baseUrl/orders'),
      headers: {
        'Authorization': 'Bearer $token',
      },
    );
    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      print('Data from API: $data');
      return data.map((item) => ShopOrder.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load orders');
    }
  }

  Future<void> acceptOrder(int orderId, String shipperEmail) async {
    final response = await http.post(
      Uri.parse('$baseUrl/save?orderId=$orderId&shipperEmail=$shipperEmail'),
      headers: {
        'Authorization': 'Bearer $token',
      },
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to accept order');
    }
  }

  Future<void> changeOrderStatus(int orderId, String newStatus, String? inducement) async {
    final response = await http.post(
      Uri.parse('$baseUrl/changestatus?orderId=$orderId&newStatus=$newStatus&inducement=$inducement'),
      headers: {
        'Authorization': 'Bearer $token',
      },
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to change order status');
    }
  }

  Future<Map<String, dynamic>> getOrderById(int orderId) async {
    final response = await http.get(
      Uri.parse('$baseUrl/orders/$orderId'),
      headers: {
        'Authorization': 'Bearer $token',
      },
    );
    if (response.statusCode == 200) {
      return json.decode(response.body);
    } else {
      throw Exception('Failed to load order');
    }
  }

  Future<List<OrderDelivery>> getAllOrderDeliveries() async {
    final response = await http.get(
      Uri.parse('$baseUrl/orderdelivery'),
      headers: {
        'Authorization': 'Bearer $token',
      },
    );
    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      print('Data from API: $data');
      return data.map((item) => OrderDelivery.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load orders');
    }
  }

  Future<OrderDelivery> getOrderDeliveryById(int orderId) async {
    final response = await http.get(
      Uri.parse('$baseUrl/orderdelivery/$orderId'),
      headers: {
        'Authorization': 'Bearer $token',
      },
    );
    if (response.statusCode == 200) {
      return OrderDelivery.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to load order');
    }
  }
}
