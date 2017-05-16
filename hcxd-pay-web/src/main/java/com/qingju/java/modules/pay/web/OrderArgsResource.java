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
import com.qingju.java.pay.domain.OrderArgs;
import com.qingju.java.pay.service.OrderArgsService;

/**
 * 支付参数Controller 支付参数
 * 
 * @author lj
 * @version 2017-05-02
 */
@Controller
@RequestMapping(value = "${albedo.adminPath}/pay/orderArgs")
public class OrderArgsResource extends DataResource<OrderArgsService, OrderArgs> {

	@ModelAttribute
	public OrderArgs get(@RequestParam(required = false) String id) throws Exception {
		String path = request.getRequestURI();
		if (path != null && !path.contains("checkBy") && !path.contains("find") && PublicUtil.isNotEmpty(id)) {
			return service.findOne(id);
		} else {
			return new OrderArgs();
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public String list() {
		return "modules/pay/orderArgsList";
	}

	/**
	 * GET / : get all orderArgs.
	 * 
	 * @param pm
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and with body all
	 *         orderArgs
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity getPage(PageModel<OrderArgs> pm) {
		service.findPage(pm, SecurityUtil.dataScopeFilter());
		JSON rs = JsonUtil.getInstance().setRecurrenceStr().toJsonObject(pm);
		return ResultBuilder.buildObject(rs);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public String form(OrderArgs orderArgs) {
		if (orderArgs == null) {
			throw new RuntimeMsgException(
					PublicUtil.toAppendStr("查询支付参数失败，原因：无法查找到编号为[", request.getParameter("id"), "]的支付参数"));
		}
		return "modules/pay/orderArgsForm";
	}

	/**
	 * POST / : Save a orderArgs.
	 *
	 * @param orderArgs
	 *            the HTTP orderArgs
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity save(OrderArgs orderArgs) {
		log.debug("REST request to save OrderArgs : {}", orderArgs);
		service.save(orderArgs);
		return ResultBuilder.buildOk("保存支付参数成功");

	}

	/**
	 * DELETE //:id : delete the "id" OrderArgs.
	 *
	 * @param ids
	 *            the id of the orderArgs to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/delete/{ids:" + Globals.LOGIN_REGEX
			+ "}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity delete(@PathVariable String ids) {
		log.debug("REST request to delete OrderArgs: {}", ids);
		service.delete(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT)));
		return ResultBuilder.buildOk("删除支付参数成功");
	}

	/**
	 * lock //:id : lockOrUnLock the "id" OrderArgs.
	 *
	 * @param ids
	 *            the id of the orderArgs to lock
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/lock/{ids:" + Globals.LOGIN_REGEX
			+ "}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity lockOrUnLock(@PathVariable String ids) {
		log.debug("REST request to lockOrUnLock OrderArgs: {}", ids);
		service.lockOrUnLock(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT)));
		return ResultBuilder.buildOk("操作支付参数成功");
	}

}