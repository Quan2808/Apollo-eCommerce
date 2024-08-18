document.addEventListener("DOMContentLoaded", function () {
  var body = document.getElementById("kt_app_body");
  var bodyAttributes = {
    "data-kt-app-header-fixed": "true",
    "data-kt-app-header-fixed-mobile": "true",
    "data-kt-app-sidebar-enabled": "true",
    "data-kt-app-sidebar-fixed": "true",
    "data-kt-app-sidebar-hoverable": "true",
    "data-kt-app-sidebar-push-toolbar": "true",
    "data-kt-app-sidebar-push-footer": "true",
    "data-kt-app-toolbar-enabled": "true",
    "data-kt-app-aside-enabled": "true",
    "data-kt-app-aside-fixed": "true",
    "data-kt-app-aside-push-toolbar": "true",
    "data-kt-app-aside-push-footer": "true",
  };
  for (var key in bodyAttributes) {
    body.setAttribute(key, bodyAttributes[key]);
  }
});

document.addEventListener("DOMContentLoaded", function () {
  var symbols = document.querySelectorAll('.symbol');

  symbols.forEach(function (symbol) {
    var thumbnailData = symbol.getAttribute('thumbnail-data');
    if (thumbnailData) {
      var symbolLabel = symbol.querySelector('.symbol-label');
      if (symbolLabel) {
        symbolLabel.style.backgroundImage = 'url(' + thumbnailData + ')';
      }
    }
  });
});