import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/products/ratings/product_rating_indicator.dart';
import 'package:apolloshop/features/shop/screens/product_previews/widgets/rating_progress_indicator.dart';
import 'package:apolloshop/features/shop/screens/product_previews/widgets/user_review_card.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';

class ProductPreviewsScreen extends StatelessWidget {
  const ProductPreviewsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      /// App bar
      appBar: const ApolloAppBar(
        title: Text('Previews & Ratings'),
        showBackArrow: true,
      ),

      /// Body
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(TSizes.defaultSpace),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text(
                  'Ratings and reviews are verified and are from people who use the same type of device that you use.'),
              const SizedBox(height: TSizes.spaceBtwItems),

              /// Overall Product Ratings
              const OverallProductRating(),
              const ProductRatingIndicator(rating: 4.5),
              Text('12,611', style: Theme.of(context).textTheme.bodySmall),
              const SizedBox(height: TSizes.spaceBtwSections),

              /// User Reviews List
              const UserReviewCard(
                userName: 'User 1',
                rating: 4.0,
                date: '29 Jul 2024',
                reviewText:
                    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
              ),
              const UserReviewCard(
                userName: 'User 2',
                rating: 3.5,
                date: '28 Jul 2024',
                reviewText:
                    'Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
              ),
            ],
          ),
        ),
      ),
    );
  }
}
