package ru.kaawork.ui.mvc;


import com.sun.javafx.sg.prism.NGShape;
import net.webservicex.geoip.GeoIP;
import net.webservicex.geoip.GeoIPService;
import net.webservicex.geoip.GeoIPServiceSoap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import ru.kaawork.auth.model.User;
import ru.kaawork.auth.model.UserProfile;

import ru.kaawork.auth.service.UserProfileService;
import ru.kaawork.auth.service.UserService;
import ru.kaawork.auth.service.UserServiceImpl;

import java.util.List;


/**
 * Created by user on 10.06.17.
 */
@Controller
@RequestMapping("/kaawork")
@SessionAttributes("roles")
public class KaaworkController {

    Logger logger = LoggerFactory.getLogger(KaaworkController.class);

    @Autowired
    UserProfileService userProfileService;
    @Autowired
    UserService userService;

    public User getCurrentUser() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.findBySSO(userDetails.getUsername());
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping
    public ModelAndView root() {
        return new ModelAndView("kaawork/index", "user", getCurrentUser());
    }

    @RequestMapping("registration")
    public String getRegistration(Model model) {
        model.addAttribute("newuser", new User());
        return "kaawork/registration";
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public String sum(@ModelAttribute User newuser, Model model) {
        logger.info("New user add: {} {} ", newuser.getFirstName(), newuser.getLastName());
        userService.saveUser(newuser);
        return "kaawork/login";
    }

    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }

    @RequestMapping("login")
    public String getLogin() {
        return "kaawork/login";
    }

    @RequestMapping("profile")
    public String getProfile(Model model) {
        model.addAttribute("user", getCurrentUser());
        return "kaawork/login";
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public String editProfile(@ModelAttribute User user, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findBySSO(userDetails.getUsername());
        currentUser.setBirthDate(user.getBirthDate());
        currentUser.setAboutMe(user.getAboutMe());
        currentUser.setEmail(user.getEmail());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setOccupation(user.getOccupation());
        currentUser.setWebsite(user.getWebsite());
        //TODO change password

        logger.info("update profile: {}", currentUser.getSsoId());
        userService.updateUser(currentUser);
        return "kaawork/profile";
    }

    @RequestMapping("resume")
    public String getResume(Model model) {
        model.addAttribute("user", getCurrentUser());
        return "kaawork/resume";
    }

    @RequestMapping("widgets")
    public String getWidgets(Model model) {
        model.addAttribute("user", getCurrentUser());
        return "kaawork/widgets";
    }

    @RequestMapping("chart-chartjs")
    public String getCharts(Model model) {
        model.addAttribute("user", getCurrentUser());
        return "kaawork/chart-chartjs";
    }

    @RequestMapping("templates")
    public String getTemplates() {
        return "kaawork/templates";
    }

    @RequestMapping("form/{value}")
    public String form(@PathVariable("value") String value, Model model) {
        model.addAttribute("user", getCurrentUser());
        switch (value) {
            case "component":
                return "kaawork/form_component";
            case "validation":
                return "kaawork/form_validation";
            default:
                return "kaawork/index";
        }
    }

    @RequestMapping("uifitures/{value}")
    public String uifitures(@PathVariable("value") String value, Model model) {
        model.addAttribute("user",getCurrentUser());
        switch (value) {
            case "general":
                return "kaawork/general";
            case "buttons":
                return "kaawork/buttons";
            case "grids":
                return "kaawork/grids";
            default:
                return "kaawork/index";
        }

    }

    private String clientIpAddress() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
    }

    private String clientCountry() {
        GeoIPService ipService = new GeoIPService();
        GeoIPServiceSoap geoIPServiceSoap = ipService.getGeoIPServiceSoap();
        GeoIP geoIP = geoIPServiceSoap.getGeoIP(clientIpAddress());
        return geoIP.getCountryName();
    }

    @RequestMapping("ws")
    public String mainPage(Model model) {
        model.addAttribute("ipaddress", clientIpAddress());
        model.addAttribute("country",clientCountry());
        model.addAttribute("user",getCurrentUser());
        return "kaawork/ws_start";
    }

}
