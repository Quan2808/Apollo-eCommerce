import 'package:apolloshop/data/models/order/order_model.dart';
import 'package:apolloshop/data/services/order/order_service.dart';
import 'package:apolloshop/features/personalization/controllers/user/user_controller.dart';
import 'package:get/get.dart';

class OrderRepository extends GetxController {
  static OrderRepository get instance => Get.find();

  final OrderService _orderService = Get.find();
  final RxList<OrderModel> _userOrders = <OrderModel>[].obs;

  List<OrderModel> get userOrders => _userOrders;

  Future<void> fetchUserOrders() async {
    try {
      final userId = UserController.instance.user.value!.id.toString();
      final orders = await _orderService.getOrders(userId);
      _userOrders.assignAll(orders);
    } catch (e) {
      rethrow;
    }
  }
}
