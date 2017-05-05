<!----->
<nav class="navbar-default navbar-static-top" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <h1><a class="navbar-brand" href="index">GamingPlatform</a></h1>
    </div>
    <div class=" border-bottom">
        <div class="full-left">
            <section class="full-top">
                <button id="toggle"><i class="fa fa-arrows-alt"></i></button>
            </section>
            <!--
            <form class=" navbar-left-right">
                <input type="text" value="Search..." placeholder="Search...">
                <input type="submit" value="" class="fa fa-search">
            </form>
            -->
            <div class="clearfix"></div>
        </div>


        <!-- Brand and toggle get grouped for better mobile display -->

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="drop-men">
            <ul class=" nav_1">

                <!--     notifications
                          <li class="dropdown at-drop">
                              <a href="#" class="dropdown-toggle dropdown-at " data-toggle="dropdown"><i class="fa fa-globe"></i> <span class="number">5</span></a>
                              <ul class="dropdown-menu menu1 " role="menu">
                                  <li><a href="#">

                                      <div class="user-new">
                                          <p>New user registered</p>
                                          <span>40 seconds ago</span>
                                      </div>
                                      <div class="user-new-left">

                                          <i class="fa fa-user-plus"></i>
                                      </div>
                                      <div class="clearfix"> </div>
                                  </a></li>
                                  <li><a href="#">
                                      <div class="user-new">
                                          <p>Someone special liked this</p>
                                          <span>3 minutes ago</span>
                                      </div>
                                      <div class="user-new-left">

                                          <i class="fa fa-heart"></i>
                                      </div>
                                      <div class="clearfix"> </div>
                                  </a></li>
                                  <li><a href="#">
                                      <div class="user-new">
                                          <p>John cancelled the event</p>
                                          <span>4 hours ago</span>
                                      </div>
                                      <div class="user-new-left">

                                          <i class="fa fa-times"></i>
                                      </div>
                                      <div class="clearfix"> </div>
                                  </a></li>
                                  <li><a href="#">
                                      <div class="user-new">
                                          <p>The server is status is stable</p>
                                          <span>yesterday at 08:30am</span>
                                      </div>
                                      <div class="user-new-left">

                                          <i class="fa fa-info"></i>
                                      </div>
                                      <div class="clearfix"> </div>
                                  </a></li>
                                  <li><a href="#">
                                      <div class="user-new">
                                          <p>New comments waiting approval</p>
                                          <span>Last Week</span>
                                      </div>
                                      <div class="user-new-left">

                                          <i class="fa fa-rss"></i>
                                      </div>
                                      <div class="clearfix"> </div>
                                  </a></li>
                                  <li><a href="#" class="view">View all messages</a></li>
                              </ul>
                          </li> -->


                <#if user??>


                    <li class="dropdown" style="padding-top:5px;">
                        <a href="#" class="dropdown-toggle dropdown-at" data-toggle="dropdown">
                            <span class=" name-caret" style="margin-top:5px;">${user.username}<i class="caret"></i></span>
                            <img style="width:50px; height: 50px;" src="${context}/avatars/${user.avatar}"></a>
                        <ul class="dropdown-menu " role="menu">
                            <li><a href="inbox"><i class="fa fa-user"></i>&nbspProfile</a></li>
                            <li><a href="profile"><i class="fa fa-cogs"></i>Edit Profile</a></li>
                            <li><a href="logout"><i class="fa fa-arrow-circle-o-left"></i>&nbspLogOut</a></li>

                        </ul>
                    </li>

                <#else>

                <div class="full-right">
                    <p>
                        <a href="signup"><button type="button" class="btn btn-primary my_btn_red">Signup</button></a>
                        <a href="login"><button type="button" class="btn btn-success warning_2 my_btn_green">Login</button></a>

                    </p>
                </div>

                </#if>


            </ul>
        </div><!-- /.navbar-collapse -->
        <div class="clearfix">

        </div>

</nav>