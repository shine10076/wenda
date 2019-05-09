package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author shine10076
 * @date 2019/5/8 19:55
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public User selectByName(String name)
    {
        return userDAO.selectByName(name);
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    public Map<String,Object> register(String username, String password){
        Map<String, Object> map = new HashMap<>(5);
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if (user != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }

        /**
         * 密码强度
         */
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl("https://img2.woyaogexing.com/2019/05/08/9e1328032c75460589e0b2efcef3af9b!400x400.jpeg");
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);


        /**
         * 登陆
         */
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    public Map<String, Object> login(String username, String password){
        Map<String, Object> map = new HashMap<>(5);
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }

        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if(user == null){
            map.put("msg","用户名不存在");
            return map;
        }

        if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword()))
        {
            map.put("msg","密码不正确");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        map.put("userId",user.getId());
        return map;
    }

    /**
     * 根据用户id增加LoginTicket
     * @param userId
     * @return
     */
    private String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        /**
         * 设置过期时间
         */
        date.setTime(date.getTime()+1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-"," "));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket)
    {
        loginTicketDAO.updateStatus(ticket,1);
    }
}
