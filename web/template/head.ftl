<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="keywords" content="Minimal Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template,
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design"/>
<script type="application/x-javascript"> addEventListener("load", function () {
    setTimeout(hideURLbar, 0);
}, false);
function hideURLbar() {
    window.scrollTo(0, 1);
} </script>
<link href="${context}/css/bootstrap.min.css" rel='stylesheet' type='text/css'/>
<!-- Custom Theme files -->
<link href="${context}/css/style.css" rel='stylesheet' type='text/css'/>
<link href="${context}/css/font-awesome.css" rel="stylesheet">
<script src="${context}/js/jquery.min.js"></script>
<!-- Mainly scripts -->
<script src="${context}/js/jquery.metisMenu.js"></script>
<script src="${context}/js/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<link href="${context}/css/custom.css" rel="stylesheet">
<script src="${context}/js/custom.js"></script>
<script src="${context}/js/screenfull.js"></script>
<script>
    $(function () {
        $('#supported').text('Supported/allowed: ' + !!screenfull.enabled);

        if (!screenfull.enabled) {
            return false;
        }


        $('#toggle').click(function () {
            screenfull.toggle($('#container')[0]);
        });


    });
</script>

<!----->

<!--pie-chart--->
<script src="${context}/js/pie-chart.js" type="text/javascript"></script>
<script type="text/javascript">

    $(document).ready(function () {

        $('#demo-pie-1').pieChart({
            barColor: '#3bb2d0',
            trackColor: '#eee',
            lineCap: 'round',
            lineWidth: 8,
            onStep: function (from, to, percent) {
                $(this.element).find('.pie-value').text(Math.round(percent) + '%');
            }
        });

        $('#demo-pie-2').pieChart({
            barColor: '#fbb03b',
            trackColor: '#eee',
            lineCap: 'butt',
            lineWidth: 8,
            onStep: function (from, to, percent) {
                $(this.element).find('.pie-value').text(Math.round(percent) + '%');
            }
        });

        $('#demo-pie-3').pieChart({
            barColor: '#ed6498',
            trackColor: '#eee',
            lineCap: 'square',
            lineWidth: 8,
            onStep: function (from, to, percent) {
                $(this.element).find('.pie-value').text(Math.round(percent) + '%');
            }
        });
    <#if message??>
        var fade_out = function () {
            $("#message").fadeOut();
        };
        setTimeout(fade_out, 3000);

        <#if messageTxt??>
            var messageText = '${messageTxt}';
        </#if>

        $("body").prepend("<div id='message' style='font-size: 13px; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19); cursor: pointer; color: #4444dd; opacity: 1; position: absolute;z-index: 9999; width: 280px; height: 100px; border-radius: 3px; background-color: #88ddff; border:1px solid transparent; right: 30px; top:30px; padding: 10px;'><div>");
        var message;
        switch ('${message}') {
            case "OK-add-cart":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>The book has been added to your cart.';
                break;
            case "OK-add-wish":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>he book has been added to your wishlist.';
                break;
            case "OK-logout":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>You are now logged out.';
                break;
            case "OK-signup":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>You can now login with the email/password you provided.';
                break;
            case "OK-login":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>You are now logged in.';
                break;
            case "KO-order-canc":
                $('#message').css({'background-color': '#f2dede', 'color': '#a94442', 'border-color': '#ebccd1'});
                message = '<strong>Warning!</strong><br><br>You can\'t cancel orders that have already been processed.';
                break;
            case "OK-order-canc":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>The order has been canceled.';
                break;
            case "OK-order":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>Your order has been added.';
                break;
            case "KO-wrong-card-data":
                $('#message').css({'background-color': '#f2dede', 'color': '#a94442', 'border-color': '#ebccd1'});
                message = '<strong>Warning!</strong><br><br>The card you inserted is not valid.';
                break;
            case "KO-bad-old-psw":
                $('#message').css({'background-color': '#f2dede', 'color': '#a94442', 'border-color': '#ebccd1'});
                message = '<strong>Warning!</strong><br><br>The old password you inserted is wrong, try again.';
                break;
            case "KO-not-logged":
                $('#message').css({'background-color': '#f2dede', 'color': '#a94442', 'border-color': '#ebccd1'});
                message = '<strong>Warning!</strong><br><br>We need you to be registered and logged in to proceed with your request.';
                break;
            case "OK-review-add":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>Your review has been added.';
                break;
            case "OK-recover":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>A temporary password has been sent to your email address.';
                break;
            case "OK-message":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>The message has been sent successfully.';
                break;
            case "OK":
                $('#message').css({'background-color': '#dff0d8', 'color': '#3c763d', 'border-color': '#d6e9c6'});
                message = '<strong>Success!</strong><br><br>Your request has been completed successfully.';
                break;
            case "empty":
                $('#message').css({'background-color': '#fcf8e3', 'color': '#8a6d3b', 'border-color': '#faebcc'});
                message = '<strong>Warning!</strong><br><br>The are no items matching your request.';
                break;
            case "error":
            case "KO":
                $('#message').css({'background-color': '#f2dede', 'color': '#a94442', 'border-color': '#ebccd1'});
                message = '<strong>Warning!</strong><br><br>There was a problem with your request.';
                break;
            default:
                $('#message').css({'background-color': '#f2dede', 'color': '#a94442', 'border-color': '#ebccd1'});
                message = '<strong>Warning!</strong><br><br>' + messageText;
                break;
        }

        setTimeout(fade_out, 6000);

        $('#message')
                .html(message)
                .hide()
                .fadeIn()
                .click(function () {
                    $(this).fadeOut();
                });
    </#if>


    });

</script>
<!--skycons-icons-->
<script src="${context}/js/skycons.js"></script>
<!--//skycons-icons-->