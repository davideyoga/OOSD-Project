<div class="content-main" style="padding-top:0px;" xmlns="http://www.w3.org/1999/html">


    <!--content-->
    <div class="content-top" style="padding-top:0px;">

        <div class="panel-body" style="padding-top:0px;">
            <!-- tables -->

            <div class="agile-tables">
                <div class="typo-grid">
                    <div class="typo-1">
                    <#if items?? && fields??>

                        <table id="report" class="table table-striped table-bordered" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <#list fields as field>
                                    <th>${field?cap_first}</th>
                                </#list>
                            </tr>
                            </thead>

                            <tbody>
                                <#list items as item>
                                <tr onclick="document.location = '/edit/${table}/${item.id}';">
                                    <#list fields as field>
                                        <td>
                                            <#if item[field]??>
                                                ${item[field]}
                                            <#else>
                                                null or not available
                                            </#if>
                                        </td>
                                    </#list>
                                </tr>
                                </#list>
                            </tbody>
                        </table>

                    <#else >
                        <div class="grid_3 grid_5">
                            <h4 class="head-top">Info</h4>
                            <div class="but_list">
                                <div class="but_list">
                                    <div class="well">
                                        There are no items available.
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#if>

                    </div>
                </div>
            </div>

        </div>
    </div>


    <!--//content-->

<#include "footer.ftl">
</div>

<div class="clearfix"></div>


<script>
    $(document).ready(function () {
        $('#report').DataTable();
    });
</script>



