define(["jquery"], function ($) {


    $("#editAccountForm").submit(function() {

        var password = $('#passwordUser').val();
        var repeatPassword = $('#repeatPasswordUser').val();

        if(password !== repeatPassword){
            alert("Пароли не совпадают!");
            return false;
        }
    });

});