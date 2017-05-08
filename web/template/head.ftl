<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="keywords" content="Minimal Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template,
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design"/>
<link rel="apple-touch-icon" sizes="57x57" href="${context}/icon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="${context}/icon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="${context}/icon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="${context}/icon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="${context}/icon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="${context}/icon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="${context}/icon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="${context}/icon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="${context}/icon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"  href="${context}/icon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="${context}/icon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="${context}/icon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="${context}/icon/favicon-16x16.png">
<link rel="manifest" href="/manifest.json">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">
<script type="application/x-javascript"> addEventListener("load", function () {
    setTimeout(hideURLbar, 0);
}, false);
function hideURLbar() {
    window.scrollTo(0, 1);
} </script>

<script src="${context}/js/jquery.min.js"></script>
<!--list.js-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.5.0/list.min.js"></script>
<!--datatables-->
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.15/js/jquery.dataTables.js"></script>
<!--fine datatable-->

<link href="${context}/css/bootstrap.min.css" rel='stylesheet' type='text/css'/>
<!-- Custom Theme files -->
<link href="${context}/css/style.css" rel='stylesheet' type='text/css'/>
<link href="${context}/css/font-awesome.css" rel="stylesheet">
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
        setTimeout(fade_out, 2750);

        <#if messageTxt??>
            var messageText = '${messageTxt}';
        </#if>

        $("body").prepend("<div id='message' class='div_message_top'><div>");
        var message;
        switch ('${message}') {
            case "OK-logout":
                $('#message').addClass("div_message_top_success");
                message = '<strong>Success!</strong><br><br>You are now logged out.';
                break;
            case "OK-signup":
                $('#message').addClass("div_message_top_success");
                message = '<strong>Success!</strong><br><br>You can now login with the email/password you provided.';
                break;
            case "OK-login":
                $('#message').addClass("div_message_top_success");
                message = '<strong>Success!</strong><br><br>You are now logged in.';
                break;
            case "OK-review-add":
                $('#message').addClass("div_message_top_warning");
                message = '<strong>Success!</strong><br><br>Your review has been added.';
                break;
            case "OK-recover":
                $('#message').addClass("div_message_top_warning");
                message = '<strong>Success!</strong><br><br>A temporary password has been sent to your email address.';
                break;
            case "OK-message":
                $('#message').addClass("div_message_top_success");
                message = '<strong>Success!</strong><br><br>The message has been sent successfully.';
                break;
            case "OK":
                $('#message').addClass("div_message_top_success");
                message = '<strong>Success!</strong><br><br>Your request has been completed successfully.';
                break;
            case "KO-login":
                $('#message').addClass("div_message_top_warning");
                message = '<strong>Warning!</strong><br><br>The username/password combination is wrong.';
                break;
            case "KO-bad-old-psw":
                $('#message').addClass("div_message_top_warning");
                message = '<strong>Warning!</strong><br><br>The old password you inserted is wrong, try again.';
                break;
            case "KO-not-logged":
                $('#message').addClass("div_message_top_warning");
                message = '<strong>Warning!</strong><br><br>We need you to be registered and logged in to proceed with your request.';
                break;
            case "KO-signup":
                $('#message').addClass("div_message_top_warning");
                message = '<strong>Warning!</strong><br><br>Your signup request has failed.';
                break;
            case "KO-unauthorized":
                $('#message').addClass("div_message_top_warning");
                message = '<strong>Warning!</strong><br><br>You have not the authorization to access the resource.';
                break;
            case "error":
            case "KO":
                $('#message').addClass("div_message_top_warning");
                message = '<strong>Warning!</strong><br><br>There was a problem with your request.';
                break;
            default:
                $('#message').addClass("div_message_top_warning");
                message = '<strong>Warning!</strong><br><br>' + messageText;
                break;
        }

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