package ru.kaawork.ui.mvc.kaawork;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by user on 11.06.17.
 */
@Controller
@RequestMapping("/kaawork/uifitures/")
public class UiFituresController {
    @RequestMapping("general")
    public ModelAndView getGeneral() {
        return new ModelAndView("kaawork/general");
    }

    @RequestMapping("buttons")
    public ModelAndView getButtons() {
        return new ModelAndView("kaawork/buttons");
    }

    @RequestMapping("grids")
    public ModelAndView getGrids() {
        return new ModelAndView("kaawork/grids");
    }
}
