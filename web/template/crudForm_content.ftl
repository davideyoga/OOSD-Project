<div class="content-main" xmlns="http://www.w3.org/1999/html">

    <!--content-->
    <div class="content-top" style="padding:5px 20px 20px 20px;">

    <#if fields?? && keys?? && nulls?? && types?? && defaults?? && extras?? && table??>


        <div class="grid-form1">
            <h3 id="forms-horizontal" style="padding:20px;">${mode?cap_first} ${table?cap_first}</h3>

            <form class="form-horizontal" id="my_form">

                <#if itemId??>
                    <input type="hidden" name="id" value="${itemId}">
                </#if>

                <#list 1..arity as i>

                    <#if !(keys?api.get(i)?starts_with("MUL") || keys?api.get(i)?starts_with("PRI"))>

                        <!-- caso int -->
                        <#if types?api.get(i)?starts_with("int")>
                            <#assign type="number">
                            <#assign constraints="min=0">

                            <!-- caso varchar -->
                        <#elseif types?api.get(i)?starts_with("varchar")>
                            <!-- caso varchar e email-->
                            <#if fields?api.get(i)?starts_with("email")>
                                <#assign type="email">
                                <!-- caso varchar e password-->
                            <#elseif fields?api.get(i)?starts_with("password")>
                                <#assign type="password">
                                <#if mode=="edit">
                                    <#assign constraints="disabled">
                                </#if>
                                <!-- caso varchar e avatar-->
                            <#elseif fields?api.get(i)?starts_with("avatar")>
                                <#assign picture=true>
                                <#if item?? && item[fields?api.get[i]]??>
                                    <#assign picturePath="${context}/avatars/${item[fields?api.get[i]]}">
                                </#if>
                                <#assign pictureOldName="Current Avatar">
                                <#assign pictureConstraints="style=\"width:125px; height:125px;\"">

                                <!--caso varchar e image-->
                            <#elseif fields?api.get(i)?starts_with("image")>
                                <#assign picture=true>
                                <#if item?? && item[fields?api.get[i]]??>
                                    <#assign picturePath="${context}/images/${item[fields?api.get[i]]}">
                                </#if>
                                <#assign pictureOldName="Current Image">
                                <#assign pictureConstraints="style=\"width:200px; height:200px;\"">
                                <!--caso varchar e icon-->
                            <#elseif fields?api.get(i)?starts_with("icon")>
                                <#assign picture=true>
                                <#if item?? && item[fields?api.get[i]]??>
                                    <#assign picturePath="${context}/icons/${item[fields?api.get[i]]}">
                                </#if>
                                <#assign pictureOldName="Current Icon">
                                <#assign pictureConstraints="style=\"width:125px; height:125px;\"">


                                <!--caso varchar semplice-->
                            <#else>
                                <#assign type="text">
                            </#if>

                            <!--se non matcha con niente lo metto text-->
                        <#else>
                            <#assign type="text">
                        </#if>



                            <#if picture?? && picture==true>
                                <#if item??>
                                <div class="form-group">
                                        <label for="id_old_${fields?api.get(i)}" style="margin-right:15px;" class="col-sm-2 control-label hor-form">${pictureOldName}</label>
                                        <img ${pictureConstraints} src="<#if picturePath??>${picturePath}</#if>" onerror="this.onerror=null;this.src='${context}/images/missing_image.png';">
                                </div>

                                </#if>
                            <div class="form-group">
                                <label for="id_${fields?api.get(i)}" class="col-sm-2 control-label hor-form">Choose a new ${fields?api.get(i)?cap_first}</label>
                                    <div class="col-sm-10">
                                        <input type="file" style="margin-top:10px;" name="${fields?api.get(i)}">
                                    </div>
                            </div>

                                <#assign picture=false>
                                <#assign picturePath="">
                                <#assign pictureOldName="">
                                <#assign pictureConstraints="">
                            <#else>
                                <div class="form-group">


                                <label for="id_${fields?api.get(i)}" class="col-sm-2 control-label hor-form">${fields?api.get(i)?cap_first}</label>
                                <div class="col-sm-10">
                                    <input type="${type}" class="form-control" name="${fields?api.get(i)}" <#if constraints??> ${constraints} </#if> <#if nulls?api.get(i)?starts_with("NO")> required </#if> id="id_${fields?api.get(i)}" placeholder="Insert ${fields?api.get(i)?cap_first}"

                                        <#if item?? && item[fields?api.get(i)]??>
                                           value="${item[fields?api.get(i)]}"
                                        </#if> >
                                </div>
                            </div>

                            </#if>

                    <!--reset variabili-->
                    <#assign type="">
                    <#assign constraints="">

                    <#else>
                        <!--se è una chiave salto-->
                    </#if>

                </#list>


                <!--sezione dedicata ai casi particolari di group-->

                <#if table?starts_with("groups") && mode?starts_with("edit")>

                    <div class="form-group">
                        <label for="selector1" class="col-sm-2 control-label">Choose a service to add or remove</label>
                        <div class="col-sm-8" style="margin-top:10px;">
                            <select name="serviceToAddRemove" id="serviceToAddRemove" class="form-control1">
                            <option value="-1">-</option>
                            <#if servicesList??>
                                <#list servicesList as service>
                                    <option value="${service.id}">${service.name?cap_first}</option>
                                </#list>
                            </#if>

                        </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="radio1" class="col-sm-2 control-label"></label>
                        <div class="col-sm-8">
                            <div class="radio block" style="margin-top:-10px;">
                                <label style="font-size: 80%; margin-right:50px;"><input type="radio" name="servicesRadio" value="add" checked=""> Add</label>
                                <label style="font-size: 80%;"><input type="radio" name="servicesRadio" value="remove"> Remove</label>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="serviceToAdd" class="col-sm-2 control-label">Choose an user to add or remove</label>
                        <div class="col-sm-8" style="margin-top:10px;">
                            <select name="serviceToAdd" id="serviceToAdd" class="form-control1">
                            <option value="-1">-</option>
                            <#if usersList??>
                                <#list usersList as user>
                                    <option value="${user.id}">${user.username}</option>
                                </#list>
                            </#if>

                        </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="radio2" class="col-sm-2 control-label"></label>
                        <div class="col-sm-8">
                            <div class="radio block" style="margin-top:-10px;">
                                <label style="font-size: 80%; margin-right:50px;"><input type="radio" name="usersRadio" checked="" value="add"> Add</label>
                                <label style="font-size: 80%;"><input type="radio" name="usersRadio" value="remove"> Remove</label>
                            </div>
                        </div>
                    </div>

                </#if>



                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10" style="margin-top:20px;">

                        <#if itemId??>

                            <button type="button" id="edit_btn" style="float:left; background-color: #1abc9c; border-color: #1abc9c;" class="btn btn-lg btn-primary edit_btn_hover">Edit</button>

                            <button type="button" id="del_btn" style="float:right; background-color:#d95459; border-color:#d95459;" class="btn btn-lg btn-danger delete_btn_hover" onclick="this.style.display='none'; document.getElementById('confirm_btn').style.display='block';">Delete</button>

                            <button type="button" id="confirm_btn" style="float:right; display:none; border-color:#f0ad4e; background-color:#f0ad4e;" class="btn btn-lg btn-warning warning_11 confirm_btn_hover">Confirm</button>

                        <#else>

                            <button type="button" id="insert_btn" style="float:left; background-color: #1abc9c; border-color: #1abc9c;" class="btn btn-lg btn-success warning_1 edit_btn_hover">Insert</button>

                        </#if>

                    </div>
                </div>
            </form>


        </div>


    <#else>
        <div class="typo-grid">
            <div class="typo-1">
                <div class="grid_3 grid_5">
                    <h4 class="head-top">Info</h4>
                    <div class="but_list">
                        <div class="but_list">
                            <div class="well">
                                Something went wrong :(
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


    <#if itemId??>
    /*---------------------------------------------------*/

    $("#edit_btn").click(function(e) {

        var fade_outAjax = function () {
            $("#messageAjax").fadeOut();
        };
        setTimeout(fade_outAjax, 2750);

        var url = "/doUpdate/${table}/${itemId}";

        $.ajax({
            type: "POST",
            url: url,
            data: $("#my_form").serialize(),
            success: function(data)
            {
                $("body").prepend("<div id='messageAjax' class='div_message_top'><div>");
                var messageAjax;
                $('#messageAjax').addClass("div_message_top_success");
                messageAjax = '<strong>Success!</strong><br><br>The item has been edited.';
                window.scrollTo(0, 0);
                $('#messageAjax')
                        .html(messageAjax)
                        .hide()
                        .fadeIn()
                        .click(function () {
                            $(this).fadeOut();
                        });
            },
            error: function(data)
            {
                $("body").prepend("<div id='messageAjax' class='div_message_top'><div>");
                var messageAjax;
                $('#messageAjax').addClass("div_message_top_warning");
                messageAjax = '<strong>Warning!</strong><br><br>There was a problem with your request.';
                $("html, body").animate({ scrollTop: 0 }, "slow");
                $('#messageAjax')
                        .html(messageAjax)
                        .hide()
                        .fadeIn()
                        .click(function () {
                            $(this).fadeOut();
                        });

            }

        });

        e.preventDefault();

    });


    /*---------------------------------------------*/

    $("#confirm_btn").click(function(e) {

        var fade_outAjax = function () {
            $("#messageAjax").fadeOut();
        };
        setTimeout(fade_outAjax, 2750);

        var url = "/doDelete/${table}/${itemId}";

        $.ajax({
            type: "POST",
            url: url,
            data: $("#my_form").serialize(),
            success: function(data)
            {
                $("body").prepend("<div id='messageAjax' class='div_message_top'><div>");
                var messageAjax;
                $('#messageAjax').addClass("div_message_top_success");
                messageAjax = '<strong>Success!</strong><br><br>The item has been deleted.';
                window.scrollTo(0, 0);
                $('#messageAjax')
                        .html(messageAjax)
                        .hide()
                        .fadeIn()
                        .click(function () {
                            $(this).fadeOut();
                        });
                setTimeout(function(){ window.location = "/report/${table}"; }, 2750);
            },
            error: function(data)
            {
                $("body").prepend("<div id='messageAjax' class='div_message_top'><div>");
                var messageAjax;
                $('#messageAjax').addClass("div_message_top_warning");
                messageAjax = '<strong>Warning!</strong><br><br>There was a problem with your request.';
                $("html, body").animate({ scrollTop: 0 }, "slow");
                $('#messageAjax')
                        .html(messageAjax)
                        .hide()
                        .fadeIn()
                        .click(function () {
                            $(this).fadeOut();
                        });
            }

        });

        e.preventDefault();

    });

    <#else>
    //ajax insert
    $("#insert_btn").click(function(e) {

        var fade_outAjax = function () {
            $("#messageAjax").fadeOut();
        };
        setTimeout(fade_outAjax, 2750);

        var url = "/doInsert/${table}";

        $.ajax({
            type: "POST",
            url: url,
            data: $("#my_form").serialize(),
            success: function(data)
            {
                $("body").prepend("<div id='messageAjax' class='div_message_top'><div>");
                var messageAjax;
                $('#messageAjax').addClass("div_message_top_success");
                messageAjax = '<strong>Success!</strong><br><br>The item has been added.';
                $("html, body").animate({ scrollTop: 0 }, "slow");
                $('#messageAjax')
                        .html(messageAjax)
                        .hide()
                        .fadeIn()
                        .click(function () {
                            $(this).fadeOut();
                        });

            },
            error: function(data)
            {
                $("body").prepend("<div id='messageAjax' class='div_message_top'><div>");
                var messageAjax;
                $('#messageAjax').addClass("div_message_top_warning");
                messageAjax = '<strong>Warning!</strong><br><br>There was a problem with your request.';
                $("html, body").animate({ scrollTop: 0 }, "slow");
                $('#messageAjax')
                        .html(messageAjax)
                        .hide()
                        .fadeIn()
                        .click(function () {
                            $(this).fadeOut();
                        });

            }

        });

        e.preventDefault();


    });
    </#if>


</script>
