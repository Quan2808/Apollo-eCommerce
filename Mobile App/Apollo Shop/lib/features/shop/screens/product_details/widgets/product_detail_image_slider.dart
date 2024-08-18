import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/custom_shapes/curved_edges/curved_edges_widget.dart';
import 'package:apolloshop/common/widgets/images/rounded_image.dart';
import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/features/shop/controllers/product/image_controller.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class ProductImageSlider extends StatelessWidget {
  const ProductImageSlider({
    super.key,
    required this.product,
  });

  final ProductModel product;

  @override
  Widget build(BuildContext context) {
    final dark = THelperFunctions.isDarkMode(context);

    final controller = Get.put(ImageController());

    return FutureBuilder<List<String>>(
      future: controller.getProductImages(product),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        } else if (snapshot.hasData) {
          final images = snapshot.data!;

          return CurvedEdgeWidget(
            child: Container(
              color: dark ? TColors.darkerGrey : TColors.light,
              child: Stack(
                children: [
                  /// Main Large Image
                  SizedBox(
                    height: 400,
                    child: Padding(
                      padding:
                          const EdgeInsets.all(TSizes.productImageRadius * 2),
                      child: Center(
                        child: Obx(() {
                          final image = controller.selectedProductImage.value;
                          return GestureDetector(
                            onTap: () => controller.showEnlargeImage(image),
                            child: CachedNetworkImage(
                              imageUrl: image,
                              progressIndicatorBuilder:
                                  (context, url, progress) =>
                                      CircularProgressIndicator(
                                value: progress.progress,
                                color: TColors.primary,
                              ),
                              errorWidget: (context, url, error) =>
                                  const Icon(Icons.error, color: TColors.error),
                            ),
                          );
                        }),
                      ),
                    ),
                  ),

                  /// Image Slider
                  Positioned(
                    right: 0,
                    bottom: 30,
                    left: TSizes.defaultSpace,
                    child: SizedBox(
                      height: 80,
                      child: ListView.separated(
                          itemCount: images.length,
                          shrinkWrap: true,
                          scrollDirection: Axis.horizontal,
                          physics: const AlwaysScrollableScrollPhysics(),
                          separatorBuilder: (_, __) => const SizedBox(
                                width: TSizes.spaceBtwItems,
                              ),
                          itemBuilder: (_, index) => Obx(
                                () {
                                  final image = images[index];
                                  final isSelected =
                                      controller.selectedProductImage.value ==
                                          image;

                                  return RoundedImage(
                                    width: 80,
                                    backgroundColor:
                                        dark ? TColors.black : TColors.white,
                                    border: Border.all(
                                        color: isSelected
                                            ? TColors.primary
                                            : Colors.transparent),
                                    padding: const EdgeInsets.all(TSizes.sm),
                                    isNetworkImage: true,
                                    imageUrl: image,
                                    onPressed: () => controller
                                        .selectedProductImage.value = image,
                                  );
                                },
                              )),
                    ),
                  ),

                  const ApolloAppBar(showBackArrow: true),
                ],
              ),
            ),
          );
        } else {
          return const Center(child: Text('No images available'));
        }
      },
    );
  }
}
