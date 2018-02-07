$(document).ready(function(){

    $('#orderBook').on("click",function(){
        $('#orderBookForm').submit();
    }).hover(function(){
        $('#orderBookLabel').toggle();
    });

    $('#viewOrderedBooks').on("click",function(){
        $('#viewOrderedBooksForm').submit();
    }).hover(function(){
        $('#viewOrderedBooksLabel').toggle();
    });

    $('#editAccount').on("click",function(){
        $('#editAccountForm').submit();
    }).hover(function(){
        $('#editAccountLabel').toggle();
    });

    $('#authorGallery').on("click",function(){
        $('#authorGalleryForm').submit();
    }).hover(function(){
        $('#authorGalleryLabel').toggle();
    });

    $('#trainingVideo').on("click",function(){
        $('#trainingVideoForm').submit();
    }).hover(function(){
        $('#trainingVideoLabel').toggle();
    });

    $('#deleteAccount').on("click",function(){
        $('#deleteAccountForm').submit();
    }).hover(function(){
        $('#deleteAccountLabel').toggle();
    });

    $("#menuImages").find("> div").hover(function() {
        $('#titleBlock').toggle();
    });

});

