import 'package:apollodeliver/Screens/MakeColor/Onboarding/OnboardingScreen.dart';
import 'package:flutter/material.dart';
import 'Screens/MainScreen/LoginScreen.dart';
import 'Screens/MainScreen/RegisterScreen.dart';
import 'Screens/MainScreen/HomeScreen.dart';
import 'Screens/MainScreen/ShipperProfileScreen.dart';
import 'Screens/MainScreen/OrderManagementScreen.dart';
import 'Screens/MainScreen/DeliveryManagementScreen.dart';
import 'package:flutter_native_splash/flutter_native_splash.dart';

void main() async {
  WidgetsBinding widgetsBinding = WidgetsFlutterBinding.ensureInitialized();
  FlutterNativeSplash.preserve(widgetsBinding: widgetsBinding);
  await Future.delayed(Duration(seconds: 3));
  FlutterNativeSplash.remove();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Delivery App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: '/onboarding',
      routes: {
        '/onboarding': (context) => OnboardingView(),
        '/login': (context) => LoginScreen(),
        '/register': (context) => RegisterScreen(),
      },
      onGenerateRoute: (settings) {
        if (settings.name == '/home') {
          final args = settings.arguments as Map<String, String>;
          return MaterialPageRoute(
            builder: (context) =>
                HomeScreen(token: args['token']!, email: args['email']!),
          );
        } else if (settings.name == '/orderManagement') {
          final args = settings.arguments as Map<String, String>;
          return MaterialPageRoute(
            builder: (context) => OrderManagementScreen(
                token: args['token']!, email: args['email']!),
          );
        } else if (settings.name == '/shipperProfile') {
          final args = settings.arguments as Map<String, String>;
          return MaterialPageRoute(
            builder: (context) => ShipperProfileScreen(
                token: args['token']!, email: args['email']!),
          );
        } else if (settings.name == '/deliveryManagement') {
          final args = settings.arguments as Map<String, String>;
          return MaterialPageRoute(
            builder: (context) => DeliveryManagementScreen(
                token: args['token']!, email: args['email']!),
          );
        }
        // Add more routes if necessary
        return null;
      },
    );
  }
}
