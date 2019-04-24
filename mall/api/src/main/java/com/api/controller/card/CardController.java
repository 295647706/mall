package com.api.controller.card;

import com.api.controller.BaseController;
import com.service.cash.CashCardBudgetService;
import com.service.cash.CashCardService;
import com.service.user.UserCouponService;
import com.service.user.UserGoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController("card")
@RequestMapping("/user")
public class CardController extends BaseController  {

   @Autowired
   private UserGoldService userGoldService;
   @Autowired
   private UserCouponService userCouponService;
   @Autowired
   private CashCardService cashCardService;
    @Autowired
   private CashCardBudgetService cashCardBudgetService;


   /**
    * @date 11:09 2019/4/23
    * @description 分页获取三信币
    **/
    @GetMapping("/gold/pages")
    public Object getGoldPage(@RequestParam(name = "useType",required = false) String useType,
                                  @RequestParam(name = "page",defaultValue = "1",required = false) Integer page,
                                  @RequestParam(name = "size",defaultValue = "10",required = false)Integer size){
      return userGoldService.page(useType,page,size);
    }
    /**
     * @date 11:23 2019/4/23
     * @description 获取优惠卷
     **/
    @GetMapping("/coupon/pages")
    public Object getCouponPage(@RequestParam(name = "useType",required = false) String useType,
                              @RequestParam(name = "page",defaultValue = "1",required = false) Integer page,
                              @RequestParam(name = "size",defaultValue = "10",required = false)Integer size){
        return userCouponService.page(useType,page,size);
    }

    /**
     * @date 11:23 2019/4/23
     * @description 现金卡
     **/
    @GetMapping("/cashcard")
    public Object getUserCashCard(){
        return cashCardService.getUserCashCard();
    }
    
    /**
     * @date 11:42 2019/4/23
     * @description 现金卡明细
     **/
    @GetMapping("/cashcard/pages")
    public Object getUserCashCardDetails(@RequestParam(name = "page",defaultValue = "1",required = false) Integer page,
                                         @RequestParam(name = "size",defaultValue = "10",required = false)Integer size){
        return cashCardBudgetService.getUserCashCardDetails(page,size);
    }


    /**
     * @date 11:42 2019/4/23
     * @description 我的卡券
     **/
    @GetMapping("/mycoupon/pages")
    public Object getUserMycouponPage(@RequestParam(name = "page",defaultValue = "1",required = false) Integer page,
                                         @RequestParam(name = "size",defaultValue = "10",required = false)Integer size,
                                         @RequestParam(name = "selectType",required = false,defaultValue = "0")String selectType){
        return userCouponService.myCouponPage(page,size,selectType);
    }
}
