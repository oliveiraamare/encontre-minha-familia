$.validator.setDefaults({
    errorElement: "div",
    errorPlacement: function (error, element) {

        // Add the `help-block` class to the error element
        error.addClass("help-block invalid-feedback d-block");

        if (element.prop("type") === "checkbox") {
            error.insertAfter(element.parent("label"));
        } else {
            element.parents('.form-group').append(error);
        }

    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass("is-invalid");
    },
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass("is-invalid");
    }
});