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
    <div class="">
    </div>
    <div class="container z-index">
        <a id="logoutBtn" style="float: right; padding-right: 20px; padding-top: 5px;">Logout</a>
        <br>
        <br>
        <br>
        <div class="sixteen columns" data-scrollreveal="enter bottom and move 150px over 1s">
            <div class="contact-wrap">
                <p><span>My Account</span></p>
                <form name="password-form" id="ajax-form" action="/changepassword" method="post">
                    <label for="email">
                        E-Mail:
                    </label>
                    <input name="email" id="email" type="text" disabled value="${email}" />
                    <label for="oldpassword">
                        Current Password:
                    </label>
                    <input name="oldPassword" id="oldPassword" type="text" />
                    <label for="password">
                        New Password:
                    </label>
                    <input name="password" id="password" type="text" />
                    <label for="confirmPassword">
                        Confirm Password:
                    </label>
                    <input name="confirmPassword" id="confirmPassword" type="text" />
                    <div id="button-con">
                        <button class="send_message" id="changePassword">
                            Change Password
                        </button>
                    </div>
                    <div class="error">
                    </div>
                </form>
                <div id="ajaxsuccess">
                    Successfully sent!
                </div>
            </div>

            <div class="contact-wrap">
                <br>
                <p><span>Payment Method</span></p>
                <form name="payment-form" id="ajax-form" action="/changecard" method="post">
                    <label for="currentCard">
                        Current Card:
                    </label>
                    <input name="currentCard" id="currentCard" type="text" disabled value="${cardLastFour}" />
                    <label for="ccNumber">
                        Add New Card:
                    </label>
                    <div style="width: 100%;">
                        <input style="width: 44%;" id="ccNumber" data-stripe="number" size="20" maxlength="20" type="text" placeholder="Card Number"/>
                        <input style="width: 11%;" id="ccMonth" data-stripe="exp-month" size="2" maxlength="2" type="text" placeholder="MM"/>
                        <input style="width: 11%;" id="ccYear" data-stripe="exp-year" size="4" maxlength="4" type="text" placeholder="YYYY"/>
                        <input style="width: 13%;" id="cvcNumber" data-stripe="cvc" size="4" maxlength="4" type="text" placeholder="CVC"/>
                    </div>
                    <input name="email" id="email" type="hidden" disabled value="${email}" />
                    <div id="button-con">
                        <button class="send_message" id="changeCard">
                            Change Cards
                        </button>
                    </div>
                    <div class="error">
                    </div>
                </form>
                <div id="ajaxsuccess">
                    Successfully sent!
                </div>
                <br>
            </div>
        </div>

    </div>
    <br>
    <br>
</div>

</div>
<br>
<br>


<div style="position: relative; left: 50%; float: left;">
    <!-- Download links -->
    <a href="/resources/downloads/politestare_win.zip" style="position: relative; left: -50%;">
        <img style="position: relative; width: 138px;" src="/resources/images/windows.png">
    </a>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

    <img style="position: relative; width: 138px; left: -50%;" src="/resources/images/maccomingsoon.png">

</div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
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


<!-- Switch Panel -->
<div id="switch">
    <div class="content-switcher">

        <p>
            Color Options:
        </p>
        <ul class="header">

            <li>
                <a href="#" onClick="setActiveStyleSheet('orange'); return false;" class="button color switch" style="background-color:#e67e22">
                </a>
            </li>
            <li>
                <a href="#" onClick="setActiveStyleSheet('green'); return false;" class="button color switch" style="background-color:#2ecc71">
                </a>
            </li>
            <li>
                <a href="#" onClick="setActiveStyleSheet('red'); return false;" class="button color switch" style="background-color:#e74c3c">
                </a>
            </li>
            <li>
                <a href="#" onClick="setActiveStyleSheet('blue'); return false;" class="button color switch" style="background-color:#3498db">
                </a>
            </li>
            <li>
                <a href="#" onClick="setActiveStyleSheet('yellow'); return false;" class="button color switch" style="background-color:#f1c40f">
                </a>
            </li>

        </ul>

        <div class="clear">
        </div>

        <p>
            Page Templates:
        </p>

        <div class="home-options">
            <a href="http://ivang-design.com/chronos/slider/">
                Slider Version
            </a>
            <a href="http://ivang-design.com/chronos/parallax/">
                Parallax Version
            </a>
            <a href="http://ivang-design.com/chronos/light/">
                Light Version
            </a>
        </div>

        <div id="hide">
            <img src="/resources/images/close.png" alt="" />

        </div>
    </div>
</div>
<div id="show" style="display: none;">
    <div id="setting">
    </div>
</div>
<!-- Switch Panel -->

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
</script>-
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
<script type="text/javascript">
    $(document).ready(function(){

        $('#logoutBtn').click(function(e) {
            e.stopPropagation();
            e.preventDefault();

            var cookies = document.cookie.split(";");

            for (var i = 0; i < cookies.length; i++) {
                var cookie = cookies[i];
                var eqPos = cookie.indexOf("=");
                var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
                document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
            }

            window.location.href = "http://localhost:8080/account";

            return false;
        });
    });
</script>
<script type="text/javascript" src="https://js.stripe.com/v1/"></script>
<script type="text/javascript">

    $(document).ready(function(){
        Stripe.setPublishableKey('pk_live_kAsQAcfWazWM0r2GBRIe7PX4');

        var stripeResponseHandler = function(status, response) {
            var $form = $('form[name="payment-form"]');

            if (response.error) {
                // Show the errors on the form
                $form.find('.error').text(response.error.message);
                $form.find('button').prop('disabled', false);
            } else {
                // token contains id, last4, and card type
                var token = response.id;
                // Insert the token into the form so it gets submitted to the server

                $form.append($('<input type="hidden" name="stripeToken" />').val(token));
                // and re-submit
                $form.submit();
            }
        };

        $('#changePassword').click(function(e) {
            e.stopPropagation();
            e.preventDefault();
            var $form = $(this).closest('form');

            // Disable the submit button to prevent repeated clicks
            $form.find('button').prop('disabled', true);

            $form.submit();

            setTimeout(function() {
                $form.find('button').prop('disabled', false);
            }, 5000);

            // Prevent the form from submitting with the default action
            return false;
        });
    });

    $('#changeCard').click(function(e) {
        e.stopPropagation();
        e.preventDefault();
        var $form = $(this).closest('form');

        // Disable the submit button to prevent repeated clicks
        $form.find('button').prop('disabled', true);

        Stripe.card.createToken($form, stripeResponseHandler);

        // Prevent the form from submitting with the default action
        return false;
    });

</script>
</body>

</html>