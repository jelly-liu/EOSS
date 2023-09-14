package com.open.eoss.web;

import com.open.eoss.service.SampleService;
import com.open.eoss.web.vo.EossRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 9:32 AM 2019/1/10
 * @Description：${description}
 */

@Controller
@RequestMapping(value = "/gue/")
public class SampleAction {
    @Autowired
    SampleService sampleService;

    @ResponseBody
    @RequestMapping("t1")
    public EossRes<Object> test(){
        sampleService.testWhere();
        return EossRes.success();
    }
}
