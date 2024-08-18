import 'dart:convert';
import 'package:apollodeliver/Models/Shipper.dart';
import 'package:http/http.dart' as http;

class ApiService {
  final String baseUrl;
  final String token;

  ApiService({required this.baseUrl, required this.token});

  Future<Shipper?> getShipper(int id) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/api/shippers/$id'),
        headers: {
          'Authorization': 'Bearer $token',
          'Content-Type': 'application/json',
        },
      );

      if (response.statusCode == 200) {
        final jsonData = jsonDecode(response.body);
        return Shipper.fromJson(jsonData);
      } else {
        throw Exception('Failed to load shipper: ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching shipper: $e');
      return null;
    }
  }
}
