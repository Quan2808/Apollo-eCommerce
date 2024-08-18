import 'package:apollodeliver/Screens/MakeColor/Notification/LoadingScreen.dart';
import 'package:flutter/material.dart';
import 'package:apollodeliver/Services/AuthService.dart';

class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  String? _emailError;
  String? _passwordError;

  void _validateInputs() {
    setState(() {
      _emailError = _emailController.text.isEmpty ? 'Email is required' : null;
      _passwordError = _passwordController.text.isEmpty ? 'Password is required' : null;
    });
  }

  Future<void> _login() async {
    _validateInputs();

    if (_emailError == null && _passwordError == null) {
      try {
        showDialog(
          context: context,
          barrierDismissible: false,
          builder: (BuildContext context) => LoadingScreen(
            message: 'Logging....',
          ),
        );

        final token = await AuthService.login(_emailController.text, _passwordController.text);
        await Future.delayed(Duration(seconds: 1));
        Navigator.of(context).pop();
        Navigator.pushReplacementNamed(
          context,
          '/home',
          arguments: {'token': token, 'email': _emailController.text},
        );
      } catch (e) {
        Navigator.of(context).pop();
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(e.toString())),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Container(
            color: Color(0xFF7357a4),
            height: MediaQuery.of(context).size.height / 4,
            child: Center(
              child: Text(
                'Sign in to your Account',
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
          ),
          Expanded(
            child: Padding(
              padding: EdgeInsets.all(16.0),
              child: SingleChildScrollView(
                child: Column(
                  children: [
                    // Email Input
                    TextField(
                      controller: _emailController,
                      decoration: InputDecoration(
                        prefixIcon: Icon(Icons.email),
                        labelText: 'Email',
                        errorText: _emailError,
                        border: OutlineInputBorder(),
                      ),
                      keyboardType: TextInputType.emailAddress,
                    ),
                    SizedBox(height: 16.0),

                    // Password Input
                    TextField(
                      controller: _passwordController,
                      decoration: InputDecoration(
                        prefixIcon: Icon(Icons.lock),
                        labelText: 'Password',
                        errorText: _passwordError,
                        border: OutlineInputBorder(),
                      ),
                      obscureText: true,
                    ),
                    SizedBox(height: 24.0),

                    // Login Button
                    SizedBox(
                      width: double.infinity,
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Color(0xFF7357a4),
                        ),
                        onPressed: _login,
                        child: Text('Login', style: TextStyle( color: Colors.white),),
                      ),
                    ),
                    SizedBox(height: 24.0),

                    // Register Link
                    TextButton(
                      child: Text('Don\'t have an account? Register'),
                      onPressed: () {
                        Navigator.pushNamed(context, '/register');
                      },
                    ),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
