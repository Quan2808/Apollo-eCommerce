import 'package:flutter/material.dart';

class ProductAttributeText extends StatelessWidget {
  const ProductAttributeText({
    super.key,
    required this.title,
    this.maxLines = 1,
    this.textAlign = TextAlign.left,
  });

  final String title;
  final int maxLines;
  final TextAlign? textAlign;

  @override
  Widget build(BuildContext context) {
    return Text(
      title,
      style: Theme.of(context).textTheme.bodySmall,
      overflow: TextOverflow.ellipsis,
      maxLines: maxLines,
      textAlign: textAlign,
    );
  }
}
