package com.mkyong.ui.mvc.kaawork;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by user on 11.06.17.
 */
@Controller
@RequestMapping("/kaawork/form")
public class FormController {
    @RequestMapping("component")
    public ModelAndView formComponent() {
        return new ModelAndView("kaawork/form_component");
    }

    @RequestMapping("validation")
    public ModelAndView formValidation() {
        return new ModelAndView("kaawork/form_validation");
    }
}
