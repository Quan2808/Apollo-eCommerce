import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/features/shop/controllers/product/variant_controller.dart';
import 'package:apolloshop/features/shop/screens/product_details/widgets/bottom_add_to_cart.dart';
import 'package:apolloshop/features/shop/screens/product_details/widgets/product_attributes.dart';
import 'package:apolloshop/features/shop/screens/product_details/widgets/product_detail_image_slider.dart';
import 'package:apolloshop/features/shop/screens/product_details/widgets/product_meta_data.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:readmore/readmore.dart';

class ProductDetailScreen extends StatelessWidget {
  const ProductDetailScreen({
    super.key,
    required this.product,
  });

  final ProductModel product;

  @override
  Widget build(BuildContext context) {
    final variantController = Get.put(VariantController());
    WidgetsBinding.instance.addPostFrameCallback((_) {
      if (variantController.variants.isEmpty) {
        variantController.fetchVariantsByProduct(product.id);
      }
    });
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          children: [
            /// Product Image Slider
            ProductImageSlider(product: product),

            /// Product Details
            Padding(
              padding: const EdgeInsets.only(
                  right: TSizes.defaultSpace,
                  left: TSizes.defaultSpace,
                  bottom: TSizes.defaultSpace),
              child: Column(
                children: [
                  /// Price, Title Stock & Stores
                  ProductMetaData(product: product),
                  const SizedBox(height: TSizes.spaceBtwItems),

                  /// Attribute
                  ProductAttributes(product: product),
                  const SizedBox(height: TSizes.spaceBtwSections),

                  /// Description
                  const SectionHeading(
                      title: 'Description', showActionButton: false),
                  const SizedBox(height: TSizes.spaceBtwItems),
                  ReadMoreText(
                    product.description,
                    trimLines: 2,
                    trimMode: TrimMode.Line,
                    trimCollapsedText: '\nShow more',
                    trimExpandedText: '\nShow less',
                    moreStyle: const TextStyle(
                        fontSize: 14, fontWeight: FontWeight.w800),
                    lessStyle: const TextStyle(
                        fontSize: 14, fontWeight: FontWeight.w800),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
      bottomNavigationBar: Obx(
        () {
          if (variantController.variants.isEmpty) {
            return const SizedBox.shrink();
          }
          return BottomAddToCart(product: product);
        },
      ),
    );
  }
}
