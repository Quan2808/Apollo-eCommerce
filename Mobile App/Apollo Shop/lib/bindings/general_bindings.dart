import 'package:apolloshop/data/repositories/address/address_repository.dart';
import 'package:apolloshop/data/repositories/cart/cart_repository.dart';
import 'package:apolloshop/data/repositories/category/category_repository.dart';
import 'package:apolloshop/data/repositories/order/order_repository.dart';
import 'package:apolloshop/data/repositories/payment_method/payment_method_repository.dart';
import 'package:apolloshop/data/repositories/product/product_repository.dart';
import 'package:apolloshop/data/repositories/shipping_method/shipping_method_repository.dart';
import 'package:apolloshop/data/repositories/store/store_repository.dart';
import 'package:apolloshop/data/repositories/variant/variant_repository.dart';
import 'package:apolloshop/data/services/address/address_service.dart';
import 'package:apolloshop/data/services/cart/cart_service.dart';
import 'package:apolloshop/data/services/category/category_service.dart';
import 'package:apolloshop/data/services/order/order_service.dart';
import 'package:apolloshop/data/services/payment_method/payment_method_service.dart';
import 'package:apolloshop/data/services/product/product_service.dart';
import 'package:apolloshop/data/services/shipping_method/shipping_method_service.dart';
import 'package:apolloshop/data/services/store/store_service.dart';
import 'package:apolloshop/data/services/variant/variant_service.dart';
import 'package:apolloshop/features/personalization/controllers/address/address_controller.dart';
import 'package:apolloshop/features/shop/controllers/cart/cart_controller.dart';
import 'package:apolloshop/features/shop/controllers/product/variant_controller.dart';
import 'package:apolloshop/utils/helpers/network_manager.dart';
import 'package:get/get.dart';

/// The `GeneralBindings` class is responsible for setting up dependency injection
/// for various services and repositories using the GetX package.
class GeneralBindings extends Bindings {
  @override
  void dependencies() {
    // Register NetworkManager for handling network operations.
    Get.put(NetworkManager());

    // Register ProductService and its corresponding repository.
    Get.put(ProductService());
    Get.put(ProductRepository());

    // Register VariantService and its corresponding repository.
    Get.put(VariantService());
    Get.put(VariantRepository());
    Get.put(VariantController());

    // Register CategoryService and its corresponding repository.
    Get.put(CategoryService());
    Get.put(CategoryRepository());

    // Register StoreService and its corresponding repository.
    Get.put(StoreService());
    Get.put(StoreRepository());

    // Register CartService and its corresponding repository.
    Get.put(CartService());
    Get.put(CartRepository());
    Get.put(CartController());

    // Register AddressService and its corresponding repository.
    Get.put(AddressService());
    Get.put(AddressRepository());
    Get.put(AddressController());

    // Register PaymentMethodService and its corresponding repository.
    Get.put(PaymentMethodService());
    Get.put(PaymentMethodRepository());

    // Register ShippingMethodService and its corresponding repository.
    Get.put(ShippingMethodService());
    Get.put(ShippingMethodRepository());

    // Register OrderService and its corresponding repository.
    Get.put(OrderService());
    Get.put(OrderRepository());
  }
}
