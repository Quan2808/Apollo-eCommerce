import 'package:flutter/material.dart';
import 'package:apollodeliver/Screens/MainScreen/ShipperProfileScreen.dart';
import 'package:apollodeliver/Screens/MainScreen/OrderManagementScreen.dart';
import 'package:apollodeliver/Screens/MainScreen/DeliveryManagementScreen.dart';
import 'package:apollodeliver/Screens/MakeColor/NavBar/CustomBottomNavigationBar.dart';
import 'package:apollodeliver/Services/DeliveryService.dart';
import 'package:apollodeliver/Models/ShopOrder.dart';
import 'package:apollodeliver/Models/OrderDelivery.dart';

class HomeScreen extends StatefulWidget {
  final String token;
  final String email;

  HomeScreen({required this.token, required this.email});

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _selectedIndex = 0;
  int acceptedOrdersCount = 0;
  int pendingDeliveriesCount = 0;

  @override
  void initState() {
    super.initState();
    _fetchAcceptedOrders();
    _fetchPendingDeliveries();
  }

  void _fetchAcceptedOrders() async {
    try {
      DeliveryService service = DeliveryService(widget.token);
      List<ShopOrder> fetchedOrders = await service.getAllOrders();
      print('Fetched Orders: $fetchedOrders'); // Debug line
      List<ShopOrder> acceptedOrders = fetchedOrders.where((order) => order.status == 'ACCEPTED').toList();
      print('Accepted Orders: $acceptedOrders'); // Debug line
      setState(() {
        acceptedOrdersCount = acceptedOrders.length;
      });
    } catch (e) {
      print("Failed to load accepted orders: $e");
    }
  }

  void _fetchPendingDeliveries() async {
    try {
      DeliveryService service = DeliveryService(widget.token);
      List<OrderDelivery> fetchedDeliveries = await service.getAllOrderDeliveries();
      print('Fetched Deliveries: $fetchedDeliveries'); // Debug line
      List<OrderDelivery> pendingDeliveries = fetchedDeliveries.where((delivery) => delivery.status == 'PENDING').toList();
      print('Pending Deliveries: $pendingDeliveries'); // Debug line
      setState(() {
        pendingDeliveriesCount = pendingDeliveries.length;
      });
    } catch (e) {
      print("Failed to load pending deliveries: $e");
    }
  }

  List<Widget> _widgetOptions(String token, String email) {
    return [
      Scaffold(
        appBar: AppBar(
          backgroundColor: Color(0xFF7357a4),
          title: Image.asset(
            'assets/images/walkthrough/logoBlack.png',
            height: 40.0,
          ),
          centerTitle: true,
        ),
        body: Center(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                _buildCard('Total New Orders', acceptedOrdersCount, () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => OrderManagementScreen(token: token, email: email),
                    ),
                  );
                }),
                SizedBox(height: 16.0),
                _buildCard('Total Pending Deliveries', pendingDeliveriesCount, () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => DeliveryManagementScreen(token: token, email: email),
                    ),
                  );
                }),
              ],
            ),
          ),
        ),
      ),
      OrderManagementScreen(token: token, email: email),
      DeliveryManagementScreen(token: token, email: email),
      ShipperProfileScreen(token: token, email: email),
    ];
  }

  Widget _buildCard(String title, int count, VoidCallback onTap) {
    return GestureDetector(
      onTap: onTap,
      child: Card(
        elevation: 8.0,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(16.0),
        ),
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                title,
                style: TextStyle(fontSize: 18.0, fontWeight: FontWeight.bold),
              ),
              Text(
                '$count',
                style: TextStyle(fontSize: 24.0, fontWeight: FontWeight.bold, color: Colors.purple),
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _widgetOptions(widget.token, widget.email)[_selectedIndex],
      bottomNavigationBar: CustomBottomNavigationBar(
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
      ),
    );
  }
}