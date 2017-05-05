<div class="portlet light bordered">
	<div class="portlet-title">
		<div class="caption font-blue">
            <i class="fa fa-globe font-blue"></i><#if orderAmountLog.id??>编辑<#else>添加</#if>订单日志
        </div>
		<div class="actions">
			<div class="btn-group">
                <a id="orderAmountLog_list" class="btn red list" href="javascript:void(0);" data-table-id="#data-table-orderAmountLog">
                    <i class="fa fa-list"> 订单日志列表</i>
                </a>
            </div>
		</div>
	</div>
	<div class="portlet-body form form-no-modal">
		<!-- BEGIN FORM-->
		<div id="bootstrap-alerts"></div>
		<form id="ajax-form-orderAmountLog" action="${ctx}/pay/orderAmountLog/edit" method="post"
			class="form-horizontal form-validation form-bordered form-label-stripped"
			config="{rules:{
						},
                       messages:{
					   }}}">
			<div class="form-body">
				<input type="hidden" name="id" value="${(orderAmountLog.id)!}" />
				<div class="form-group">
					<label class="control-label col-md-3">支付id<span class="required">*</span></label>
					<div class="col-md-5">
						<input type="text" name="orderId" id="orderId" class="form-control required" value="${(orderAmountLog.orderId)!}" htmlEscape="false" maxlength="32">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">类型<span class="required">*</span></label>
					<div class="col-md-5">
						<@albedo.form name="type" dictCode="order_amout_type" cssClass="required" boxType="select" value="${(orderAmountLog.type)!}" > </@albedo.form>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">改变前金额<span class="required">*</span></label>
					<div class="col-md-5">
						<input type="text" name="before" id="before" class="form-control required number" value="${(orderAmountLog.before)!}" htmlEscape="false">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">改变后金额<span class="required">*</span></label>
					<div class="col-md-5">
						<input type="text" name="after" id="after" class="form-control required number" value="${(orderAmountLog.after)!}" htmlEscape="false">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">描述</label>
					<div class="col-md-5">
						<textarea name="description" rows="5" maxlength="1024" class="form-control input-xxlarge ">${(orderAmountLog.description)! }</textarea>
					</div>
				</div>
				<div class="form-actions">
                   <div class="row">
                       <div class="col-md-offset-3 col-md-9">
                           <button type="button" class="btn add green">
                               <i class="fa fa-check"></i> 保存</button>
                           <button type="reset" class="btn default">重置</button>
                           <button type="button" class="btn list" data-table-id="#data-table-orderAmountLog">返回</button>
                       </div>
                   </div>
               </div>
			</div>
		</form>
	</div>
</div>
