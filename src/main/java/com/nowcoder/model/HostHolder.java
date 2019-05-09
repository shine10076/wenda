package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * @author shine10076
 * @date 2019/5/9 20:18
 */
@Component
public class HostHolder {
    /**
     * 每个线程都有一份拷贝
     */
    private static ThreadLocal<User> users = new ThreadLocal<>();


    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear()
    {
        users.remove();
    }
}
