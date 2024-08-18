"use strict";

var KTModalCustomersAdd = function () {
    var modal, form, submitButton, cancelButton, closeButton;

    var initValidation = function () {
        var formValidation = FormValidation.formValidation(form, {
            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: 'Name is required'
                        }
                    }
                },
                email: {
                    validators: {
                        notEmpty: {
                            message: 'Email is required'
                        },
                        emailAddress: {
                            message: 'The value is not a valid email address'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: 'Password is required'
                        }
                    }
                }
            },
            plugins: {
                trigger: new FormValidation.plugins.Trigger(),
                bootstrap: new FormValidation.plugins.Bootstrap5({
                    rowSelector: '.fv-row',
                    eleInvalidClass: '',
                    eleValidClass: ''
                })
            }
        });

        submitButton.addEventListener('click', function (e) {
            e.preventDefault();
            formValidation.validate().then(function (isValid) {
                if (isValid === 'Valid') {
                    submitButton.setAttribute('data-kt-indicator', 'on');
                    submitButton.disabled = true;

                    // Submit the form
                    form.submit();
                } else {
                    Swal.fire({
                        text: 'Sorry, looks like there are some errors detected, please try again.',
                        icon: 'error',
                        buttonsStyling: false,
                        confirmButtonText: 'Ok, got it!',
                        customClass: { confirmButton: 'btn btn-primary' }
                    });
                }
            });
        });

        cancelButton.addEventListener('click', function (e) {
            e.preventDefault();
            Swal.fire({
                text: 'Are you sure you would like to cancel?',
                icon: 'warning',
                showCancelButton: true,
                buttonsStyling: false,
                confirmButtonText: 'Yes, cancel it!',
                cancelButtonText: 'No, return',
                customClass: { confirmButton: 'btn btn-primary', cancelButton: 'btn btn-active-light' }
            }).then(function (result) {
                if (result.value) {
                    form.reset();
                    modal.hide();
                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    Swal.fire({
                        text: 'Your form has not been cancelled!',
                        icon: 'error',
                        buttonsStyling: false,
                        confirmButtonText: 'Ok, got it!',
                        customClass: { confirmButton: 'btn btn-primary' }
                    });
                }
            });
        });

        closeButton.addEventListener('click', function (e) {
            e.preventDefault();
            Swal.fire({
                text: 'Are you sure you would like to cancel?',
                icon: 'warning',
                showCancelButton: true,
                buttonsStyling: false,
                confirmButtonText: 'Yes, cancel it!',
                cancelButtonText: 'No, return',
                customClass: { confirmButton: 'btn btn-primary', cancelButton: 'btn btn-active-light' }
            }).then(function (result) {
                if (result.value) {
                    form.reset();
                    modal.hide();
                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    Swal.fire({
                        text: 'Your form has not been cancelled!',
                        icon: 'error',
                        buttonsStyling: false,
                        confirmButtonText: 'Ok, got it!',
                        customClass: { confirmButton: 'btn btn-primary' }
                    });
                }
            });
        });
    };

    return {
        init: function () {
            modal = new bootstrap.Modal(document.getElementById('kt_modal_add_customer'));
            form = document.getElementById('kt_modal_add_customer_form');
            submitButton = document.getElementById('kt_modal_add_customer_submit');
            cancelButton = document.getElementById('kt_modal_add_customer_cancel');
            closeButton = document.getElementById('kt_modal_add_customer_close');

            initValidation();
        }
    };
}();

KTUtil.onDOMContentLoaded(function () {
    KTModalCustomersAdd.init();
});
