<div class="content-main" xmlns="http://www.w3.org/1999/html">


    <!--content-->
    <div class="content-top" style="padding:5px 20px 20px 20px;">

    <#if fields?? && keys?? && nulls?? && types?? && defaults?? && extras?? && table??>


        <div class="grid-form1">
            <h3 id="forms-horizontal" style="padding:20px;">${mode?cap_first} ${table?cap_first}</h3>

            <form class="form-horizontal" method="POST" enctype="multipart/form-data" id="formPippo">

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
                                    <#assign picturePath="${context}/images/${item[fields?api.get[i]]}">
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
                                    <label for="id_old_${fields?api.get(i)}" style="margin-right:15px;"
                                           class="col-sm-2 control-label hor-form">${pictureOldName}</label>
                                    <img ${pictureConstraints} src="<#if picturePath??>${picturePath}</#if>"
                                                               onerror="this.onerror=null;this.src='${context}/images/missing_image.png';">
                                </div>

                            </#if>
                            <div class="form-group">
                                <label for="id_${fields?api.get(i)}" class="col-sm-2 control-label hor-form">Choose a
                                    new ${fields?api.get(i)?cap_first}</label>
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


                                <label for="id_${fields?api.get(i)}"
                                       class="col-sm-2 control-label hor-form">${fields?api.get(i)?cap_first}</label>
                                <div class="col-sm-10">
                                    <input type="${type}" class="form-control"
                                           name="${fields?api.get(i)}" <#if constraints??> ${constraints} </#if> <#if nulls?api.get(i)?starts_with("NO")>
                                           required </#if> id="id_${fields?api.get(i)}"
                                           placeholder="Insert ${fields?api.get(i)?cap_first}"

                                        <#if item?? && item[fields?api.get(i)]?? && !type?starts_with("password")>
                                            <#if type?starts_with("number")>
                                           value="${item[fields?api.get(i)]?replace(",","")}"

                                            <#else>
                                           value="${item[fields?api.get(i)]}"

                                            </#if>
                                        </#if>>
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
                                <#if servicesNotInGroup??>
                                    <#list servicesNotInGroup as service>
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
                                <label style="font-size: 80%; margin-right:50px;" id="serviceRadioAdd"><input
                                        type="radio"

                                        name="servicesRadio"
                                        value="add" checked="">
                                    Add</label>
                                <label style="font-size: 80%;" id="serviceRadioRemove"><input type="radio"
                                                                                              name="servicesRadio"
                                                                                              value="remove">
                                    Remove</label>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="serviceToAdd" class="col-sm-2 control-label">Choose an user to add or remove</label>
                        <div class="col-sm-8" style="margin-top:10px;">
                            <select name="userToAddRemove" id="userToAddRemove" class="form-control1">
                                <option value="-1">-</option>
                                <#if usersNotInGroup??>
                                    <#list usersNotInGroup as user>
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
                                <label style="font-size: 80%; margin-right:50px;" id="usersRadioAdd"><input type="radio"
                                                                                                            name="usersRadio"

                                                                                                            checked=""
                                                                                                            value="add">
                                    Add</label>
                                <label style="font-size: 80%;" id="usersRadioRemove"><input type="radio"
                                                                                            name="usersRadio"
                                                                                            value="remove">
                                    Remove</label>
                            </div>
                        </div>
                    </div>

                </#if>


                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10" style="margin-top:20px;">

                        <#if itemId??>

                            <button type="submit" id="edit_btn"
                                    style="float:left; background-color: #1abc9c; border-color: #1abc9c;"
                                    class="btn btn-lg btn-primary edit_btn_hover">Edit
                            </button>

                            <button type="button" id="del_btn"
                                    style="float:right; background-color:#d95459; border-color:#d95459;"
                                    class="btn btn-lg btn-danger delete_btn_hover"
                                    onclick="this.style.display='none'; document.getElementById('confirm_btn').style.display='block';">
                                Delete
                            </button>

                            <button type="submit" id="confirm_btn"
                                    style="float:right; display:none; border-color:#f0ad4e; background-color:#f0ad4e;"
                                    class="btn btn-lg btn-warning warning_11 confirm_btn_hover">Confirm
                            </button>

                        <#else>

                            <button type="button" id="insert_btn"
                                    style="float:left; background-color: #1abc9c; border-color: #1abc9c;"
                                    class="btn btn-lg btn-success warning_1 edit_btn_hover">Insert
                            </button>

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

<div class="clearfix"></div>
<script>

    <#if itemId??>

    $("#edit_btn").click(function (e) {

        e.preventDefault();

        // Get form
        var pippoForm = $('#formPippo')[0];

        // Create an FormData object
        var pippoData = new FormData(pippoForm);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/doUpdate/${table}/${itemId}",
            data: pippoData,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {

                ajaxFunction(data, "edit", "success");


            },
            error: function (data) {
                ajaxFunction(data, "edit", "error");

            }

        });

    });


    /*---------------------------------------------*/

    $("#confirm_btn").click(function (e) {

        e.preventDefault();

        // Get form
        var pippoForm = $('#formPippo')[0];

        // Create an FormData object
        var pippoData = new FormData(pippoForm);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/doDelete/${table}/${itemId}",
            data: pippoData,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data, textStatus) {

                ajaxFunction(data, "del", textStatus, "success");


            },
            error: function (data, textStatus) {
                ajaxFunction(data, "del", textStatus, "error");

            }

        });
    });

    <#else>
    //ajax insert
    $("#insert_btn").click(function (e) {

        e.preventDefault();

        // Get form
        var pippoForm = $('#formPippo')[0];

        // Create an FormData object
        var pippoData = new FormData(pippoForm);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/doInsert/${table}",
            data: pippoData,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data, textStatus) {

                ajaxFunction(data, "add", textStatus, "success");


            },
            error: function (data, textStatus) {

                ajaxFunction(data, "add", textStatus, "error");

            }

        });

    });
    </#if>

    function ajaxFunction(data, type, outcome) {
        var fade_outAjax = function () {
            $("#messageAjax").fadeOut();
        };
        //durata per la quale il messaggio è visibile
        setTimeout(fade_outAjax, 2750);
        var divClass = "success";
        var divText = "Your request has been completed successfully.";
        var divTitle = divClass.charAt(0).toUpperCase() + divClass.slice(1);

        //se c'è stato un errore nella richiesta ajax oppure un errore servlet
        if (outcome == "error" || data == "KO") {
            divClass = "warning";
            divTitle = divClass.charAt(0).toUpperCase() + divClass.slice(1);
            divText = "There was a problem with your request."
        }

        //inietto il messaggio
        $("body").prepend("<div id='messageAjax' class='div_message_top div_message_top_" + divClass + "'" + "><div>");
        var messageAjax;
        messageAjax = '<strong>' + divTitle + '</strong><br><br>' + divText;

        //mostro il messaggio
        $('#messageAjax')
                .html(messageAjax)
                .hide()
                .fadeIn()
                .click(function () {
                    $(this).fadeOut();
                });
        //scrollo al top con animazione
        $("html, body").animate({scrollTop: 0}, "fast");

        //se era un'eliminazione ed è avvenuta con successo redirect a report
        if (type == "del" && outcome == "success" && data == "OK") {
            //eliminazione avvenuta con successo, redirect a report
            window.setTimeout(function () {
                window.location.href = "/report/${table}";
            }, 3250);
        }
        if (type == "add" && outcome == "success" && data == "OK") {
            //inserimento avvenuto con successo, redirect a report
            window.setTimeout(function () {
                window.location.href = "/add/${table}";
            }, 3250);
        }

    }

</script>
<script>
    <#if table?? && table?starts_with("user")>
        <#assign disable="true">
        <#list services as service>
            <#if service.name?starts_with("user")>
                <#assign disable="false">
                <#break>
            </#if>
        </#list>

    var username = "${user.username}";
    if (username === document.getElementById("id_username").getAttribute("value") && ${disable}) {
        document.getElementById("id_exp").setAttribute("readonly", "true");
    }
    </#if>
</script>

<#if servicesInGroup?? && servicesNotInGroup?? && usersInGroup?? && usersNotInGroup??>
<script>
    function capFirst(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }

    var servicesNotInGroup = [<#list servicesNotInGroup as service>[${service.id}, "${service.name}"]<#if service?has_next>,</#if></#list>];

    var usersInGroup = [<#list usersInGroup as user>[${user.id}, "${user.username}"]<#if user?has_next>,</#if></#list>];

    var usersNotInGroup = [<#list usersNotInGroup as user>[${user.id}, "${user.username}"]<#if user?has_next>,</#if></#list>];

    var servicesInGroup = [<#list servicesInGroup as service>[${service.id}, "${service.name}"]<#if service?has_next>,</#if></#list>];


    $('#serviceRadioAdd').click(function () {
        $('#serviceToAddRemove').find('option:not(:first)').remove();
        $.each(servicesNotInGroup, function (key, value) {
            $('#serviceToAddRemove')
                    .append($("<option></option>")
                            .attr("value", servicesNotInGroup[key][0])
                            .text(capFirst(servicesNotInGroup[key][1])));
        });
    });

    $('#serviceRadioRemove').click(function () {
        $('#serviceToAddRemove').find('option:not(:first)').remove();
        $.each(servicesInGroup, function (key, value) {
            $('#serviceToAddRemove')
                    .append($("<option></option>")
                            .attr("value", servicesInGroup[key][0])
                            .text(capFirst(servicesInGroup[key][1])));
        });
    });

    $('#usersRadioAdd').click(function () {
        $('#userToAddRemove').find('option:not(:first)').remove();
        $.each(usersNotInGroup, function (key, value) {
            $('#userToAddRemove')
                    .append($("<option></option>")
                            .attr("value", usersNotInGroup[key][0])
                            .text(capFirst(usersNotInGroup[key][1])));
        });
    });

    $('#usersRadioRemove').click(function () {
        $('#userToAddRemove').find('option:not(:first)').remove();
        $.each(usersInGroup, function (key, value) {
            $('#userToAddRemove')
                    .append($("<option></option>")
                            .attr("value", usersInGroup[key][0])
                            .text(capFirst(usersInGroup[key][1])));
        });
    });

</script>
</#if>
