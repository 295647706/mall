package com.dao.user;

import com.dao.BaseMapper;
import com.model.user.CashCardBudget;
import com.model.user.CashCardBudgetSub;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Author Ruan
 * 
 */

public interface CashCardBudgetMapper extends BaseMapper<CashCardBudget, CashCardBudgetSub> {

    List<Map<String,Object>> getUserCashCardDetails(Long userId);
}