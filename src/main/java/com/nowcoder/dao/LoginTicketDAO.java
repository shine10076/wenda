package com.nowcoder.dao;

import com.nowcoder.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author shine10076
 * @date 2019/5/8 19:55
 */
@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = "user_id, expired, status, ticket";
    String SELECT_FIELDS = "id, "+INSERT_FIELDS;

    /**
     * 插入LoginTicket
     * @param ticket
     * @return
     */
    @Insert({"insert into ", TABLE_NAME, "(",INSERT_FIELDS,") values(#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    /**
     * 根据ticket字段选择登陆token
     * @param ticket
     * @return
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    /**
     * 更新状态
     * @param ticket
     * @param status
     */
    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}

