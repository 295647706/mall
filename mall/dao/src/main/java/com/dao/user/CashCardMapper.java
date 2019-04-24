package com.dao.user;

import com.dao.BaseMapper;
import com.model.user.CashCard;
import com.model.user.CashCardSub;

import java.util.Map;

/**
 * 
 * @Author Ruan
 * 
 */

public interface CashCardMapper extends BaseMapper<CashCard, CashCardSub> {

    Map<String, Object> getUserCashCard(Long userId);
}