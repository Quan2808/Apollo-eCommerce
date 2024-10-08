"use strict";

var KTAppEcommerceProducts = (function () {
  var table;
  var datatable;

  var initDatatable = function () {
    datatable = $(table).DataTable({
      info: false,
      order: [],
      pageLength: 10,
      columnDefs: [
        { render: DataTable.render.number(",", ".", 2), targets: 4 },
        { orderable: false, targets: 0 },
        { orderable: false, targets: 7 },
      ],
    });

    datatable.on("draw", function () {
      handleDeleteRows();
    });
  };

  var handleSearchDatatable = () => {
    const filterSearch = document.querySelector(
      '[data-kt-ecommerce-product-filter="search"]'
    );
    filterSearch.addEventListener("keyup", function (e) {
      datatable.search(e.target.value).draw();
    });
  };

  var handleStatusFilter = () => {
    const filterStatus = document.querySelector(
      '[data-kt-ecommerce-product-filter="status"]'
    );
    $(filterStatus).on("change", (e) => {
      let value = e.target.value;
      if (value === "all") {
        value = "";
      }
      datatable.column(6).search(value).draw();
    });
  };

  var handleDeleteRows = () => {
    const deleteButtons = table.querySelectorAll(
      '[data-kt-ecommerce-product-filter="delete_row"]'
    );

    deleteButtons.forEach((d) => {
      d.addEventListener("click", function (e) {
        e.preventDefault();

        const parent = e.target.closest("tr");

        const productName = parent.querySelector(
          '[data-kt-ecommerce-product-filter="product_name"]'
        ).innerText;

        Swal.fire({
          text: "Are you sure you want to delete " + productName + "?",
          icon: "warning",
          showCancelButton: true,
          buttonsStyling: false,
          confirmButtonText: "Yes, delete!",
          cancelButtonText: "No, cancel",
          customClass: {
            confirmButton: "btn fw-bold btn-danger",
            cancelButton: "btn fw-bold btn-active-light-primary",
          },
        }).then(function (result) {
          if (result.value) {
            Swal.fire({
              text: "You have deleted " + productName + "!.",
              icon: "success",
              buttonsStyling: false,
              confirmButtonText: "Ok, got it!",
              customClass: {
                confirmButton: "btn fw-bold btn-primary",
              },
            }).then(function () {
              datatable.row($(parent)).remove().draw();
            });
          } else if (result.dismiss === "cancel") {
            Swal.fire({
              text: productName + " was not deleted.",
              icon: "error",
              buttonsStyling: false,
              confirmButtonText: "Ok, got it!",
              customClass: {
                confirmButton: "btn fw-bold btn-primary",
              },
            });
          }
        });
      });
    });
  };

  return {
    init: function () {
      table = document.querySelector("#kt_ecommerce_products_table");

      if (!table) {
        return;
      }

      initDatatable();
      handleSearchDatatable();
      handleStatusFilter();
      handleDeleteRows();
    },
  };
})();

KTUtil.onDOMContentLoaded(function () {
  KTAppEcommerceProducts.init();
});
