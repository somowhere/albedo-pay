package com.qingju.java.modules.pay.web;

import com.albedo.java.common.security.SecurityUtil;
import com.albedo.java.util.StringUtil;
import com.google.common.collect.Lists;
import com.qingju.java.modules.pay.domain.OrderLog;
import com.qingju.java.modules.pay.service.OrderLogService;
import com.albedo.java.util.JsonUtil;
import com.albedo.java.util.PublicUtil;
import com.albedo.java.util.domain.Globals;
import com.albedo.java.util.domain.PageModel;
import com.albedo.java.util.exception.RuntimeMsgException;
import com.albedo.java.web.rest.ResultBuilder;
import com.albedo.java.web.rest.base.DataResource;
import com.alibaba.fastjson.JSON;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 订单日志Controller 订单日志
 * @author lj
 * @version 2017-05-05
 */
@Controller
@RequestMapping(value = "${albedo.adminPath}/pay/orderLog")
public class OrderLogResource extends DataResource<OrderLogService, OrderLog> {
	
	@ModelAttribute
	public OrderLog get(@RequestParam(required = false) String id) throws Exception {
		String path = request.getRequestURI();
		if (path!=null && !path.contains("checkBy") && !path.contains("find") && PublicUtil.isNotEmpty(id)) {
			return service.findOne(id);
		} else {
			return new OrderLog();
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public String list() {
		return "mappings/modules/pay/orderLogList";
	}

	/**
	 * GET / : get all orderLog.
	 * 
	 * @param pm
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and with body all orderLog
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity getPage(PageModel<OrderLog> pm) {
	    service.findPage(pm, SecurityUtil.dataScopeFilter());
		JSON rs = JsonUtil.getInstance().setRecurrenceStr().toJsonObject(pm);
		return ResultBuilder.buildObject(rs);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public String form(OrderLog orderLog) {
		if(orderLog == null){
			throw new RuntimeMsgException(PublicUtil.toAppendStr("查询订单日志失败，原因：无法查找到编号为[", request.getParameter("id"), "]的订单日志"));
		}
		return "mappings/modules/pay/orderLogForm";
	}

	/**
	 * POST / : Save a orderLog.
	 *
	 * @param orderLog the HTTP orderLog
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity save(OrderLog orderLog) {
		log.debug("REST request to save OrderLog : {}", orderLog);
		service.save(orderLog);
		return ResultBuilder.buildOk("保存订单日志成功");

	}

	/**
	 * DELETE //:id : delete the "id" OrderLog.
	 *
	 * @param ids the id of the orderLog to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/delete/{ids:" + Globals.LOGIN_REGEX
			+ "}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity delete(@PathVariable String ids) {
		log.debug("REST request to delete OrderLog: {}", ids);
		service.delete(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT)));
		return ResultBuilder.buildOk("删除订单日志成功");
	}
	/**
	 * lock //:id : lockOrUnLock the "id" OrderLog.
	 *
	 * @param ids the id of the orderLog to lock
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/lock/{ids:" + Globals.LOGIN_REGEX
			+ "}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity lockOrUnLock(@PathVariable String ids) {
		log.debug("REST request to lockOrUnLock OrderLog: {}", ids);
		service.lockOrUnLock(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT)));
		return ResultBuilder.buildOk("操作订单日志成功");
	}

}