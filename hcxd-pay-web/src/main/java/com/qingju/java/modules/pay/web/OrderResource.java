package com.qingju.java.modules.pay.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.albedo.java.common.security.SecurityUtil;
import com.albedo.java.util.JsonUtil;
import com.albedo.java.util.PublicUtil;
import com.albedo.java.util.StringUtil;
import com.albedo.java.util.domain.Globals;
import com.albedo.java.util.domain.PageModel;
import com.albedo.java.util.exception.RuntimeMsgException;
import com.albedo.java.web.rest.ResultBuilder;
import com.albedo.java.web.rest.base.DataResource;
import com.alibaba.fastjson.JSON;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import com.qingju.java.pay.domain.Order;
import com.qingju.java.pay.service.OrderService;

/**
 * 支付订单Controller 支付订单
 * @author lijie
 * @version 2017-05-05
 */
@Controller
@RequestMapping(value = "${albedo.adminPath}/pay/order")
public class OrderResource extends DataResource<OrderService, Order> {
	
	@ModelAttribute
	public Order get(@RequestParam(required = false) String id) throws Exception {
		String path = request.getRequestURI();
		if (path!=null && !path.contains("checkBy") && !path.contains("find") && PublicUtil.isNotEmpty(id)) {
			return service.findOne(id);
		} else {
			return new Order();
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public String list() {
		return "modules/pay/orderList";
	}

	/**
	 * GET / : get all order.
	 * 
	 * @param pm
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and with body all order
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity getPage(PageModel<Order> pm) {
	    service.findPage(pm, SecurityUtil.dataScopeFilter());
		JSON rs = JsonUtil.getInstance().setRecurrenceStr().toJsonObject(pm);
		return ResultBuilder.buildObject(rs);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public String form(Order order) {
		if(order == null){
			throw new RuntimeMsgException(PublicUtil.toAppendStr("查询支付订单失败，原因：无法查找到编号为[", request.getParameter("id"), "]的支付订单"));
		}
		return "modules/pay/orderForm";
	}

	/**
	 * POST / : Save a order.
	 *
	 * @param order the HTTP order
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity save(Order order) {
		log.debug("REST request to save Order : {}", order);
		service.save(order);
		return ResultBuilder.buildOk("保存支付订单成功");

	}

	/**
	 * DELETE //:id : delete the "id" Order.
	 *
	 * @param ids the id of the order to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/delete/{ids:" + Globals.LOGIN_REGEX
			+ "}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity delete(@PathVariable String ids) {
		log.debug("REST request to delete Order: {}", ids);
		service.delete(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT)));
		return ResultBuilder.buildOk("删除支付订单成功");
	}
	/**
	 * lock //:id : lockOrUnLock the "id" Order.
	 *
	 * @param ids the id of the order to lock
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/lock/{ids:" + Globals.LOGIN_REGEX
			+ "}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity lockOrUnLock(@PathVariable String ids) {
		log.debug("REST request to lockOrUnLock Order: {}", ids);
		service.lockOrUnLock(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT)));
		return ResultBuilder.buildOk("操作支付订单成功");
	}

}