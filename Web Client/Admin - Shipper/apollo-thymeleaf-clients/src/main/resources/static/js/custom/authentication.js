"use strict";
var KTSigninGeneral = (function () {
    var t, e, r;
    return {
        init: function () {
            (t = document.querySelector("#kt_sign_in_form")),
                (e = document.querySelector("#kt_sign_in_submit")),
                (r = FormValidation.formValidation(t, {
                    fields: {
                        email: {
                            validators: {
                                regexp: {
                                    regexp: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                                    message: "The value is not a valid email address",
                                },
                                notEmpty: { message: "Email address is required" },
                            },
                        },
                        password: {
                            validators: { notEmpty: { message: "The password is required" } },
                        },
                    },
                    plugins: {
                        trigger: new FormValidation.plugins.Trigger(),
                        bootstrap: new FormValidation.plugins.Bootstrap5({
                            rowSelector: ".fv-row",
                            eleInvalidClass: "",
                            eleValidClass: "",
                        }),
                    },
                })),
                e.addEventListener("click", function (i) {
                    i.preventDefault();
                    r.validate().then(function (r) {
                        if (r === "Valid") {
                            e.setAttribute("data-kt-indicator", "on");
                            e.disabled = true;

                            // Sử dụng setTimeout để mô phỏng độ trễ của mạng
                            setTimeout(function () {
                                e.removeAttribute("data-kt-indicator");
                                e.disabled = false;

                                // Hiển thị thông báo thành công
                                Swal.fire({
                                    text: "Form is valid! Submitting...",
                                    icon: "success",
                                    buttonsStyling: false,
                                    confirmButtonText: "Ok, got it!",
                                    customClass: { confirmButton: "btn btn-primary" },
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        // Gửi form
                                        t.submit();
                                    }
                                });
                            }, 2000);
                        } else {
                            Swal.fire({
                                text: "Sorry, looks like there are some errors detected, please try again.",
                                icon: "error",
                                buttonsStyling: false,
                                confirmButtonText: "Ok, got it!",
                                customClass: { confirmButton: "btn btn-primary" },
                            });
                        }
                    });
                });
        },
    };
})();

KTUtil.onDOMContentLoaded(function () {
    KTSigninGeneral.init();
});