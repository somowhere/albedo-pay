<div class="row">
    <div class="col-md-12">
        <!-- BEGIN EXAMPLE TABLE PORTLET-->
        <div class="portlet light bordered">
            <div class="portlet-title">
                <div class="caption font-blue">
                    <i class="fa fa-globe font-blue"></i>数据列表
                </div>
                <div class="actions">
                    <div class="btn-group">
                        <#if SecurityUtil.hasPermission('pay_orderAmountLog_edit')><a class="btn red dialog" href="javascript:void(0);" 
                        data-url="${ctx}/pay/orderAmountLog/edit" data-is-modal="" data-modal-width="950" data-table-id="#data-table-orderAmountLog">
                            <i class="fa fa-plus"> 添加订单日志</i>
                        </a></#if>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
            	<form class="form-inline form-search" id="form-search-orderAmountLog">
						<div class="form-group">
					        <label class="input-label" for="orderId">支付id</label>
							<input type="text" class="form-control" searchItem="searchItem" id="orderId" name="orderId" value="${(orderAmountLog.orderId)!}" attrType="String" operate="like" htmlEscape="false" maxlength="32" placeholder="...">
						</div>
						<div class="form-group">
					        <label class="input-label" for="type">类型</label>
							<@albedo.form name="type" searchItem="searchItem" dictCode="order_amout_type" boxType="select" value="${(orderAmountLog.type)!}" operate="eq" attrType="Integer"> </@albedo.form>
						</div>
                         <div class="form-group form-btn">
                         <button class="btn btn-sm green btn-outline filter-submit-table-orderAmountLog margin-bottom" type="button"><i class="fa fa-search"></i> 查询</button>
                         <button class="btn btn-sm red btn-outline filter-cancel" type="reset"><i class="fa fa-times"></i> 重置</button>
                         </div>
                     </form>
                     <hr />
              		<div id="bootstrap-alerts"></div>
                    <table class="table table-striped table-bordered table-hover dataTable no-footer dt-responsive" id="data-table-orderAmountLog">
                        <thead>
                        <tr role="row" class="heading">
                        	<th class="all"> 支付id </th>
                        	<th class=""> 类型 </th>
                        	<th class=""> 改变前金额 </th>
                        	<th class=""> 改变后金额 </th>
                        	<th class=""> 创建时间 </th>
                        	<th class=""> 最后更新时间 </th>
                        <#if SecurityUtil.hasPermission('pay_orderAmountLog_edit,pay_orderAmountLog_delete,pay_orderAmountLog_lock')><th width="10%"> 操作 </th></#if>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
              </div>
        </div>
        <!-- END EXAMPLE TABLE PORTLET-->
    </div>
</div>
<!-- END PAGE BASE CONTENT -->
<script type="text/javascript">
    var dataOrderAmountLogTables = function () {
        var initTradeOrderTable = function () {
            var grid = new Datatable();
            grid.init({
                src: $("#data-table-orderAmountLog"),
                dataTable: {
                    "ajax": {
                        "url": "${ctx}/pay/orderAmountLog/page",
                        type: 'GET',
                        "dataType": 'json'
                    },
                    "columns": [
					{data:'orderId'
					, render: function(data, type, row){
						<#if SecurityUtil.hasPermission('pay_orderAmountLog_edit')>data = '<a href="javascript:void(0);" class="dialog" data-table-id="#data-table-orderAmountLog" data-is-modal="" data-url="${ctx}/pay/orderAmountLog/edit?id='+row.id+'" title=\"点击编辑订单日志\">'+data+'</a>'</#if>
						return data;
					}}
					, {data:'type'
					}
					, {data:'before'
					}
					, {data:'after'
					}
					, {data:'createdDate'
					}
					, {data:'lastModifiedDate'
					}
					<#if SecurityUtil.hasPermission('pay_orderAmountLog_edit,pay_orderAmountLog_delete,pay_orderAmountLog_lock')>, 
                       { orderable: false, data: function ( row, type, val, meta ) {
                        	var data = '<span class="operation">'<#if SecurityUtil.hasPermission('pay_orderAmountLog_edit')>+'<a href="javascript:void(0);" class="dialog" data-table-id="#data-table-orderAmountLog" data-is-modal="" data-modal-width="950" data-url="${ctx}/pay/orderAmountLog/edit?id='+row.id+'"><i class=\"fa fa-lg fa-pencil\" title=\"编辑订单日志\"></i></a>'</#if>
                    		<#if SecurityUtil.hasPermission('pay_orderAmountLog_lock')>+'<a href="javascript:void(0);" class="confirm" data-table-id="#data-table-orderAmountLog" data-title="你确认要操作选中的订单日志吗？" data-url="${ctx}/pay/orderAmountLog/lock/'+row.id+'"><i class=\"fa fa-lg fa-'+(row.status=="正常" ? "unlock" : "lock") +'  font-yellow-gold\" title=\"'+(row.status=="正常" ? "锁定" : "解锁") +'订单日志\"></i></a></span>'</#if>
                    		<#if SecurityUtil.hasPermission('pay_orderAmountLog_delete')>+'<a href="javascript:void(0);" class="confirm" data-table-id="#data-table-orderAmountLog" data-method="delete" data-title="你确认要删除选中的订单日志吗？" data-url="${ctx}/pay/orderAmountLog/delete/'+row.id+'"><i class=\"fa fa-lg fa-trash-o font-red-mint\" title=\"删除\"></i></a></span>'</#if>+'<\span>';
                        	return data;
                        }
                        }</#if>
                    ]
                }
            });
            $(".filter-submit-table-orderAmountLog").click(function(){
            	grid.submitFilter();
            })
        };
        return {
            init: function () {
                if (!jQuery().dataTable) {
                    return;
                }
                initTradeOrderTable();
            }
        };
    }();
    jQuery(document).ready(function() {
        dataOrderAmountLogTables.init();
    });
</script>