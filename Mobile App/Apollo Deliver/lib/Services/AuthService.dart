import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class AuthService {
  static const String baseUrl = 'http://10.0.2.2:9999/api';

  static Future<String> login(String email, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/login/shipper'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'email': email,
        'password': password,
      }),
    );

    if (response.statusCode == 200) {
      final Map<String, dynamic> data = json.decode(response.body);
      return data['accessToken'];
    } else {
      throw Exception('Failed to login: ${response.body}');
    }
  }

  static Future<void> register(String name, String email, String password, String phone) async {
    final response = await http.post(
      Uri.parse('$baseUrl/register/shipper'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'shipperName': name,
        'email': email,
        'password': password,
        'phoneNumber': phone,
      }),
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to register. Status code: ${response.statusCode}');
    }
  }

  static Future<Map<String, dynamic>> fetchShipperInfo(String token) async {
    final response = await http.get(
      Uri.parse('$baseUrl/shipper/info'),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    if (response.statusCode == 200) {
      return json.decode(response.body);
    } else {
      throw Exception('Failed to fetch shipper info: ${response.body}');
    }
  }

  static Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('token');
  }
}

