var stupidIE = true;

$(window).scroll(function () {
    if ($(document).scrollTop() > $(window).height() / 6) {
        $("header.top-header").addClass("scrolled");
    } else {
        $("header.top-header").removeClass("scrolled");
    }
    $("[id]").each(function () {
        if ($(window).scrollTop() > $(this).position().top - $(window).height() / 2.5) {
            var page = $(this).attr("id");
            $("nav a").removeClass("active");
            $('nav a[href="#' + page + '"]:first').addClass("active");
        }
    });
});
$('a[href^="#"]').click(function () {
    if (!stupidIE) {
        loadsite(this.hash);
    }
});
$(".nav-button").click(function (e) {
    $("header.top-header").toggleClass("shown");
    e.stopPropagation();
});
$(document).click(function (e) {
    $("header.top-header").removeClass("shown");
})
$(window).on("load", function () {
    if (!navigator.userAgent.includes("Trident")) {
        $("html").removeClass("stupid-ie");
        stupidIE = false;
    }
    if (window.location.hash) {
        loadsite(window.location.hash);
    }
});

function loadsite(page) {
    console.log(page)
    $("body, html").animate({
        scrollTop: $(page).offset().top - $("header").height()
    }, 500);
}