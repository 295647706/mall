package com.api.controller.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.TblOrder;
import com.service.BaseService;
import com.service.order.TblOrderService;
import com.util.PageUtils;
import com.util.R;


/**
 * 订单
 * 
 * @author laimeng
 * @email laimeng@126.com
 * @date 2019-04-23 21:41:37
 */
@Controller
@RequestMapping("tblorder")
public class TblOrderController extends BaseService{
	@Autowired
	private TblOrderService tblOrderService;
	
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TblOrder> tblOrderList = tblOrderService.queryList(map);
		int total = tblOrderService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tblOrderList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id){
		TblOrder tblOrder = tblOrderService.queryObject(id);
		
		return R.ok().put("tblOrder", tblOrder);
	}
	
	/**
	 * 保存
	 */
	
	/**
	 * 
	 * productId	string	是	商品ID	
	   productSkuId	string	否	商品规格ID	
       spike	string	否	是否秒杀(0=否 1=是)	
       collage	string	否	是否拼团(0=否 1=是)	
       integral	string	否	是否积分(0=否 1=是)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestParam(name = "productId")String productId ,
			@RequestParam(name = "productSkuId")String productSkuId ,
			@RequestParam(name = "spike")String spike ,
			@RequestParam(name = "collage")String collage ,
			@RequestParam(name = "integral")String integral ,
			HttpServletRequest request
			){
		TblOrder tblOrder=new TblOrder();
		//Authorization	string	是	权限jwtToken
		String Authorization=request.getHeader("Authorization");
		Long userid = getUserIdFromSecurity();
		tblOrder.setUserId(userid);
		tblOrder.setId(new Date().getTime());
		tblOrder.setCreateTime(new Date().getTime());
		tblOrder.setUpdateTime(new Date().getTime());
		tblOrder.setCollageId(Long.valueOf(collage));
		
		tblOrderService.save(tblOrder);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TblOrder tblOrder){
		tblOrderService.update(tblOrder);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids){
		tblOrderService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
