<div class="portlet light bordered">
	<div class="portlet-title">
		<div class="caption font-blue">
            <i class="fa fa-globe font-blue"></i><#if orderArgs.id??>编辑<#else>添加</#if>支付参数
        </div>
		<div class="actions">
			<div class="btn-group">
                <a id="orderArgs_list" class="btn red list" href="javascript:void(0);" data-table-id="#data-table-orderArgs">
                    <i class="fa fa-list"> 支付参数列表</i>
                </a>
            </div>
		</div>
	</div>
	<div class="portlet-body form form-no-modal">
		<!-- BEGIN FORM-->
		<div id="bootstrap-alerts"></div>
		<form id="ajax-form-orderArgs" action="${ctx}/pay/orderArgs/edit" method="post"
			class="form-horizontal form-validation form-bordered form-label-stripped"
			config="{rules:{
						},
                       messages:{
					   }}}">
			<div class="form-body">
				<input type="hidden" name="id" value="${(orderArgs.id)!}" />
				<div class="form-group">
					<label class="control-label col-md-3">名称<span class="required">*</span></label>
					<div class="col-md-5">
						<input type="text" name="name" id="name" class="form-control required" value="${(orderArgs.name)!}" htmlEscape="false" maxlength="64">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">参数类型<span class="required">*</span></label>
					<div class="col-md-5">
						<@albedo.form name="argsType" dictCode="pay_args" cssClass="required" boxType="select" value="${(orderArgs.argsType)!}" > </@albedo.form>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">支付类型<span class="required">*</span></label>
					<div class="col-md-5">
						<@albedo.form name="payType" dictCode="order_pay_type" cssClass="required" boxType="select" value="${(orderArgs.payType)!}" > </@albedo.form>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">业务类型<span class="required">*</span></label>
					<div class="col-md-5">
						<@albedo.form name="bizType" dictCode="order_biz_type" cssClass="required" boxType="select" value="${(orderArgs.bizType)!}" > </@albedo.form>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">描述</label>
					<div class="col-md-5">
						<textarea name="description" rows="5" maxlength="1024" class="form-control input-xxlarge ">${(orderArgs.description)! }</textarea>
					</div>
				</div>
				<div class="form-actions">
                   <div class="row">
                       <div class="col-md-offset-3 col-md-9">
                           <button type="button" class="btn add green">
                               <i class="fa fa-check"></i> 保存</button>
                           <button type="reset" class="btn default">重置</button>
                           <button type="button" class="btn list" data-table-id="#data-table-orderArgs">返回</button>
                       </div>
                   </div>
               </div>
			</div>
		</form>
	</div>
</div>
