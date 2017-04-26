<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Minimal Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template,
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<link href="${context}/css/bootstrap.min.css" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<link href="${context}/css/style.css" rel='stylesheet' type='text/css' />
<link href="${context}/css/font-awesome.css" rel="stylesheet">
<script src="${context}/js/jquery.min.js"> </script>
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


    });

</script>
<!--skycons-icons-->
<script src="${context}/js/skycons.js"></script>
<!--//skycons-icons-->