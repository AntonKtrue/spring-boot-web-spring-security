package com.mkyong.ui.mvc.kaawork;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by user on 11.06.17.
 */
@Controller
@RequestMapping("/kaawork/templates")
public class TemplateController {

    @RequestMapping
    public ModelAndView getTemplates() {
        return new ModelAndView("kaawork/templates");
    }

}
