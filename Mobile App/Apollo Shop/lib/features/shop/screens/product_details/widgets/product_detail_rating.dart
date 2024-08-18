import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:iconsax/iconsax.dart';

class ProductDetailRating extends StatelessWidget {
  const ProductDetailRating({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Row(
          children: [
            const Icon(
              Iconsax.star5,
              color: Colors.amber,
              size: 24,
            ),
            const SizedBox(width: TSizes.spaceBtwItems / 2),
            Text.rich(
              TextSpan(
                children: [
                  TextSpan(
                    text: '4.5',
                    style: Theme.of(context).textTheme.bodyLarge,
                  ),
                  const TextSpan(text: '(5)'),
                ],
              ),
            ),
          ],
        )
      ],
    );
  }
}
