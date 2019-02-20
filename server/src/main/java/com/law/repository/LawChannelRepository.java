package com.law.repository;

import com.law.model.LawChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: nonghz
 * @Date: 2018/11/6 15:47
 * @Description:
 */
public interface LawChannelRepository extends JpaRepository<LawChannel, Integer>, JpaSpecificationExecutor<LawChannel> {
    public LawChannel findAllByChannelId(int channelId);
}
