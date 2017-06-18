package ru.kaawork.ui.mvc;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by user on 10.06.17.
 */
@Controller
@RequestMapping("/kaawork")
public class KaaworkController {

    @RequestMapping
    public ModelAndView root() {
        return new ModelAndView("kaawork/index");
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
