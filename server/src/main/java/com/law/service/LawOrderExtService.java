package com.law.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.law.common.OrderStatusEnum;
import com.law.entity.LawOrderExt;
import com.law.model.LawOrder;
import com.law.repository.LawOrderExtRepository;
import com.law.repository.LawOrderRepository;

@Service
public class LawOrderExtService {

	@Autowired
	private LawOrderExtRepository lawOrderExtRepository;
	@Autowired
	private LawOrderRepository lawOrderRepository;

	public LawOrderExt save(LawOrderExt t) {
		return lawOrderExtRepository.save(t);
	}

	public LawOrderExt findByOrderId(Integer orderId) {
		return lawOrderExtRepository.findByOrderId(orderId);
	}

	public LawOrderExt uodateOrderExt(String data) {
		JSONObject json = JSONObject.parseObject(data);
		LawOrderExt requestParams = JSONObject.parseObject(data, LawOrderExt.class);
		Integer orderId = json.getInteger("orderId");
		LawOrderExt lawOrderExt = findByOrderId(orderId);
		if(lawOrderExt!=null) {
			//更新订单状态
			LawOrder lawOrder = lawOrderRepository.findOneByOrderId(orderId);
			lawOrder.setStatus(OrderStatusEnum.CALL_PHONE.getCode());
			//保存预约时间点数据
			lawOrderExt.setSelected(requestParams.getSelected());
			lawOrderExt.setCustomEndTime(requestParams.getCustomEndTime());
			lawOrderExt.setCustomStratTime(requestParams.getCustomStratTime());
			 return lawOrderExtRepository.save(lawOrderExt);
		}else {
			LawOrder lawOrder = lawOrderRepository.findOneByOrderId(orderId);
			if(OrderStatusEnum.PHONE_APPOINTMENT.getCode().equals(lawOrder.getStatus())) {
				lawOrderExt = new LawOrderExt();
				lawOrderExt.setOrderId(orderId);
				lawOrderExt.setSelected(requestParams.getSelected());
				lawOrderExt.setCustomEndTime(requestParams.getCustomEndTime());
				lawOrderExt.setCustomStratTime(requestParams.getCustomStratTime());
				lawOrder.setStatus(OrderStatusEnum.CALL_PHONE.getCode());
				return lawOrderExtRepository.save(lawOrderExt);
			}
		}
		return null;
	}
}
