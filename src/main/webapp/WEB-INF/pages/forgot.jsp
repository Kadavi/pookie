<!DOCTYPE html>
<!--[if IE 8]>
<html class="no-js lt-ie9" lang="en">

<![endif]-->
<!--[if gt IE 8]>
<!-->
<html class="no-js" lang="en">
<!--
<![endif]-->

<head>

    <!-- Basic Page Needs
================================================== -->
    <meta charset="utf-8">
    <title>
        Polite Stare - My Account
    </title>
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Mobile Specific Metas
================================================== -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <!-- CSS
================================================== -->
    <link rel="stylesheet" href="/resources/css/base.css" />
    <link rel="stylesheet" href="/resources/css/skeleton.css" />
    <link rel="stylesheet" href="/resources/css/layout.css" />
    <link rel="stylesheet" href="/resources/css/font-awesome.css" />
    <link rel="stylesheet" href="/resources/css/colorbox.css" />
    <link rel="stylesheet" href="/resources/css/retina.css" />

    <link rel="alternate stylesheet" type="text/css" href="/resources/css/colors/color-orange.css" title="orange">
    <link rel="alternate stylesheet" type="text/css" href="/resources/css/colors/color-green.css" title="green">
    <link rel="alternate stylesheet" type="text/css" href="/resources/css/colors/color-red.css" title="red">
    <link rel="alternate stylesheet" type="text/css" href="/resources/css/colors/color-blue.css" title="blue">

    <!-- Favicons
================================================== -->
    <link rel="shortcut icon" href="/resources/images/favicon.png">
    <link rel="apple-touch-icon" href="/resources/images/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/resources/images/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/resources/images/apple-touch-icon-114x114.png">

</head>

<body class="royal_loader">

<!-- Primary Page Layout
================================================== -->

<div id="menu-wrap" style="background-color: rgba(85, 123, 162, 0.41);" class="menu-back cbp-af-header">
    <div class="container">
        <div class="sixteen columns">
            <div class="logo">
            </div>
            <ul class="slimmenu">
                <li>
                    <a class="scroll" href="/#home">
                        home
                    </a>
                </li>
                <li>
                    <a class="scroll" href="/#about">
                        Product
                    </a>
                </li>
                <li>
                    <a class="scroll" href="/#services">
                        Pricing
                    </a>
                </li>
                <li>
                    <a class="scroll" href="/#contact">
                        Sign Up
                    </a>
                </li>
                <li>
                    <a class="scroll" href="/account">
                        My Account
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>

<div id="contact">
    <div class="just_pattern">
    </div>
    <div class="just_pattern1">
    </div>
    <div class="container z-index">
        <br>
        <br>
        <br>
        <div class="sixteen columns" data-scrollreveal="enter bottom and move 150px over 1s">
            <div id="topMessage" style="color: red;">${message}</div><br>
            <div class="contact-wrap">
                <p>
                    <span>Forgot Password</span>
                </p>
                <form name="ajax-form" id="ajax-form" action="/forgot" method="post">
                    <label for="email">
                        E-Mail:
                    </label>
                    <input name="email" id="email" type="text" />
                    <div id="button-con">
                        <button id="forgot">
                            Send
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <br>
    <br>
</div>
</div>
<div id="footer">
    <div class="container">
        <div class="sixteen columns">
            <p>
                Made with
                <i class="icon-footer">
                    &#xf004;
                </i>
                and
                <i class="icon-footer">
                    &#xf0f4;
                </i>
            </p>
            <p>
                &copy;opywrong Polite Stare 2014
            </p>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resources/js/jquery.js">
</script>

<script type="text/javascript" src="/resources/js/modernizr.custom.js">
</script>

<script type="text/javascript" src="/resources/js/royal_preloader.min.js">
</script>
<script type="text/javascript">
    Royal_Preloader.config({
        mode: 'text', // 'number', "text" or "logo"
        text: '',
        timeout: 0,
        showInfo: true,
        opacity: 1,
        background: ['#000000']
    });
</script>

<script type="text/javascript" src="/resources/js/classie.js">
</script>
<script type="text/javascript" src="/resources/js/cbpAnimatedHeader.min.js">
</script>
<script type="text/javascript" src="/resources/js/styleswitcher.js">
</script>
<script type="text/javascript" src="/resources/js/retina-1.1.0.min.js">
</script>
<script type="text/javascript" src="/resources/js/flippy.js">
</script>
<script type="text/javascript" src="/resources/js/jquery.easing.js">
</script>
<script type="text/javascript" src="/resources/js/svganimations.js">
</script>
<script type="text/javascript" src="/resources/js/jquery.bxslider.min.js">
</script>
<script type="text/javascript" src="/resources/js/jquery.colorbox.js">
</script>

<script type="text/javascript" src="/resources/js/contact.js">
</script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true">
</script>
<script type="text/javascript" src="/resources/js/plugins.js">
</script>
<script type="text/javascript" src="/resources/js/template.js">
</script>
<script type="text/javascript" src="https://js.stripe.com/v1/"></script>
<script type="text/javascript">

    $(document).ready(function(){
        $('#forgot').click(function(e) {
            e.stopPropagation();
            e.preventDefault();
            var $form = $(this).closest('form');

            // Disable the submit button to prevent repeated clicks
            $('button').prop('disabled', true);

            setTimeout(function() {
                $('button').prop('disabled', false);
            }, 3000);

            $form.submit();

            // Prevent the form from submitting with the default action
            return false;
        });
    });
</script>
</body>

</html>