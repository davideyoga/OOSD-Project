<div class="content-main" xmlns="http://www.w3.org/1999/html">

    <!--content-->
    <div class="grid-system" id="game_list" style="padding-top:0">


        <!--container gioco-->

        <div class="horz-grid">

            <div class="grid-hor" style="padding: 0; height: 35px;">
                <h2 style="float:left; width:70%;">${user.username}'s profile</h2>

            <#if level?? && level.icon??>


                <ul class="icon" id="starList" style="float:right; margin-top:-7px;">
                    <i class="post-file">Level &nbsp&nbsp <img style="width:50px; height: 50px;"
                                                               src="${context}/images/${level.icon}"
                                                               onerror="this.onerror=null;this.src='${context}/images/zero.png';"></i>
                </ul>
            </#if>

            </div>
        </div>

        <div class="horz-grid">
            <div class="row show-grid" style="margin :0; border:0px;">
                <div class="col-md-6 my_hover_div">

                    <img src="${context}/avatars/${user.avatar}" style="width:100%; height: 100%; z-index:2;">

                </div>
                <!--container descrizione-->
                <div class="col-md-6" style="background: #ffffff; border:0px; padding: 0 0 0 20px;">
                    <div class="well" style="width:100%; height: 100%; padding:10px;">
                        Name: <b>${user.name}</b>
                    </div>
                    <div class="well" style="width:100%; height: 100%; padding:10px;">
                        Surname: <b>${user.surname}</b>
                    </div>
                    <div class="well" style="width:100%; height: 100%; padding:10px;">
                        Username: <b>${user.username}</b>
                    </div>
                    <div class="well" style="width:100%; height: 100%; padding:10px;">
                        Email: <b>${user.email}</b>
                    </div>
                    <div style="position: absolute; bottom: -205px; right: 5px;">
                        <button type="submit" onclick="window.location='/edit/user/${user.id}'" id="edit_btn" style="background-color: #1abc9c; border-color: #1abc9c;" class="btn btn-lg btn-primary edit_btn_hover">Edit Infos
                        </button>
                    </div>


                </div>
            </div>
        </div>

        <!-- barra progresso -->
        <div class="horz-grid">
            <p>${user.exp} / ${nextLevel.exp} (level ${nextLevel.name})</p>
            <div class="progress">
                <div class="progress-bar progress-bar-inverse"
                     style="width: ${percentage}%; background-color: #337ab7;"></div>
            </div>
        </div>

        <!--grafici-->

    <#if userGameList?? && (userGameList?size>0) && userLevelList?? &&(userLevelList?size>0)  && userGameListFull?? && userLevelListFull??>

        <script src="${context}/js/Chart.js"></script>

        <div class="graph-grid">
            <div class="col-md-6 graph-1" style="padding-left:0px;">
                <div class="grid-1">
                    <h4>Exp gain chart (last 5 items)</h4>
                    <canvas id="bar1" height="500" width="500" style="width: 500px; height: 300px;"></canvas>
                    <script>


                        var barChartData = {
                            labels: [<#list userGameList as list>

                                "${list[0]?string["dd/MM,hh:mm"]} (${list[1]})"
                                <#if list_has_next>,</#if>


                            </#list>],
                            datasets: [
                                {
                                    fillColor: "#d95459",
                                    strokeColor: "#d95459",
                                    data: [<#list userGameList as list>


                                    ${list[2]}
                                        <#if list_has_next>,</#if>

                                    </#list>]
                                }
                            ]

                        };
                        new Chart(document.getElementById("bar1").getContext("2d")).Bar(barChartData);
                    </script>
                </div>
            </div>
            <div class="col-md-6 graph-2" style="padding-right:0px;">
                <div class="grid-1">
                    <h4>Levels Chart (last 5 items)</h4>
                    <canvas id="line1" height="500" width="500" style="width: 500px; height: 300px;"></canvas>
                    <script>

                        var lineChartData = {
                            labels: [<#list userLevelList as list>

                                "${list[0]?string["dd/MM,hh:mm"]} (lvl. ${list[1]})"
                                <#if list_has_next>,</#if>


                            </#list>],
                            datasets: [
                                {
                                    fillColor: "#fff",
                                    strokeColor: "#1ABC9C",
                                    pointColor: "#1ABC9C",
                                    pointStrokeColor: "#1ABC9C",
                                    data: [<#list userLevelList as list>


                                    ${list[2]}
                                        <#if list_has_next>,</#if>

                                    </#list>]
                                }
                            ]

                        };
                        new Chart(document.getElementById("line1").getContext("2d")).Line(lineChartData);
                    </script>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
    </#if>

        <div class="typo-grid">


            <div class="typo-1" style="padding:0;">

                <div class="row" style="background-color: #fec057; border-radius: 4px;">
                    <div class="col-sm-6">
                        <div style="margin-top:6px; "><label style="color:white;">Full game sessions report:</label></div>
                    </div>
                </div>
            </div>


            <div class="typo-1" style="padding:0;">

                <table id="reportGames" class="table table-striped table-bordered" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Game Name</th>
                        <th>Exp Gained</th>
                    </tr>
                    </thead>

                    <tbody>
                        <#list userGameListFull as item>
                        <tr>
                            <#list item as i>
                                <td>
                                    <#if i??>
                                    ${i}
                                    <#else>
                                        null or not available
                                    </#if>
                                </td>
                            </#list>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>


        <div class="typo-1" style="padding:0;">

            <div class="row" style="background-color: #337ab7; border-radius: 4px;">
                <div class="col-sm-6">
                    <div style="margin-top:6px; font-size: 105%;"><label style="color:white;">Full level history:</label></div>
                </div>
            </div>
        </div>


        <div class="typo-1" style="padding:0;">

            <table id="reportLevels" class="table table-striped table-bordered" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Level Name</th>
                    <th>Exp Nedded</th>
                </tr>
                </thead>

                <tbody>
                    <#list userLevelListFull as item>
                    <tr>
                        <#list item as i>
                            <td>
                                <#if i??>
                                ${i}
                                <#else>
                                    null or not available
                                </#if>
                            </td>
                        </#list>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>


    </div>


    <!--//content-->

<#include "footer.ftl">
</div>

<div class="clearfix"></div>


<script>
    $(document).ready(function () {
        $('#reportGames').DataTable();
        $('#reportLevels').DataTable();
    });
</script>
