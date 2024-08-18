import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:apolloshop/utils/constants/api_constants.dart';
import 'package:get/get.dart';

class CartService extends GetxService {
  static CartService get instance => Get.find();

  final String baseUrl = ApiConstants.baseApiUrl;

  Future<void> postCart({
    required String clientName,
    required String email,
    required String phoneNumber,
    required String password,
  }) async {
    final response = await http.post(
      Uri.parse('$baseUrl/cart'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode({
        'clientName': clientName,
        'email': email,
        'phoneNumber': phoneNumber,
        'password': password,
      }),
    );

    if (response.statusCode == 200) {
      jsonDecode(response.body);
    } else {
      throw Exception(
          'Failed to register new cart. Status code: ${response.statusCode}');
    }
  }

  Future<Map<String, dynamic>?> getCart({
    required String user,
  }) async {
    final response = await http.get(
      Uri.parse('$baseUrl/cart/user/$user'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception(
          'Failed to retrieve cart. Status code: ${response.statusCode}');
    }
  }

  Future<List<dynamic>?> getCartItems(String user) async {
    final response = await http.get(
      Uri.parse('$baseUrl/cart-lines/$user'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception(
          'Failed to retrieve cart items. Status code: ${response.statusCode}');
    }
  }

  Future<void> manageCartItem(Map<String, dynamic> cartItem) async {
    final response = await http.post(
      Uri.parse('$baseUrl/cart-lines/manage-cart-line'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(cartItem),
    );

    if (response.statusCode != 200 &&
        response.statusCode != 201 &&
        response.statusCode != 500 &&
        response.statusCode != 405) {
      throw Exception(
          'Failed to manage cart line. Status code: ${response.statusCode}, Response body: ${response.body}');
    }
  }

  Future<void> deleteAllCartItems(String userId) async {
    final response = await http.delete(
      Uri.parse('$baseUrl/cart-lines/delete-all/$userId'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    if (response.statusCode != 200) {
      throw Exception(
          'Failed to delete all cart lines. Status code: ${response.statusCode}, Response body: ${response.body}');
    }
  }
}
