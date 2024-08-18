import 'package:apolloshop/data/models/store/store_model.dart';
import 'package:apolloshop/data/services/store/store_service.dart';
import 'package:get/get.dart';

class StoreRepository extends GetxService {
  static StoreRepository get instance => Get.find();

  final StoreService _service = Get.find<StoreService>();

  Future<List<StoreModel>> getStores() async {
    final categoriesData = await _service.getProducts();
    return categoriesData.map((data) => StoreModel.fromJson(data)).toList();
  }
}
