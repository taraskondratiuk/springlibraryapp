$(function () {

    $('.reg-form').validate();

    const $email = $('#email');
    const $password = $('#password');
    const $phone = $('#phone-num');
    const $phoneCode = $('#phone-code');
    const $regBtn = $('#reg-button');

    $regBtn.on('click', function () {
        if ($('.reg-form').valid()) {
            let user = {
                email: $email.val(),
                password: $password.val(),
                phoneNumber: $phone.val(),
                $phoneCode: $phoneCode.val()
            };
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8089/lib/register',
                data: JSON.stringify(user),
                dataType: "json",
                contentType: "application/json",
                success: function (newUser) {
                    alert("reg");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('error');

                }
            });
        }



    });
});