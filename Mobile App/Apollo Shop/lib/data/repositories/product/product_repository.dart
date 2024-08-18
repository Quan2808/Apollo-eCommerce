import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/data/services/product/product_service.dart';
import 'package:get/get.dart';

class ProductRepository extends GetxService {
  static ProductRepository get instance => Get.find();

  final ProductService _service = Get.find<ProductService>();

  Future<List<ProductModel>> getProducts() async {
    final categoriesData = await _service.getProducts();
    return categoriesData.map((data) => ProductModel.fromJson(data)).toList();
  }

  Future<List<ProductModel>> getProductsByCategory(int categoryId) async {
    final productsData = await _service.getProducts();
    return productsData
        .where((data) => data['categoryId'] == categoryId)
        .map((data) => ProductModel.fromJson(data))
        .toList();
  }

  Future<ProductModel?> getProductById(int productId) async {
    final products = await getProducts();
    return products.firstWhereOrNull((product) => product.id == productId);
  }
}
