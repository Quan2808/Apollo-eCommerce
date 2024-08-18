import 'package:apolloshop/features/shop/screens/product_previews/widgets/progress_indicator_and_rating.dart';
import 'package:flutter/material.dart';

class OverallProductRating extends StatelessWidget {
  const OverallProductRating({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context).textTheme;
    return Row(
      children: [
        Expanded(
          flex: 3,
          child: Text(
            '4.5',
            style: theme.displayLarge,
          ),
        ),
        const Expanded(
          flex: 7,
          child: Column(
            children: [
              RatingProgressIndicator(text: '5', value: 1.0),
              RatingProgressIndicator(text: '4', value: 0.8),
              RatingProgressIndicator(text: '3', value: 0.6),
              RatingProgressIndicator(text: '2', value: 0.4),
              RatingProgressIndicator(text: '1', value: 0.2),
            ],
          ),
        )
      ],
    );
  }
}
