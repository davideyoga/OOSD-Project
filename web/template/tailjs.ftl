<div class="my_modal_loading"><!-- Place at bottom of page --></div>

<script src="${context}/js/bootstrap.min.js"></script>

<script>
    $body = $("body");

    $(document).on({
        ajaxStart: function () {
            $body.addClass("loading");
        },
        ajaxStop: function () {
            $body.removeClass("loading");
        }
    });
</script>