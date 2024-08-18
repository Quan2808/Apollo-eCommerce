import 'package:apollodeliver/Screens/MainScreen/LoginScreen.dart';
import 'package:flutter/material.dart';
import 'package:apollodeliver/Services/AuthService.dart';

class ShipperProfileScreen extends StatelessWidget {
  final String token;
  final String email;

  ShipperProfileScreen({required this.token, required this.email});

  Future<Map<String, dynamic>> _getShipperInfo() async {
    return await AuthService.fetchShipperInfo(token);
  }

  void _logout(BuildContext context) async {
    await AuthService.logout();
    Navigator.of(context).pushAndRemoveUntil(
      MaterialPageRoute(builder: (context) => LoginScreen()),
          (Route<dynamic> route) => false,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Profile Details'),
        backgroundColor: Color(0xFF7357a4),
      ),
      body: FutureBuilder<Map<String, dynamic>>(
        future: _getShipperInfo(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data == null) {
            return Center(child: Text('No profile information found.'));
          } else {
            final shipperInfo = snapshot.data!;
            return Padding(
              padding: EdgeInsets.all(16.0),
              child: Column(
                children: [
                  _buildProfileItem(
                    icon: Icons.person,
                    label: 'Name',
                    value: shipperInfo['shipperName'],
                  ),
                  SizedBox(height: 16.0),
                  _buildProfileItem(
                    icon: Icons.email,
                    label: 'Email',
                    value: shipperInfo['email'],
                  ),
                  SizedBox(height: 16.0),
                  _buildProfileItem(
                    icon: Icons.phone,
                    label: 'Phone',
                    value: shipperInfo['phoneNumber'] ?? 'N/A',
                  ),
                  Spacer(),
                  SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Color(0xFF7357a4),
                        padding: EdgeInsets.symmetric(vertical: 16.0),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(8.0),
                        ),
                      ),
                      onPressed: () => _logout(context),
                      child: Text(
                        'Logout',
                        style: TextStyle(color: Colors.white, fontSize: 18),
                      ),
                    ),
                  ),
                ],
              ),
            );
          }
        },
      ),
    );
  }

  Widget _buildProfileItem({
    required IconData icon,
    required String label,
    required String value,
  }) {
    return Container(
      padding: EdgeInsets.all(16.0),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(8.0),
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.5),
            spreadRadius: 1,
            blurRadius: 5,
            offset: Offset(0, 3),
          ),
        ],
      ),
      child: Row(
        children: [
          Icon(icon, size: 28, color: Color(0xFF7357a4)),
          SizedBox(width: 16.0),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  label,
                  style: TextStyle(fontSize: 14, color: Colors.grey),
                ),
                SizedBox(height: 4.0),
                Text(
                  value,
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
