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

                    <#assign floor=(average?floor)>
                    <#list 1..(floor) as i>
                        <i class="post-file fa fa-star"></i>
                    </#list>

                    <#assign decimal = average-floor>
                    <#if (decimal > 0)>
                        <i class="post-file fa fa-star-half"></i>
                    </#if>

                    <i class="post-file">&nbsp&nbsp${average}&nbsp&nbsp<span style="font-size:65%;">/5</span></i>

                </ul>
            </#if>
        </div>
    </div>

    <div class="horz-grid">
        <div class="row show-grid" style="margin :0; border:0px;">
            <div class="col-md-5 my_hover_div">

                <img src="${context}/images/${game.image}" style="width:100%; height: 100%; z-index:2;">

                <div class="hover_black"></div>

                <button type="submit"
                        id="play_button"
                        class="btn btn-lg btn-primary edit_btn_hover btn_game">
                    Play !
                </button>

            </div>
            <!--container descrizione-->
            <div class="col-md-7" style="background: #ffffff; border:0px; padding: 0 0 0 20px;">
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

                                    var value = $(this).attr("id").charAt(4);

                                    $("#star").val(value);
                                });

                            </script>
                        </ul>
                        <form class="text-sub">
                            <input type="submit" style="background-color:#337ab7" class="search_btn_review" value="post">
                        </form>
                        <div class="clearfix"></div>
                    </div>
                </div>

            </div>

            <#assign authReview=0>
            <#list services as service>
                <#if service.name?contains("review")>
                    <#assign authReview=1>
                    <#break>
                </#if>
            </#list>


            <div class="col-md-12 post-top" style="background:#fff; border:0; padding:20px 0 0 0;" id="reviewList">
                <#if reviews?? && (reviews?size>0)>
                    <form class="navbar-left-right"
                          style="margin-left:0em; width:33%; border-bottom:1px solid silver; margin-bottom: 20px;"
                          id="gameSearchForm">
                        <input type="text" class="search" style="float:left;" placeholder="Search through reviews">
                        <input type="button" value="" style="float:right" class="fa fa-search search ">
                    </form>
                    <table class="table">
                        <tbody class="list">
                            <#list 0..(reviews?size-1) as i>

                            <tr class="table-row">
                                <td class="table-img" style="width:10%;">
                                    <img src="${context}/avatars/${users[i].avatar}" class="reviewAvatar"
                                         style="height: 50px; width:50px;">
                                </td>

                                <td class="table-text" style="width: <#if authReview==1>60%<#else>70%</#if>;">
                                    <h6 class="username">${users[i].username}</h6>
                                    <p class="body" id="review_body_${reviews[i].idUser}_${reviews[i].idGame}">${reviews[i].body}</p>
                                </td>

                                <td>
                                    <span class="fam" style="width:15%;">lvl. ${levels[i].name}</span>
                                </td>

                                <td>
                                    <i class="fa fa-star-half-o icon-state-warning voteReview"
                                       style="width:5%;" id="review_vote_${reviews[i].idUser}_${reviews[i].idGame}">${reviews[i].vote}</i>
                                </td>
                                <#if authReview==1 || reviews[i].idUser == user.id>

                                    <td style="width:10%;">
                                        <button type="submit" id="edit_btn_${reviews[i].idGame}_${reviews[i].idUser}" data-toggle="modal" data-target="#myModal"
                                                onclick="document.getElementById('review_edit_body').value=document.getElementById('review_body_${reviews[i].idUser}_${reviews[i].idGame}').innerHTML;
                                                         document.getElementById('starEdit').value=document.getElementById('review_vote_${reviews[i].idUser}_${reviews[i].idGame}').innerHTML;"
                                                style="height:40px; width:70px; padding:10px; margin-bottom: 2px; font-size:90%; float:right; border-color:#46b8da; background-color:#5bc0de;"
                                                class="btn btn-lg btn-info edit2_btn_hover">Edit
                                        </button>

                                        <!-- popup modale -->
                                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                        <h2 class="modal-title">Edit your review</h2>
                                                    </div>
                                                    <div class="modal-body" style="height:auto">
                                                        <form id="editReviewForm">
                                                            <input type="hidden" name="title" value="">
                                                            <textarea style="height:auto; width:100%; resize: vertical;" id="review_edit_body" name="body"></textarea>
                                                            <input type="hidden" name="vote" value="" id="starEdit">
                                                        </form>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <ul class="icon" id="starList" style="cursor: pointer; margin-top:5px;">


                                                            <i class="post-file1 fa fa-star-o" id="star1.1"></i>
                                                            <i class="post-file1 fa fa-star-o" id="star2.2"></i>
                                                            <i class="post-file1 fa fa-star-o" id="star3.3"></i>
                                                            <i class="post-file1 fa fa-star-o" id="star4.4"></i>
                                                            <i class="post-file1 fa fa-star-o" id="star5.5"></i>


                                                            <script>
                                                                $('.post-file1').click(function () {

                                                                    $(this).removeClass("fa-star-o")
                                                                            .addClass("fa-star");

                                                                    $(this).siblings()
                                                                            .removeClass("fa-star")
                                                                            .addClass("fa-star-o");

                                                                    $(this).prevAll()
                                                                            .removeClass("fa-star-o")
                                                                            .addClass("fa-star");

                                                                    var value = $(this).attr("id").charAt(6);

                                                                    $("#starEdit").val(value);
                                                                });

                                                            </script>
                                                        </ul>
                                                        <button type="button" class="btn btn-info edit2_btn_hover">Save changes</button>
                                                    </div>
                                                </div><!-- /.modal-content -->
                                            </div><!-- /.modal-dialog -->
                                        </div>

                                        <button type="button" id="del_btn_${reviews[i].idGame}_${reviews[i].idUser}"
                                                style="float:right; background-color:#d95459; height:40px; width:70px; padding:10px; font-size:90%; border-color:#d95459;"
                                                class="btn btn-lg btn-danger delete_btn_hover"
                                                onclick="this.style.display='none'; document.getElementById('confirm_btn_${reviews[i].idGame}_${reviews[i].idUser}').style.display='block';">
                                            Delete
                                        </button>

                                        <button type="submit" id="confirm_btn_${reviews[i].idGame}_${reviews[i].idUser}"
                                                style="height:40px; width:70px; padding:10px; font-size:90%; float:right; display:none; border-color:#f0ad4e; background-color:#f0ad4e;"
                                                class="btn btn-lg btn-warning warning_11 confirm_btn_hover">Confirm
                                        </button>


                                    </td>

                                </#if>

                            </tr>

                            </#list>

                        </tbody>
                    </table>

                    <ul class="pagination"></ul>

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


<script>
    var options = {
        valueNames: ['username', 'body', 'voteReview', 'reviewAvatar'],
        page: 5,
        pagination: true
    };

    var reviews = new List('reviewList', options);

</script>
