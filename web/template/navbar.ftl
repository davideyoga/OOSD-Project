<!----->
<nav class="navbar-default navbar-static-top" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <h1><a class="navbar-brand" href="/index">GamingPlatform</a></h1>
    </div>
    <div class=" border-bottom">
        <div class="full-left" style="margin-left: 1.5em;">
            <section class="full-top">
                <button id="toggle"><i class="fa fa-arrows-alt"></i></button>
            </section>

            <!--search was here-->

            <div class="clearfix"></div>
        </div>


        <!-- Brand and toggle get grouped for better mobile display -->

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="drop-men">
            <ul class=" nav_1">


                <#if user??>


                    <li class="dropdown" style="padding-top:5px;">
                        <a href="#" class="dropdown-toggle dropdown-at" data-toggle="dropdown">
                            <span class=" name-caret" style="margin-top:5px;">${user.username}<i class="caret"></i></span>
                            <img style="width:50px; height: 50px;" src="${context}/avatars/${user.avatar}" onerror="this.onerror=null;this.src='${context}/images/default.png';"></a>
                        <ul class="dropdown-menu " role="menu">
                            <li><a href="/profile"><i class="fa fa-user"></i>&nbspProfile</a></li>
                            <li><a href="/edit/user/${user.id}"><i class="fa fa-cogs"></i>Edit Profile</a></li>
                            <li><a href="/logout"><i class="fa fa-arrow-circle-o-left"></i>&nbspLogOut</a></li>

                        </ul>
                    </li>

                <#else>

                <div class="full-right">
                    <p>
                        <a href="/signup"><button type="button" class="btn btn-primary my_btn_red">Signup</button></a>
                        <a href="/login"><button type="button" class="btn btn-success warning_2 my_btn_green">Login</button></a>

                    </p>
                </div>

                </#if>


            </ul>
        </div><!-- /.navbar-collapse -->
        <div class="clearfix">

        </div>

</nav>