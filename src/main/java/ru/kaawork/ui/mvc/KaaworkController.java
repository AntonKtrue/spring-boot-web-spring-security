package ru.kaawork.ui.mvc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kaawork.auth.model.User;
import ru.kaawork.auth.service.UserProfileService;
import ru.kaawork.auth.service.UserService;

import java.util.HashMap;

/**
 * Created by user on 10.06.17.
 */
@Controller
@RequestMapping("/kaawork")
public class KaaworkController {

    @Autowired
    UserService userService;

    @Autowired
    UserProfileService userProfileService;

    @RequestMapping
    public ModelAndView root() {
        return new ModelAndView("kaawork/index");
    }

    @RequestMapping("login")
    public ModelAndView getLogin() {
        return new ModelAndView("kaawork/login");
    }

    @RequestMapping("profile")
    public ModelAndView getProfile() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findBySSO(userDetails.getUsername());
        //HashMap<String, Object> map = new HashMap<>();
        return new ModelAndView("kaawork/profile","user", user);
    }

    @RequestMapping("resume")
    public ModelAndView getResume() {
        return new ModelAndView("kaawork/resume");
    }

    @RequestMapping("widgets")
    public ModelAndView getWidgets() {
        return new ModelAndView("kaawork/widgets");
    }

    @RequestMapping("chart-chartjs")
    public ModelAndView getCharts() {
        return new ModelAndView("kaawork/chart-chartjs");
    }

}
