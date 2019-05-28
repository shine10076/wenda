package com.nowcoder.controller;

import com.nowcoder.model.User;
import com.nowcoder.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author shine10076
 * @date 2019/5/8 19:45
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;


    @RequestMapping(path ={"/reg/"}, method = {RequestMethod.POST})
    public String reg(Model model, @RequestParam("username")String username,
                      @RequestParam("password")String password,
                      @RequestParam("next")String next,
                      @RequestParam(value="rememberme", defaultValue = "false")boolean rememberme,
                      HttpServletResponse response){
        try{
            Map<String, Object> map = userService.register(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                /**
                 * setPath:确保Cookie在同一个服务器内共享
                 */
                cookie.setPath("/");
                User user = userService.selectByName(username);
                logger.info(user.getName());
                model.addAttribute("user",user);
                if(rememberme)
                {
                    /**
                     * 如果用户勾选了记住我，设置cookie的过期时间为1天
                     */
                    cookie.setMaxAge(3600*24*1000);
                }
                response.addCookie(cookie);
                if(StringUtils.isNotBlank(next)){
                    return "redirect:/"+next;
                }
                return "redirect:/";
            }else {
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }
        }catch (Exception e)
        {
            logger.error("注册异常"+e.getMessage());
            model.addAttribute("msg","服务器错误");
            return "login";
        }
    }

    /**
     * 重新回到登陆页面
     * @param model
     * @param next
     * @return
     */
    @RequestMapping(path = {"/reglogin"}, method={RequestMethod.GET})
    public String regloginPage(Model model,@RequestParam(value="next",required = false)String next)
    {
        model.addAttribute("next",next);
        return "login";
    }

    @RequestMapping(path = {"/login/"}, method ={RequestMethod.POST})
    public String login(Model model, @RequestParam("username")String username,
                        @RequestParam("password")String password,
                        @RequestParam(value = "next",required = false)String next,
                        @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                User user = userService.selectByName(username);
                logger.info(user.getName());
                if (rememberme) {
                    cookie.setMaxAge(3600 * 24 * 1000);
                }
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("登陆异常" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = {"/logout"},method = {RequestMethod.GET,RequestMethod.POST})
    public String logout(@CookieValue("ticket")String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }
}
