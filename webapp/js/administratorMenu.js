$(document).ready(function(){

    $('#addBook').on("click",function(){
        $('#addBookForm').submit();
    }).hover(function () {
        $('#addBookLabel').toggle();
    });

    $('#addLibrarian').on("click",function(){
        $('#addLibrarianForm').submit();
    }).hover(function () {
        $('#addLibrarianLabel').toggle();
    });

    $('#editAccount').on("click",function(){
        $('#editAccountForm').submit();
    }).hover(function () {
        $('#editAccountLabel').toggle();
    });

    $('#deleteBookMenu').on("click",function(){
        $('#deleteBookMenuForm').submit();
    }).hover(function () {
        $('#deleteBookMenuLabel').toggle();
    });

    $('#deleteLibrarianMenu').on("click",function(){
        $('#deleteLibrarianMenuForm').submit();
    }).hover(function () {
        $('#deleteLibrarianMenuLabel').toggle();
    });

    $('#deleteMemberMenu').on("click",function(){
        $('#deleteMemberMenuForm').submit();
    }).hover(function () {
        $('#deleteMemberMenuLabel').toggle();
    });

    $("#menuImages").find("> div").hover(function() {
        $('#titleBlock').toggle();
    });

});
