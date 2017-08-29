<div class="portlet light bordered">
	<div class="portlet-title">
		<div class="caption font-blue">
            <i class="fa fa-globe font-blue"></i><#if order.id??>编辑<#else>添加</#if>支付订单
        </div>
		<div class="actions">
			<div class="btn-group">
                <a id="order_list" class="btn red list" href="javascript:void(0);" data-table-id="#data-table-order">
                    <i class="fa fa-list"> 支付订单列表</i>
                </a>
            </div>
		</div>
	</div>
	<div class="portlet-body form form-no-modal">
		<!-- BEGIN FORM-->
		<div id="bootstrap-alerts"></div>
		<form id="ajax-form-order" action="${ctx}/pay/order/edit" method="post"
			class="form-horizontal form-validation form-bordered form-label-stripped"
			config="{rules:{
						},
                       messages:{
					   }}}">
			<div class="form-body">
				<input type="hidden" name="id" value="${(order.id)!}" />
				<div class="form-group">
					<label class="control-label col-md-3">支付编码<span class="required">*</span></label>
					<div class="col-md-5">
						<input type="text" name="payCode" id="payCode" class="form-control required" value="${(order.payCode)!}" htmlEscape="false" maxlength="32">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">业务编码<span class="required">*</span></label>
					<div class="col-md-5">
						<input type="text" name="bizCode" id="bizCode" class="form-control required" value="${(order.bizCode)!}" htmlEscape="false" maxlength="32">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">第三方交易编号<span class="required">*</span></label>
					<div class="col-md-5">
						<input type="text" name="tradeId" id="tradeId" class="form-control required" value="${(order.tradeId)!}" htmlEscape="false" maxlength="128">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">金额<span class="required">*</span></label>
					<div class="col-md-5">
						<input type="text" name="amount" id="amount" class="form-control required number" value="${(order.amount)!}" htmlEscape="false">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">支付类型<span class="required">*</span></label>
					<div class="col-md-5">
						<@albedo.form name="payType" dictCode="order_pay_type" cssClass="required" boxType="select" value="${(order.payType)!}" > </@albedo.form>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">业务类型<span class="required">*</span></label>
					<div class="col-md-5">
						<@albedo.form name="bizType" dictCode="order_biz_type" cssClass="required" boxType="select" value="${(order.bizType)!}" > </@albedo.form>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">client_ip</label>
					<div class="col-md-5">
						<input type="text" name="clientIp" id="clientIp" class="form-control" value="${(order.clientIp)!}" htmlEscape="false" maxlength="32">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">subject_<span class="required">*</span></label>
					<div class="col-md-5">
						<input type="text" name="subject" id="subject" class="form-control required" value="${(order.subject)!}" htmlEscape="false" maxlength="32">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">支付调起方式</label>
					<div class="col-md-5">
						<@albedo.form name="invokeType" dictCode="invoke_type" cssClass="" boxType="radio" value="${(order.invokeType)!}" > </@albedo.form>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">订单完成时间</label>
					<div class="col-md-5">
						<div class="input-group date"><input type="text" name="finishTime" readonly class="form-control date-time-picker " value="${(order.finishTime)!}"><span class="input-group-btn"><button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button></span></div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">open_id</label>
					<div class="col-md-5">
						<input type="text" name="openId" id="openId" class="form-control" value="${(order.openId)!}" htmlEscape="false" maxlength="128">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">附加参数</label>
					<div class="col-md-5">
						<input type="text" name="attach" id="attach" class="form-control" value="${(order.attach)!}" htmlEscape="false" maxlength="255">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">支付状态<span class="required">*</span></label>
					<div class="col-md-5">
						<@albedo.form name="payStatus" dictCode="order_pay_status" cssClass="required" boxType="select" value="${(order.payStatus)!}" > </@albedo.form>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">描述</label>
					<div class="col-md-5">
						<textarea name="description" rows="5" maxlength="1024" class="form-control input-xxlarge ">${(order.description)! }</textarea>
					</div>
				</div>
				<div class="form-actions">
                   <div class="row">
                       <div class="col-md-offset-3 col-md-9">
                           <button type="button" class="btn add green">
                               <i class="fa fa-check"></i> 保存</button>
                           <button type="reset" class="btn default">重置</button>
                           <button type="button" class="btn list" data-table-id="#data-table-order">返回</button>
                       </div>
                   </div>
               </div>
			</div>
		</form>
	</div>
</div>
