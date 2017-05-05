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
                        <#if SecurityUtil.hasPermission('pay_order_edit')><a class="btn red dialog" href="javascript:void(0);" 
                        data-url="${ctx}/pay/order/edit" data-is-modal="" data-modal-width="950" data-table-id="#data-table-order">
                            <i class="fa fa-plus"> 添加支付订单</i>
                        </a></#if>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
            	<form class="form-inline form-search" id="form-search-order">

                        <div class="form-group">
                            <label class="input-label" for="payStatus">支付状态</label>
                        <@albedo.form name="payStatus" searchItem="searchItem" dictCode="order_pay_status" boxType="checkbox" value="${(order.payStatus)!}" operate="eq" attrType="Integer"> </@albedo.form>
                        </div>
                    <br/>
						<div class="form-group">
					        <label class="input-label" for="payCode">支付编码</label>
							<input type="text" class="form-control" searchItem="searchItem" id="payCode" name="payCode" value="${(order.payCode)!}" attrType="String" operate="eq" htmlEscape="false" maxlength="32" placeholder="...">
						</div>
						<div class="form-group">
					        <label class="input-label" for="bizCode">业务编码</label>
							<input type="text" class="form-control" searchItem="searchItem" id="bizCode" name="bizCode" value="${(order.bizCode)!}" attrType="String" operate="eq" htmlEscape="false" maxlength="32" placeholder="...">
						</div>
						<div class="form-group">
					        <label class="input-label" for="payType">支付类型</label>
							<@albedo.form name="payType" searchItem="searchItem" dictCode="order_pay_type" boxType="checkbox" value="${(order.payType)!}" operate="eq" attrType="Integer"> </@albedo.form>
						</div>
						<div class="form-group">
					        <label class="input-label" for="bizType">业务类型</label>
							<@albedo.form name="bizType" searchItem="searchItem" dictCode="order_biz_type" boxType="checkbox" value="${(order.bizType)!}" operate="eq" attrType="Integer"> </@albedo.form>
						</div>
                         <div class="form-group form-btn">
                         <button class="btn btn-sm green btn-outline filter-submit-table-order margin-bottom" type="button"><i class="fa fa-search"></i> 查询</button>
                         <button class="btn btn-sm red btn-outline filter-cancel" type="reset"><i class="fa fa-times"></i> 重置</button>
                         </div>
                     </form>
                     <hr />
              		<div id="bootstrap-alerts"></div>
                    <table class="table table-striped table-bordered table-hover dataTable no-footer dt-responsive" id="data-table-order">
                        <thead>
                        <tr role="row" class="heading">
                        	<th class="all"> 支付编码 </th>
                        	<th class=""> 业务编码 </th>
                        	<th class=""> 金额 </th>
                        	<th class=""> 支付类型 </th>
                        	<th class=""> 业务类型 </th>
                        	<th class=""> 客户端ip </th>
                        	<th class=""> 主题 </th>
                        	<th class=""> 支付调起方式 </th>
                        	<th class="none"> open_id </th>
                        	<th class="none"> 支付状态 </th>
                        	<th class="none"> 创建时间 </th>
                        	<th class="none"> 最后更新时间 </th>
                        <#if SecurityUtil.hasPermission('pay_order_edit,pay_order_delete,pay_order_lock')><th width="10%"> 操作 </th></#if>
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
    var dataOrderTables = function () {
        var initTradeOrderTable = function () {
            var grid = new Datatable();
            grid.init({
                src: $("#data-table-order"),
                dataTable: {
                    "ajax": {
                        "url": "${ctx}/pay/order/page",
                        type: 'GET',
                        "dataType": 'json'
                    },
                    "columns": [
					{data:'payCode'
					, render: function(data, type, row){
						<#if SecurityUtil.hasPermission('pay_order_edit')>data = '<a href="javascript:void(0);" class="dialog" data-table-id="#data-table-order" data-is-modal="" data-url="${ctx}/pay/order/edit?id='+row.id+'" title=\"点击编辑支付订单\">'+data+'</a>'</#if>
						return data;
					}}
					, {data:'bizCode'
					}
					, {data:'amount'
					}
					, {data:'payType'
					}
					, {data:'bizType'
					}
					, {data:'clientIp'
					}
					, {data:'subject'
					}
					, {data:'invokeType'
					}
					, {data:'openId'
					}
					, {data:'payStatus'
					}
					, {data:'createdDate'
					}
					, {data:'lastModifiedDate'
					}
					<#if SecurityUtil.hasPermission('pay_order_edit,pay_order_delete,pay_order_lock')>, 
                       { orderable: false, data: function ( row, type, val, meta ) {
                        	var data = '<span class="operation">'<#if SecurityUtil.hasPermission('pay_order_edit')>+'<a href="javascript:void(0);" class="dialog" data-table-id="#data-table-order" data-is-modal="" data-modal-width="950" data-url="${ctx}/pay/order/edit?id='+row.id+'"><i class=\"fa fa-lg fa-pencil\" title=\"编辑支付订单\"></i></a>'</#if>
                    		<#if SecurityUtil.hasPermission('pay_order_lock')>+'<a href="javascript:void(0);" class="confirm" data-table-id="#data-table-order" data-title="你确认要操作选中的支付订单吗？" data-url="${ctx}/pay/order/lock/'+row.id+'"><i class=\"fa fa-lg fa-'+(row.status=="正常" ? "unlock" : "lock") +'  font-yellow-gold\" title=\"'+(row.status=="正常" ? "锁定" : "解锁") +'支付订单\"></i></a></span>'</#if>
                    		<#if SecurityUtil.hasPermission('pay_order_delete')>+'<a href="javascript:void(0);" class="confirm" data-table-id="#data-table-order" data-method="delete" data-title="你确认要删除选中的支付订单吗？" data-url="${ctx}/pay/order/delete/'+row.id+'"><i class=\"fa fa-lg fa-trash-o font-red-mint\" title=\"删除\"></i></a></span>'</#if>+'<\span>';
                        	return data;
                        }
                        }</#if>
                    ]
                }
            });
            $(".filter-submit-table-order").click(function(){
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
        dataOrderTables.init();
    });
</script>