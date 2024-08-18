import 'package:apollodeliver/Models/Shipper.dart';
import 'package:apollodeliver/Models/ShopOrder.dart';

class OrderDelivery {
  int? id;
  ShopOrder? order;
  Shipper? shipper;
  DateTime? assignedDate;
  String? status;
  String? inducement;

  OrderDelivery({
    this.id,
    this.order,
    this.shipper,
    this.assignedDate,
    this.status,
    this.inducement,
  });

  factory OrderDelivery.fromJson(Map<String, dynamic> json) {
    return OrderDelivery(
      id: json['id'],
      order: json['order'] != null ? ShopOrder.fromJson(json['order']) : null,
      shipper: json['shipper'] != null ? Shipper.fromJson(json['shipper']) : null,
      assignedDate: json['assignedDate'] != null
          ? DateTime.fromMillisecondsSinceEpoch(json['assignedDate'])
          : null,
      status: json['status'],
      inducement: json['inducement'],
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'order': order?.toJson(),
    'shipper': shipper?.toJson(),
    'assignedDate': assignedDate?.toIso8601String(),
    'status': status,
    'inducement': inducement,
  };
}
