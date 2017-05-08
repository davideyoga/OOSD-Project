<div class="content-main" xmlns="http://www.w3.org/1999/html">

    <!--content-->
    <div class="content-top" style="padding:0px;">

        <#if items?? && fields??>

            <table id="report-pippo" class="display" cellspacing="0" width="100%">
                <thead>
                <tr>
                <#list fields as field>
                      <th>${field?cap_first}</th>
                </#list>
                </tr>
                </thead>
                <tfoot>
                <tr>
                    <#list fields as field>
                        <th>${field?cap_first}</th>
                    </#list>
                </tr>
                </tfoot>
                <tbody>
                <#list items as item>
                    <tr onclick="document.location = '/edit/${table}/${item.id}';">
                        <#list fields as field>
                            <td>${item[field]}</td>
                        </#list>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>

    </div>
    <!--//content-->

<#include "footer.ftl">
</div>

<div class="clearfix"> </div>


<script>
    $(document).ready(function() {
        $('#report-pippo').DataTable();
    });
</script>



