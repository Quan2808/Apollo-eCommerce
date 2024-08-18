import 'package:flutter/material.dart';

class LoadingScreen extends StatelessWidget {
  final String message;
  final String gifPath;

  const LoadingScreen({
    Key? key,
    this.message = 'Please wait...',
    this.gifPath = 'assets/logos/loading.gif',
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Container(
          color: Colors.white,
          child: Center(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                Stack(
                  alignment: Alignment.center,
                  children: [
                    Image.asset(
                      gifPath,
                      width: 190,
                      height: 190,
                    ),
                    Positioned(
                      top: 0,
                      left: 0,
                      right: 0,
                      bottom: 0,
                      child: Center(
                        child: SizedBox(
                          width: 200,
                          height: 200,
                          child: CircularProgressIndicator(
                            strokeWidth: 8.0,
                            valueColor: AlwaysStoppedAnimation<Color>(Colors.black45),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
                SizedBox(height: 20),
                Text(
                  message,
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 13,
                  ),
                ),
              ],
            ),
          ),
        ),
      ],
    );
  }
}
