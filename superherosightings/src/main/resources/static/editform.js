/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    $('.editForm').hide("1000");
    if ($('.errorDiv').length) {
        $('.editForm').show("1000");
        $('.emptyDiv').toggle("1000");
    }
    $('.editButton').on('click', function () {
        $('.editForm').toggle("1000");
        $('.emptyDiv').toggle("1000");
    })

});

