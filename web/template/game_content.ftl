<div class="content-main" xmlns="http://www.w3.org/1999/html">

    <!--content-->
<div class="grid-system" id="game_list" style="padding-top:0">

<#if game??>


    <!--container gioco-->

    <div class="horz-grid">

        <div class="grid-hor" style="padding: 0; height: 35px;">
            <h2 style="float:left; width:70%;">${game.name?cap_first}</h2>

            <#if average??>
            <ul class="icon" id="starList" style="float:right;">

                <#list 1..(average?round) as i>
                    <i class="post-file fa fa-star" ></i>
                </#list>

                <i class="post-file">&nbsp&nbsp${average}&nbsp&nbsp<span style="font-size:65%;">/5</span></i>

            </ul>
            </#if>
        </div>
    </div>

    <div class="horz-grid">
        <div class="row show-grid" style="margin :0; border:0px;">
            <div class="col-md-6 my_hover_div">

                <img src="${context}/images/${game.image}" style="width:100%; height: 100%; z-index:2;">

                <div class="hover_black"></div>

                <button type="submit"
                        id="play_button"
                        class="btn btn-lg btn-primary edit_btn_hover btn_game">
                    Play !
                </button>

            </div>
            <!--container descrizione-->
            <div class="col-md-6" style="background: #ffffff; border:0px; padding: 0 0 0 20px;">
                <div class="well" style="width:100%; height: 100%; padding:10px;">
                ${game.description}
                </div>
            </div>

            <!--reviews-->
            <div class="col-md-12 post-top" style="background:#fff; border:0; padding:20px 0 0 0;">
                <div class="post">
                    <form class="text-area" id="formReview">
                        <textarea placeholder="Leave a review"></textarea>
                        <input type="hidden" name="vote" id="star" value="1">

                    </form>
                    <div class="post-at">
                        <ul class="icon" id="starList" style="cursor: pointer;">


                            <i class="post-file fa fa-star-o" id="star1"></i>
                            <i class="post-file fa fa-star-o" id="star2"></i>
                            <i class="post-file fa fa-star-o" id="star3"></i>
                            <i class="post-file fa fa-star-o" id="star4"></i>
                            <i class="post-file fa fa-star-o" id="star5"></i>


                            <script>
                                $('.post-file').click(function () {

                                    $(this).removeClass("fa-star-o")
                                            .addClass("fa-star");

                                    $(this).siblings()
                                            .removeClass("fa-star")
                                            .addClass("fa-star-o");

                                    $(this).prevAll()
                                            .removeClass("fa-star-o")
                                            .addClass("fa-star");

                                    var value=$(this).attr("id").charAt(4);

                                    $("#star").val(value);
                                });



                            </script>
                        </ul>
                        <form class="text-sub">
                            <input type="submit" value="post">
                        </form>
                        <div class="clearfix"></div>
                    </div>
                </div>

            </div>


            <div class="col-md-12 post-top" style="background:#fff; border:0; padding:20px 0 0 0;">
                <#if reviews?? && (reviews?size>0)>
                    <table class="table">
                        <tbody>
                        <#list 0..reviews?size as i>

                        <tr class="table-row">
                            <td class="table-img">
                                <img src="${context}/avatars/${users[i].avatar}" alt="">
                            </td>
                            <td class="table-text">
                                <h6>${users[i].username}</h6>
                                <p>${reviews[i].body}</p>
                            </td>
                            <td>
                                <span class="fam">${levels[i].name}</span>
                            </td>
                            <td class="march">
                                ${reviews[i].date}
                            </td>

                            <td>
                                <i class="fa fa-star-half-o icon-state-warning">${reviews[i].vote}</i>
                            </td>
                        </tr>

                        </#list>

                        </tbody>
                    </table>
                    <#else>
                    There are no reviews for this game :(
                </#if>


            </div>





        </div>


    </div>


</div>


<#else>
    <!-- se c'è qualche errore col gioco-->
    <div class="typo-grid">
        <div class="typo-1">
            <div class="grid_3 grid_5">
                <h4 class="head-top">Info</h4>
                <div class="but_list">
                    <div class="but_list">
                        <div class="well">
                            There was a problem with this game :(
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

<div class="clearfix"></div>
