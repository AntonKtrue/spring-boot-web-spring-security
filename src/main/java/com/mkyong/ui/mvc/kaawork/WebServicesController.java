package com.mkyong.ui.mvc.kaawork;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by user on 12.06.17.
 */
@Controller
@RequestMapping("/kaawork/ws")
public class WebServicesController {

    private String clientIpAddress() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
    }


    @RequestMapping
    public ModelAndView mainPage() {
        return new ModelAndView("kaawork/ws_start","ipaddress",clientIpAddress());
    }
}
