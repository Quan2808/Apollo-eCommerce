import 'package:apolloshop/common/widgets/layouts/grid_layout.dart';
import 'package:apolloshop/common/widgets/shimmers/shimmer_effect.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';

class VerticalProductShimmer extends StatelessWidget {
  const VerticalProductShimmer({
    super.key,
    this.itemCount = 4,
  });

  final int itemCount;

  @override
  Widget build(BuildContext context) {
    return GridLayout(
      itemCount: 4,
      itemBuilder: (_, __) => const SizedBox(
        width: 180,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Image
            ShimmerEffect(width: 180, height: 180),
            SizedBox(height: TSizes.spaceBtwItems),

            // Text
            ShimmerEffect(width: 160, height: 15),
            SizedBox(height: TSizes.spaceBtwItems / 2),
            ShimmerEffect(width: 110, height: 15),
          ],
        ),
      ),
    );
  }
}
