import 'package:apolloshop/common/widgets/loaders/loaders.dart';
import 'package:apolloshop/common/widgets/success_screen/success_screen.dart';
import 'package:apolloshop/data/models/order/order_model.dart';
import 'package:apolloshop/data/repositories/order/order_repository.dart';
import 'package:apolloshop/data/request/order/order_request.dart';
import 'package:apolloshop/data/response/order/order_response.dart';
import 'package:apolloshop/data/services/order/order_service.dart';
import 'package:apolloshop/features/personalization/controllers/address/address_controller.dart';
import 'package:apolloshop/features/personalization/controllers/payment_method/payment_method_controller.dart';
import 'package:apolloshop/features/personalization/controllers/user/user_controller.dart';
import 'package:apolloshop/features/shop/controllers/cart/cart_controller.dart';
import 'package:apolloshop/features/shop/controllers/payment_shipping/shipping_method_controller.dart';
import 'package:apolloshop/navigation_menu.dart';
import 'package:apolloshop/utils/constants/image_strings.dart';
import 'package:apolloshop/utils/popups/full_screen_loader.dart';
import 'package:get/get.dart';

class OrderController extends GetxController {
  static OrderController get instance => Get.find();

  final _orderRepo = OrderRepository.instance;
  final _cartController = CartController.instance;
  final _addressController = AddressController.instance;

  final getPaymentMethodController = Get.put(PaymentMethodController());
  final getShippingMethodController = Get.put(ShippingMethodController());

  final _paymentMethodController = PaymentMethodController.instance;
  final _shippingMethodController = ShippingMethodController.instance;
  final _userController = UserController.instance;

  final _orderService = OrderService.instance;

  Future<List<OrderModel>> fetchUserOrders() async {
    try {
      await _orderRepo.fetchUserOrders();
      return _orderRepo.userOrders;
    } catch (e) {
      Loaders.errorSnackBar(title: 'Oh Snap!', message: e.toString());
      return [];
    }
  }

  Future<void> processOrder() async {
    try {
      TFullScreenLoader.openLoadingDialog(
          'Processing your order', TImages.processOrderAnimation);

      final user = _userController.user.value;
      if (user == null) throw Exception('User not logged in');

      final address = _addressController.selectedAddress.value;

      final paymentMethod =
          _paymentMethodController.selectedPaymentMethod.value;

      final shippingMethod =
          _shippingMethodController.selectedShippingMethod.value;

      // Create a list of OrderRequest objects, one for each cart item
      final List<OrderRequest> orderRequests =
          _cartController.cartItems.map((item) {
        return OrderRequest(
          userId: user.id,
          variant: item.variant!.id.toString(),
          orderDate: DateTime.now().toIso8601String(),
          addressId: address.id,
          paymentMethodId: paymentMethod.id,
          shippingMethodId: shippingMethod.id,
          quantity: item.quantity,
        );
      }).toList();

      final List<OrderResponse> responses =
          await _orderService.createOrder(orderRequests);

      if (responses.isNotEmpty) {
        TFullScreenLoader.stopLoading();
        _cartController.clearCart();
        Get.off(
          () => SuccessScreen(
            image: TImages.successfulPaymentIcon,
            title: 'Payment success',
            subTitle: 'Your item will be shipped soon.',
            onPressed: () => Get.offAll(() => const NavigationMenu()),
          ),
        );
      }
    } catch (e) {
      TFullScreenLoader.stopLoading();
      Loaders.errorSnackBar(title: 'Error', message: e.toString());
    }
  }
}
