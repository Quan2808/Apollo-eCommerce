import 'package:apollodeliver/Screens/MakeColor/Notification/LoadingScreen.dart';
import 'package:flutter/material.dart';
import 'package:apollodeliver/Services/AuthService.dart';

class RegisterScreen extends StatefulWidget {
  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final _nameController = TextEditingController();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _phoneController = TextEditingController();

  String? _nameError;
  String? _emailError;
  String? _passwordError;
  String? _phoneError;

  void _validateInputs() {
    setState(() {
      final name = _nameController.text.trim();
      final email = _emailController.text.trim();
      final password = _passwordController.text.trim();
      final phone = _phoneController.text.trim();

      _nameError = name.isEmpty ? 'Name is required' :
      name.length < 2 ? 'Name must be at least 2 characters' : null;

      final emailRegExp = RegExp(r'^[a-zA-Z0-9._%+-]+@gmail\.com$');
      _emailError = email.isEmpty ? 'Email is required' :
      !emailRegExp.hasMatch(email) ? 'Email must have @gmail.com at the end' : null;

      final passwordRegExp = RegExp(r'^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$');
      _passwordError = password.isEmpty ? 'Password is required' :
      !passwordRegExp.hasMatch(password) ?
      'Password must start with an uppercase letter, \n'
          'include at least 1 number and 1 special character' : null;

      final phoneRegExp = RegExp(r'^[0-9]{9,12}$');
      _phoneError = phone.isEmpty ? 'Phone number is required' :
      !phoneRegExp.hasMatch(phone) ? 'Phone number must be 9 to 12 digits' : null;
    });
  }

  Future<void> _register() async {
    _validateInputs();

    if (_nameError == null && _emailError == null && _passwordError == null && _phoneError == null) {
      try {
        showDialog(
          context: context,
          barrierDismissible: false,
          builder: (BuildContext context) => LoadingScreen(
            message: 'Registering...',
          ),
        );

        await AuthService.register(
          _nameController.text.trim(),
          _emailController.text.trim(),
          _passwordController.text.trim(),
          _phoneController.text.trim(),
        );

        await Future.delayed(Duration(seconds: 3));

        Navigator.of(context).pop();
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Registration successful')),
        );
        Navigator.pushReplacementNamed(context, '/login');
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
                'Sign up your Account',
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
                    TextField(
                      controller: _nameController,
                      decoration: InputDecoration(
                        prefixIcon: Icon(Icons.person),
                        labelText: 'Name',
                        errorText: _nameError,
                        border: OutlineInputBorder(),
                      ),
                    ),
                    SizedBox(height: 16.0),

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
                    SizedBox(height: 16.0),

                    TextField(
                      controller: _phoneController,
                      decoration: InputDecoration(
                        prefixIcon: Icon(Icons.phone),
                        labelText: 'Phone Number',
                        errorText: _phoneError,
                        border: OutlineInputBorder(),
                      ),
                      keyboardType: TextInputType.phone,
                    ),
                    SizedBox(height: 24.0),

                    SizedBox(
                      width: double.infinity,
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Color(0xFF7357a4),
                        ),
                        onPressed: _register,
                        child: Text('Register', style: TextStyle(color: Colors.white)),
                      ),
                    ),
                    SizedBox(height: 24.0),

                    // Login Link
                    TextButton(
                      child: Text('Already have an account? Login'),
                      onPressed: () {
                        Navigator.pushNamed(context, '/login');
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
