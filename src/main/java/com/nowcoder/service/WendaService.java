package com.nowcoder.service;

import org.springframework.stereotype.Service;

/**
 * @author shine10076
 * @date 2019/5/8 19:55
 */
@Service
public class WendaService {
    public String getMessage(int userId) {
        return "Hello Message:" + String.valueOf(userId);
    }
}
