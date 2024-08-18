import 'package:apollodeliver/Models/Admin.dart';
import 'package:apollodeliver/Models/Review.dart';

class Comment {
  int? id;
  String? content;
  Review? review;
  Admin? admin;

  Comment({this.id, this.content, this.review, this.admin});

  factory Comment.fromJson(Map<String, dynamic> json) {
    return Comment(
      id: json['id'],
      content: json['content'],
      review: json['review'] != null ? Review.fromJson(json['review']) : null,
      admin: json['admin'] != null ? Admin.fromJson(json['admin']) : null,
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'content': content,
    'review': review?.toJson(),
    'admin': admin?.toJson(),
  };
}