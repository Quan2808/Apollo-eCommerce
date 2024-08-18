import 'package:apolloshop/data/models/product/variant_model.dart';
import 'package:apolloshop/data/services/variant/variant_service.dart';
import 'package:get/get.dart';

class VariantRepository extends GetxService {
  static VariantRepository get instance => Get.find();

  final VariantService _service = Get.find<VariantService>();

  Future<List<VariantModel>> getVariantsByProductId(int productId) async {
    final variantsData = await _service.getVariantsByProductId(productId);
    return variantsData.map((data) => VariantModel.fromJson(data)).toList();
  }
}
