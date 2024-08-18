import 'package:apolloshop/utils/constants/api_constants.dart';
import 'package:get/get.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class StoreService extends GetxService {
  static StoreService get instance => Get.find();

  final String baseUrl = ApiConstants.baseApiUrl;

  Future<List<dynamic>> getProducts() async {
    final response = await http.get(Uri.parse('$baseUrl/stores'));
    if (response.statusCode == 200) {
      return json.decode(response.body);
    } else {
      throw Exception('Failed to load stores');
    }
  }
}
