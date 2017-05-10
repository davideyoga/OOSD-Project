<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse">
        <ul class="nav" id="side-menu">

            <li>
                <a href="/index" class=" hvr-bounce-to-right"><i class="fa fa-home nav_icon "></i><span class="nav-label">Home page</span> </a>
            </li>

        <#if user??>
            <li>
                <a href="/profile" class=" hvr-bounce-to-right"><i class="fa fa-user nav_icon "></i><span class="nav-label">Profile</span> </a>
            </li>

            <#if services??>

                <#list services as service>
                    <li>
                        <a href="#" class=" hvr-bounce-to-right"><i class="fa fa-sliders nav_icon"></i> <span class="nav-label">${service.name?cap_first}</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li><a href="/add/${service.name}" class=" hvr-bounce-to-right"> <i class="fa fa-plus-square-o nav_icon"></i>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Add</a></li>
                            <li><a href="/report/${service.name}" class=" hvr-bounce-to-right"> <i class="fa fa-bar-chart nav_icon"></i>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Report</a></li>
                            <li><a href="/edit/${service.name}" class=" hvr-bounce-to-right"> <i class="fa fa-edit nav_icon"></i>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Edit</a></li>
                        </ul>
                    </li>
                </#list>

            </#if>

            <li>
                <a href="/logout" class=" hvr-bounce-to-right"><i class="fa fa-arrow-circle-o-left nav_icon "></i><span class="nav-label">LogOut</span> </a>
            </li>



        </#if>

            <!--
                <a href="#" class=" hvr-bounce-to-right"><i class="fa fa-indent nav_icon"></i> <span class="nav-label">Menu Levels</span><span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li><a href="graphs.html" class=" hvr-bounce-to-right"> <i class="fa fa-area-chart nav_icon"></i>Graphs</a></li>

                    <li><a href="maps.html" class=" hvr-bounce-to-right"><i class="fa fa-map-marker nav_icon"></i>Maps</a></li>

                    <li><a href="typography.html" class=" hvr-bounce-to-right"><i class="fa fa-file-text-o nav_icon"></i>Typography</a></li>


            <li>
                <a href="inbox.html" class=" hvr-bounce-to-right"><i class="fa fa-inbox nav_icon"></i> <span class="nav-label">Inbox</span> </a>
            </li>

            <li>
                <a href="gallery.html" class=" hvr-bounce-to-right"><i class="fa fa-picture-o nav_icon"></i> <span class="nav-label">Gallery</span> </a>
            </li>
            <li>
                <a href="#" class=" hvr-bounce-to-right"><i class="fa fa-desktop nav_icon"></i> <span class="nav-label">Pages</span><span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li><a href="404.html" class=" hvr-bounce-to-right"> <i class="fa fa-info-circle nav_icon"></i>Error 404</a></li>
                    <li><a href="faq.html" class=" hvr-bounce-to-right"><i class="fa fa-question-circle nav_icon"></i>FAQ</a></li>
                    <li><a href="blank.html" class=" hvr-bounce-to-right"><i class="fa fa-file-o nav_icon"></i>Blank</a></li>
                </ul>
            </li>
            <li>
                <a href="layout.html" class=" hvr-bounce-to-right"><i class="fa fa-th nav_icon"></i> <span class="nav-label">Grid Layouts</span> </a>
            </li>

            <li>
                <a href="#" class=" hvr-bounce-to-right"><i class="fa fa-list nav_icon"></i> <span class="nav-label">Forms</span><span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li><a href="forms.html" class=" hvr-bounce-to-right"><i class="fa fa-align-left nav_icon"></i>Basic forms</a></li>
                    <li><a href="validation.html" class=" hvr-bounce-to-right"><i class="fa fa-check-square-o nav_icon"></i>Validation</a></li>
                </ul>
            </li>-->


        </ul>
    </div>
</div>