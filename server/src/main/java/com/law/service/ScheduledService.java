package com.law.service;

import com.law.model.LawPromoter;
import com.law.repository.LawOrderRepository;
import com.law.repository.LawPromoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Auther: nonghz
 * @Date: 2018/11/6 17:43
 * @Description:
 */
@Component
public class ScheduledService {
    @Autowired
    LawPromoterRepository lawPromoterRepository;
    @Autowired
    LawOrderRepository lawOrderRepository;

    @Scheduled(cron = "0 59 23 L * ?")
    public void performanceAcount() {
        List<LawPromoter> list = lawPromoterRepository.findAllByStatus("U");
        for (LawPromoter promoter: list) {
            acount(promoter);

        }
    }

    public boolean isParent(LawPromoter promoter) {
        int promoterId = promoter.getPromoterId();
        int count = lawPromoterRepository.countByParentPromoterIdAndStatus(promoterId, "U").intValue();
        if (count > 0) {
            return true;
        }
        return false;
    }

    public BigDecimal acount(LawPromoter promoter) {
        if (!isParent(promoter)) {
            return lawOrderRepository.countInvolvingMoneyByOpenId(promoter.getOpenId(), getCycleId());
        }
        List<LawPromoter> childList = lawPromoterRepository.findAllByParentPromoterId(promoter.getPromoterId());
        double involvingMoney = 0.00;
        for (LawPromoter childPromoter : childList) {
            double childInvolvingMoney = acount(childPromoter).doubleValue();
            involvingMoney = involvingMoney + childInvolvingMoney;
        }
        return BigDecimal.valueOf(involvingMoney);
    }

    public String getCycleId() {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime());
    }
}
