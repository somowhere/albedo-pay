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
                        <#if SecurityUtil.hasPermission('pay_orderArgs_edit')><a class="btn red dialog" href="javascript:void(0);" 
                        data-url="${ctx}/pay/orderArgs/edit" data-is-modal="" data-modal-width="950" data-table-id="#data-table-orderArgs">
                            <i class="fa fa-plus"> 添加支付参数</i>
                        </a></#if>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
            	<form class="form-inline form-search" id="form-search-orderArgs">
						<div class="form-group">
					        <label class="input-label" for="name">名称</label>
							<input type="text" class="form-control" searchItem="searchItem" id="name" name="name" value="${(orderArgs.name)!}" attrType="String" operate="like" htmlEscape="false" maxlength="64" placeholder="...">
						</div>
                         <div class="form-group form-btn">
                         <button class="btn btn-sm green btn-outline filter-submit-table-orderArgs margin-bottom" type="button"><i class="fa fa-search"></i> 查询</button>
                         <button class="btn btn-sm red btn-outline filter-cancel" type="reset"><i class="fa fa-times"></i> 重置</button>
                         </div>
                     </form>
                     <hr />
              		<div id="bootstrap-alerts"></div>
                    <table class="table table-striped table-bordered table-hover dataTable no-footer dt-responsive" id="data-table-orderArgs">
                        <thead>
                        <tr role="row" class="heading">
                        	<th class="all"> 名称 </th>
                        	<th class=""> 参数类型 </th>
                        	<th class=""> 支付类型 </th>
                        	<th class=""> 业务类型 </th>
                        	<th class=""> 创建时间 </th>
                        	<th class=""> 最后更新时间 </th>
                        <#if SecurityUtil.hasPermission('pay_orderArgs_edit,pay_orderArgs_delete,pay_orderArgs_lock')><th width="10%"> 操作 </th></#if>
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
    var dataOrderArgsTables = function () {
        var initTradeOrderTable = function () {
            var grid = new Datatable();
            grid.init({
                src: $("#data-table-orderArgs"),
                dataTable: {
                    "ajax": {
                        "url": "${ctx}/pay/orderArgs/page",
                        type: 'GET',
                        "dataType": 'json'
                    },
                    "columns": [
					{data:'name'
					, render: function(data, type, row){
						<#if SecurityUtil.hasPermission('pay_orderArgs_edit')>data = '<a href="javascript:void(0);" class="dialog" data-table-id="#data-table-orderArgs" data-is-modal="" data-url="${ctx}/pay/orderArgs/edit?id='+row.id+'" title=\"点击编辑支付参数\">'+data+'</a>'</#if>
						return data;
					}}
					, {data:'argsType'
					}
					, {data:'payType'
					}
					, {data:'bizType'
					}
					, {data:'createdDate'
					}
					, {data:'lastModifiedDate'
					}
					<#if SecurityUtil.hasPermission('pay_orderArgs_edit,pay_orderArgs_delete,pay_orderArgs_lock')>, 
                       { orderable: false, data: function ( row, type, val, meta ) {
                        	var data = '<span class="operation">'<#if SecurityUtil.hasPermission('pay_orderArgs_edit')>+'<a href="javascript:void(0);" class="dialog" data-table-id="#data-table-orderArgs" data-is-modal="" data-modal-width="950" data-url="${ctx}/pay/orderArgs/edit?id='+row.id+'"><i class=\"fa fa-lg fa-pencil\" title=\"编辑支付参数\"></i></a>'</#if>
                    		<#if SecurityUtil.hasPermission('pay_orderArgs_lock')>+'<a href="javascript:void(0);" class="confirm" data-table-id="#data-table-orderArgs" data-title="你确认要操作选中的支付参数吗？" data-url="${ctx}/pay/orderArgs/lock/'+row.id+'"><i class=\"fa fa-lg fa-'+(row.status=="正常" ? "unlock" : "lock") +'  font-yellow-gold\" title=\"'+(row.status=="正常" ? "锁定" : "解锁") +'支付参数\"></i></a></span>'</#if>
                    		<#if SecurityUtil.hasPermission('pay_orderArgs_delete')>+'<a href="javascript:void(0);" class="confirm" data-table-id="#data-table-orderArgs" data-method="delete" data-title="你确认要删除选中的支付参数吗？" data-url="${ctx}/pay/orderArgs/delete/'+row.id+'"><i class=\"fa fa-lg fa-trash-o font-red-mint\" title=\"删除\"></i></a></span>'</#if>+'<\span>';
                        	return data;
                        }
                        }</#if>
                    ]
                }
            });
            $(".filter-submit-table-orderArgs").click(function(){
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
        dataOrderArgsTables.init();
    });
</script>