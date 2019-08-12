$(function () {

    $('.filter-form').validate();


    const $email = $('#email');
    const $password = $('#password');
    const $loginBtn = $('#login-button');

    $loginBtn.on('click', function () {

        if ($('.filter-form').valid()) {
            let user = {email: $email.val(), password: $password.val()};

            $.ajax({
                type: 'POST',
                url: 'http://localhost:8089/lib/login',
                data: JSON.stringify(user),
                dataType: "json",
                contentType: "application/json",
                success: function (userInfo) {
                    localStorage.setItem('token', 'Bearer_' + userInfo.token);
                    document.location.href = 'file:///D:/1PROGA/libraryapp/src/main/resources/' + userInfo.role.toLowerCase() + "/books.html";
                },
                error: function () {
                    alert('error');
                }
            });
        }


    });
});
