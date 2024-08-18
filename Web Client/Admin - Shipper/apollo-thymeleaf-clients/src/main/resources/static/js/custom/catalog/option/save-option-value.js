"use strict";
var KTvariantsAddVariant = function () {
    const t = document.getElementById("kt_modal_add_option_value"),
        e = t.querySelector("#"),
        n = new bootstrap.Modal(t);kt_modal_add_option_value_form

    return {
        init: function () {
            (() => {
                var o = FormValidation.formValidation(e, {
                    fields: {
                        name: {
                            validators: {
                                notEmpty: {message: "Name is required"}
                            }
                        },
                        skuCode: {
                            validators: {
                                notEmpty: {message: "SKU Code is required"}
                            }
                        },
                        stockQuantity: {
                            validators: {
                                notEmpty: {message: "Quantity is required"},
                                integer: {message: "Quantity must be an integer"},
                                greaterThan: {
                                    min: 0,
                                    message: "Quantity must be greater than 0"
                                }
                            }
                        },
                        weight: {
                            validators: {
                                notEmpty: {message: "Weight is required"},
                                numeric: {message: "Weight must be a number"},
                                greaterThan: {
                                    min: 0,
                                    message: "Weight must be greater than 0"
                                }
                            }
                        },
                        price: {
                            validators: {
                                notEmpty: {message: "Price is required"},
                                numeric: {message: "Price must be a number"},
                                greaterThan: {
                                    min: 0,
                                    message: "Price must be greater than 0"
                                }
                            }
                        },
                    },
                    plugins: {
                        trigger: new FormValidation.plugins.Trigger,
                        bootstrap: new FormValidation.plugins.Bootstrap5({
                            rowSelector: ".fv-row",
                            eleInvalidClass: "",
                            eleValidClass: ""
                        })
                    }
                });

                const i = t.querySelector('[data-kt-variants-modal-action="submit"]');
                i.addEventListener("click", (event) => {
                    event.preventDefault();
                    o && o.validate().then((result) => {
                        if (result === "Valid") {
                            i.setAttribute("data-kt-indicator", "on");
                            i.disabled = true;

                            const formData = new FormData(e);
                            const jsonData = {};
                            formData.forEach((value, key) => {
                                if (key !== 'imgPatch') {
                                    jsonData[key] = value;
                                }
                            });

                            fetch(e.action, {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json',
                                },
                                body: JSON.stringify(jsonData)
                            })
                                .then(response => {
                                    if (!response.ok) {
                                        throw new Error('Network response was not ok');
                                    }
                                    return response.text();
                                })
                                .then(html => {
                                    i.removeAttribute("data-kt-indicator");
                                    i.disabled = false;
                                    Swal.fire({
                                        text: "Variant has been successfully added!",
                                        icon: "success",
                                        buttonsStyling: false,
                                        confirmButtonText: "Ok, got it!",
                                        customClass: {confirmButton: "btn btn-primary"}
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            n.hide();
                                            document.body.innerHTML = html;
                                            KTUtil.onDOMContentLoaded(() => {
                                                KTvariantsAddVariant.init();
                                            });
                                        }
                                    });
                                })
                                .catch(error => {
                                    i.removeAttribute("data-kt-indicator");
                                    i.disabled = false;
                                    Swal.fire({
                                        text: "Sorry, there was an error adding the variant.",
                                        icon: "error",
                                        buttonsStyling: false,
                                        confirmButtonText: "Ok, got it!",
                                        customClass: {confirmButton: "btn btn-primary"}
                                    });
                                });
                        } else {
                            Swal.fire({
                                text: "Sorry, looks like there are some errors detected, please try again.",
                                icon: "error",
                                buttonsStyling: false,
                                confirmButtonText: "Ok, got it!",
                                customClass: {confirmButton: "btn btn-primary"}
                            });
                        }
                    });
                });

                t.querySelector('[data-kt-variants-modal-action="cancel"]').addEventListener("click", (t => {
                    t.preventDefault();
                    handleCancelAction();
                }));

                t.querySelector('[data-kt-variants-modal-action="close"]').addEventListener("click", (t => {
                    t.preventDefault();
                    handleCancelAction();
                }));

                function handleCancelAction() {
                    Swal.fire({
                        text: "Are you sure you would like to cancel?",
                        icon: "warning",
                        showCancelButton: true,
                        buttonsStyling: false,
                        confirmButtonText: "Yes, cancel it!",
                        cancelButtonText: "No, return",
                        customClass: {confirmButton: "btn btn-primary", cancelButton: "btn btn-active-light"}
                    }).then((result) => {
                        if (result.value) {
                            e.reset();
                            n.hide();
                        } else if (result.dismiss === "cancel") {
                            Swal.fire({
                                text: "Your form has not been cancelled!",
                                icon: "error",
                                buttonsStyling: false,
                                confirmButtonText: "Ok, got it!",
                                customClass: {confirmButton: "btn btn-primary"}
                            });
                        }
                    });
                }
            })();
        }
    };
}();

KTUtil.onDOMContentLoaded((function () {
    KTvariantsAddVariant.init();
}));