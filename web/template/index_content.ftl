<div class="content-main" xmlns="http://www.w3.org/1999/html">

    <!--content-->
    <div class="content-top" style="padding:0px;" id="game_list">

            <#if games?? && (games?size-1 > 0)>

                <div class="my_full-left banner">


                    <form class="navbar-left-right" style="margin-left:1.1em; width:33%; border-bottom:1px solid silver" id="gameSearchForm">
                        <input type="text" class="search" style="float:left;" placeholder="Search...">
                        <input type="button" value="" style="float:right" class="fa fa-search search">
                    </form>


                    <div class="clearfix"></div>
                </div>

                <div class="gallery">

                    <div class="list" style="width:101%; height:auto; float:left;">

                    <#list games as game>
                        <div class="col-md ">
                            <div class="gallery-img">
                                <a href="game?id=${game.id}" class="b-link-stripe b-animate-go swipebox" title="${game.name}">
                                    <img class="img-responsive" style="padding:5px; background-color: white" src="${context}/images/${game.image}" alt="">
                                    <span class="zoom-icon"> </span> </a>

                            </div>
                            <div class="text-gallery">
                                <a href="game?id=${game.id}"><h6 class="gameName">${game.name}</h6></a>
                            </div>
                        </div>
                    </#list>
                    </div>
                    <ul class="pagination"></ul>


                    <div class="clearfix"> </div>
                </div>


            <#else>
                <div class="typo-grid">
                    <div class="typo-1">
                        <div class="grid_3 grid_5">
                            <h4 class="head-top">Info</h4>
                            <div class="but_list">
                                <div class="but_list">
                                    <div class="well">
                                        There are no games available at the moment.
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </#if>

    </div>
    <!--//content-->

<#include "footer.ftl">
</div>

<div class="clearfix"> </div>

<script>
    var gameList = new List('game_list', {
        valueNames: ['gameName'],
        page: 10,
        pagination: true
    });
</script>