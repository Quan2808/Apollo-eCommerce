import 'dart:core';

import 'package:apolloshop/common/widgets/custom_shapes/containers/primary_header_container.dart';
import 'package:apolloshop/common/widgets/layouts/grid_layout.dart';
import 'package:apolloshop/common/widgets/products/product_cards/product_card_vertical.dart';
import 'package:apolloshop/common/widgets/shimmers/vertical_product_shimmer.dart';
import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/features/shop/controllers/product/product_controller.dart';
import 'package:apolloshop/features/shop/screens/all_products/all_products.dart';
import 'package:apolloshop/features/shop/screens/home/widgets/home_appbar.dart';
import 'package:apolloshop/features/shop/screens/home/widgets/home_categories.dart';
import 'package:apolloshop/features/shop/screens/home/widgets/promo_slider.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/image_strings.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(ProductController());

    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          children: [
            /// Header
            const PrimaryHeaderContainer(
              child: Column(
                children: [
                  /// App bar
                  HomeAppBar(),
                  SizedBox(height: TSizes.spaceBtwSections),

                  /// Categories
                  Padding(
                    padding: EdgeInsets.only(left: TSizes.defaultSpace),
                    child: Column(
                      children: [
                        /// Heading
                        SectionHeading(
                          title: "Popular Categories",
                          showActionButton: false,
                          textColor: TColors.white,
                        ),
                        SizedBox(height: TSizes.spaceBtwItems),

                        /// Categories
                        HomeCategories(),
                      ],
                    ),
                  ),
                  SizedBox(height: TSizes.spaceBtwSections),
                ],
              ),
            ),

            /// Body
            Padding(
              padding: const EdgeInsets.all(TSizes.defaultSpace),
              child: Column(
                children: [
                  const PromoSlider(
                    banners: [TImages.promoBanner1, TImages.promoBanner2],
                  ),
                  const SizedBox(height: TSizes.spaceBtwSections),
                  SectionHeading(
                    title: 'Popular Products',
                    onPressed: () => Get.to(
                      () => const AllProductsScreen(title: 'All Products'),
                    ),
                  ),
                  const SizedBox(height: TSizes.spaceBtwItems / 1.5),
                  Obx(
                    () {
                      if (controller.isLoading.value) {
                        return const VerticalProductShimmer();
                      }
                      if (controller.products.isEmpty) {
                        return Center(
                          child: Text(
                            'No Data Found!',
                            style: Theme.of(context)
                                .textTheme
                                .bodyMedium!
                                .apply(color: Colors.white),
                          ),
                        );
                      }
                      return GridLayout(
                        mainAxisExtent: 271,
                        itemCount: 6,
                        itemBuilder: (_, index) => ProductCardVertical(
                          product: controller.products[index],
                        ),
                      );
                    },
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
