$(document).ready(function(){

    $('#readingRoomBookDelivery').on("click",function(){
        $('#readingRoomBookDeliveryForm').submit();
    }).hover(function () {
        $('#readingRoomBookDeliveryLabel').toggle();
    });

    $('#subscriptionBookDelivery').on("click",function(){
        $('#subscriptionBookDeliveryForm').submit();
    }).hover(function () {
        $('#subscriptionBookDeliveryLabel').toggle();
    });

    $('#addAuthor').on("click",function(){
        $('#addAuthorForm').submit();
    }).hover(function () {
        $('#addAuthorLabel').toggle();
    });

    $('#editBook').on("click",function(){
        $('#editBookForm').submit();
    }).hover(function () {
        $('#editBookLabel').toggle();
    });

    $('#editAccount').on("click",function(){
        $('#editAccountForm').submit();
    }).hover(function () {
        $('#editAccountLabel').toggle();
    });

    $('#deleteAccount').hover(function () {
        $('#deleteAccountLabel').toggle();
    });

    $("#menuImages").find("> div").hover(function() {
        $('#titleBlock').toggle();
    });

});