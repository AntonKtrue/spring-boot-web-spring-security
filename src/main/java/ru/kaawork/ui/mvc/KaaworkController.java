package ru.kaawork.ui.mvc;



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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import ru.kaawork.auth.model.User;
import ru.kaawork.auth.model.UserProfile;

import ru.kaawork.auth.model.UserProfileType;
import ru.kaawork.auth.service.UserProfileService;
import ru.kaawork.auth.service.UserService;

import javax.validation.Valid;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;


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
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }

    @RequestMapping
    public ModelAndView root() {
        return new ModelAndView("kaawork/index", "user", getCurrentUser());
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    //public String saveUser(@ModelAttribute User newuser, Model model) {
    public String saveUser(@ModelAttribute("newuser") @Valid User newuser, BindingResult result) {
        if(result.hasErrors()) {
            logger.info("result.hasErrors()");
            return "/kaawork/registration";
        }

        if(!userService.isUserSSOUnique(newuser.getId(), newuser.getSsoId())) {
            logger.info("User is not unique");
            FieldError ssoError = new FieldError("user",
                    "ssoId",
                    "User " + newuser.getSsoId() + " already exists");
            result.addError(ssoError);
            return "/kaawork/registration";
        }

        logger.info("New user add: {} {} ", newuser.getFirstName(), newuser.getLastName());
        userService.saveUser(newuser);
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1); //TODO this is magical world...
        userProfile.setType(UserProfileType.USER.getUserProfileType());
        LinkedHashSet<UserProfile> userProfiles = new LinkedHashSet<>();
        userProfiles.add(userProfile);
        newuser.setUserProfiles(userProfiles);
        userService.updateUser(newuser);
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

    @RequestMapping("/{page}")
    public String getPage(@PathVariable("page") String page, Model model) {
        if(page.equals("registration")) {
            model.addAttribute("newuser", new User());
            return "kaawork/registration";
        }
        model.addAttribute("user", getCurrentUser());
        switch (page) {
            case "templates":
                return "kaawork/templates";
            case "login":
                return "kaawork/login";
            case "profile":
                return "kaawork/profile";
            case "resume":
                return "kaawork/resume";
            case "widgets":
                return "kaawork/widgets";
            case "chart-chartjs":
                return "kaawork/chart-chartjs";
            case "rest":
                return "kaawork/rest";
            case "camel":
                return "kaawork/camel";
            case "ws":
                model.addAttribute("ipaddress", clientIpAddress());
                model.addAttribute("country",clientCountry());
                return "kaawork/ws_start";
            default:
                return "kaawork/index";
        }
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



}
