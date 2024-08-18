import 'package:apolloshop/common/widgets/loaders/loaders.dart';
import 'package:apolloshop/data/models/product/variant_model.dart';
import 'package:apolloshop/data/repositories/variant/variant_repository.dart';
import 'package:flutter/foundation.dart';
import 'package:get/get.dart';

class VariantController extends GetxController {
  static VariantController get instance => Get.find();

  final isLoading = false.obs;
  final VariantRepository _variantRepository = Get.find();
  RxList<VariantModel> variants = <VariantModel>[].obs;
  Rx<VariantModel?> selectedVariant = Rx<VariantModel?>(null);

  Future<void> fetchVariantsByProduct(int productId) async {
    try {
      isLoading.value = true;

      // Fetch Variants
      final getVariants =
          await _variantRepository.getVariantsByProductId(productId);

      // Assign fetched Variants to the observable list
      variants.assignAll(
        getVariants.where((e) => e.img.isNotEmpty).toList(),
      );

      // Assign Selected Variant if variants exist
      if (variants.isNotEmpty) {
        _setSelectedVariant(productId);
      } else {
        resetSelectedVariant();
      }
    } catch (e) {
      if (kDebugMode) {
        print("==================== Variants Error ====================");
        print(e.toString());
        print("========================================================");
      }
      Loaders.errorSnackBar(title: 'Oh Snap!', message: e.toString());
    } finally {
      isLoading.value = false;
    }
  }

  void _setSelectedVariant(int productId) {
    selectedVariant.value = variants.firstWhere(
      (variant) => variant.product?.id == productId,
      orElse: () => variants.first,
    );
  }

  void resetSelectedVariant() {
    selectedVariant.value = null;
  }
}
