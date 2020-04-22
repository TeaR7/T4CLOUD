package com.t4cloud.t.support.mapper;


import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4cloud.t.support.entity.SupMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * 消息列表 Mapper 接口
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-31
 */
public interface SupMessageMapper extends BaseMapper<SupMessage> {

    @SqlParser(filter = true)
    List<SupMessage> getTodoMessage(@Param("messageType") Integer messageType, @Param("sendStatus") List<Integer> sendStatus, @Param("sendTime") Date sendTime, @Param("size") Integer size);

    @SqlParser(filter = true)
    @Update("UPDATE sup_message SET send_num = #{sendNum}, send_status = #{sendStatus}, update_by = #{updateBy}, send_result = #{sendResult} WHERE id = #{id}")
    void changeMessageStatus(@Param("id") String id, @Param("sendNum") Integer sendNum, @Param("updateBy") String updateBy, @Param("sendStatus") Integer sendStatus, @Param("sendResult") String sendResult);

}
