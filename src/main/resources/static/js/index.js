$(window).ready(function () {
    $('.web-explanation div').hide();
    $('.example-01 div').hide();
    $('.example-02 div').hide();
    $('.example-03 div').hide();
})
$(window).load(function () {
    $('.web-explanation div').fadeIn('slow', function() {
        $('.example-01 div').fadeIn('slow', function() {
            $('.example-02 div').fadeIn('slow', function() {
                $('.example-03 div').fadeIn();
            });
        });
    });
})