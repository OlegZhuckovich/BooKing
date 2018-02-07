define(["jquery"],function ($) {

    $("#searchBookByCriteriaForm").submit(function() {
        if( !$('#searchField').val() ) {
            alert("Search Value is empty");
            return false;
        }
    });

    $("#registerForm").submit(function() {
        var name = $('#nameRegister').val();
        var surname = $('#surnameRegister').val();
        var email = $('#emailRegister').val();
        var password = $('#passwordRegister').val();
        var repeatPassword = $('#repeatPasswordRegister').val();

        var nameSurnameRegex = new RegExp("[A-ZА-Я][a-zа-я]+-?[A-ZА-Я]?[a-zа-я]+?");
        var emailRegex = new RegExp("[\\w\\.]{2,40}@[a-z]{2,10}\\.[a-z]{2,10}");
        var passwordRegex = new RegExp("[\\w]{5,40}");

        if (nameSurnameRegex.test(name) && nameSurnameRegex.test(surname)) {
            if(emailRegex.test(email)){
                if(passwordRegex.test(password) && password.localeCompare(repeatPassword)){
                    return true;
                } else {
                    alert("Пароли");
                    return false;
                }
            } else {
                alert(email + " Email");
                return false;
            }
        } else {
            $("#nameRegister").fadeOut(3000).fadeIn(3000).fadeOut(3000).fadeIn(3000);
            alert("Имя Фамилия");
            return false;
        }
    });

})
