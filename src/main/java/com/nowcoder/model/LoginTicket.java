package com.nowcoder.model;

import java.util.Date;

/**
 * @author shine10076
 * @date 2019/5/8 19:53
 */

public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    /**
     * status 0有效， 1无效
     */
    private int status;
    private String ticket;

    /**
     * getter and setter
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
