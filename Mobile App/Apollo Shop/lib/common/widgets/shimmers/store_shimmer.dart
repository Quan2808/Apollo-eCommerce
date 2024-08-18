import 'package:apolloshop/common/widgets/layouts/grid_layout.dart';
import 'package:apolloshop/common/widgets/shimmers/shimmer_effect.dart';
import 'package:flutter/material.dart';

class StoreShimmer extends StatelessWidget {
  const StoreShimmer({
    super.key,
    this.itemCount = 4,
  });

  final int itemCount;

  @override
  Widget build(BuildContext context) {
    return GridLayout(
      mainAxisExtent: 80,
      itemCount: itemCount,
      itemBuilder: (_, index) => const ShimmerEffect(
        width: 300,
        height: 80,
      ),
    );
  }
}
