package com.shawntime.spring.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shawntime.myspring.v1.annotation.Autowired;
import com.shawntime.myspring.v1.annotation.Controller;
import com.shawntime.myspring.v1.annotation.RequestMapping;
import com.shawntime.myspring.v1.annotation.RequestParam;

@Controller
@RequestMapping(name = "/test")
public class TestController {

    @Autowired
    private ITestService testService;

    @RequestMapping(name = "/add")
    public Object add(HttpServletRequest req,
                      @RequestParam("i") Integer i,
                      @RequestParam("j") Integer j,
                      HttpServletResponse resp) {
        return testService.add(i, j);
    }
}
