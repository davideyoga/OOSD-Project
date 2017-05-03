<div class="content-main">

<#include "banner.ftl">

    <!--content-->
    <div class="content-top">


            <#if games??>

            <div class="gallery">
                <#list games as game>
                    <div class="col-md">
                        <div class="gallery-img">
                            <a href="game?id=${game.id}" class="b-link-stripe b-animate-go  swipebox" title="${game.name}">
                                <img class="img-responsive " src="${context}/images/${game.image}" alt="">
                                <span class="zoom-icon"> </span> </a>

                        </div>
                        <div class="text-gallery">
                            <h6>${game.name}</h6>
                        </div>
                    </div>
                </#list>
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
                <!--
                <div class="col-md-3">
                    <div class="gallery-img">
                        <a href="images/ga1.jpg" class="b-link-stripe b-animate-go  swipebox" title="Image Title">
                            <img class="img-responsive " src="${context}/images/ga1.jpg" alt="">
                            <span class="zoom-icon"> </span> </a>

                    </div>
                    <div class="text-gallery">
                        <h6>Lorem ipsum</h6>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="gallery-img">
                        <a href="images/ga1.jpg" class="b-link-stripe b-animate-go  swipebox" title="Image Title">
                            <img class="img-responsive " src="${context}/images/ga1.jpg" alt="">
                            <span class="zoom-icon"> </span> </a>

                    </div>
                    <div class="text-gallery">
                        <h6>Lorem ipsum</h6>
                    </div>
                </div>
            -->



    </div>
    <!--//content-->

<#include "footer.ftl">
</div>

<div class="clearfix"> </div>