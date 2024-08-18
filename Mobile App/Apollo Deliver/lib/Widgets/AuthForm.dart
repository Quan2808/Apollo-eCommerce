// // widgets/auth_form.dart
// import 'package:flutter/material.dart';
//
// class AuthForm extends StatefulWidget {
//   final Function(String email, String password) onSubmit;
//   final String submitButtonText;
//
//   AuthForm({required this.onSubmit, required this.submitButtonText});
//
//   @override
//   _AuthFormState createState() => _AuthFormState();
// }
//
// class _AuthFormState extends State<AuthForm> {
//   final _formKey = GlobalKey<FormState>();
//   final _emailController = TextEditingController();
//   final _passwordController = TextEditingController();
//
//   @override
//   Widget build(BuildContext context) {
//     return Form(
//       key: _formKey,
//       child: Column(
//         mainAxisSize: MainAxisSize.min,
//         children: <Widget>[
//           TextFormField(
//             controller: _emailController,
//             decoration: InputDecoration(labelText: 'Email'),
//             keyboardType: TextInputType.emailAddress,
//             validator: (value) {
//               if (value == null || value.isEmpty) {
//                 return 'Please enter your email';
//               }
//               return null;
//             },
//           ),
//           SizedBox(height: 16),
//           TextFormField(
//             controller: _passwordController,
//             decoration: InputDecoration(labelText: 'Password'),
//             obscureText: true,
//             validator: (value) {
//               if (value == null || value.isEmpty) {
//                 return 'Please enter your password';
//               }
//               return null;
//             },
//           ),
//           SizedBox(height: 24),
//           ElevatedButton(
//             onPressed: () {
//               if (_formKey.currentState!.validate()) {
//                 widget.onSubmit(
//                     _emailController.text, _passwordController.text);
//               }
//             },
//             child: Text(widget.submitButtonText),
//           ),
//         ],
//       ),
//     );
//   }
// }
