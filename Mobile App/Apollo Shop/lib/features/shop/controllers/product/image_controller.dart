import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/data/models/product/variant_model.dart';
import 'package:apolloshop/data/repositories/variant/variant_repository.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class ImageController extends GetxController {
  static ImageController get instance => Get.find();

  final VariantRepository _variantRepository = VariantRepository.instance;

  // Variables
  RxString selectedProductImage = ''.obs;
  RxList<String> variantImages = <String>[].obs;

  // Get All Images from Product and Variants
  Future<List<String>> getProductImages(ProductModel product) async {
    Set<String> images = {};
    images.add(product.thumbnail);
    selectedProductImage.value = product.thumbnail;

    // Get All Images from Variants if not null
    List<VariantModel> variants =
        await _variantRepository.getVariantsByProductId(product.id);

    images.addAll(variants.map((variant) => variant.img).toList());

    return images.toList();
  }

  // Show Image pop up
  void showEnlargeImage(String image) {
    Get.to(
      fullscreenDialog: true,
      () => Dialog.fullscreen(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(
                vertical: TSizes.defaultSpace * 2,
                horizontal: TSizes.defaultSpace,
              ),
              child: CachedNetworkImage(
                imageUrl: image,
                placeholder: (context, url) => const Center(
                  child: CircularProgressIndicator(),
                ),
                errorWidget: (context, url, error) => const Center(
                  child: Icon(Icons.error, color: Colors.red),
                ),
              ),
            ),
            const SizedBox(height: TSizes.spaceBtwSections),
            Align(
              alignment: Alignment.bottomCenter,
              child: SizedBox(
                width: 150,
                child: OutlinedButton(
                  onPressed: () => Get.back(),
                  child: const Text('Close'),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
