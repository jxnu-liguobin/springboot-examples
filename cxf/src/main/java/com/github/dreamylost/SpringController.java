package com.github.dreamylost;

import com.alibaba.fastjson.JSONObject;
import com.github.dreamylost.entity.Student;
import com.github.dreamylost.entity.Students;
import com.github.dreamylost.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Spring 前端控制
 * <p>
 * 通过这个返回正确，作对比
 *
 * @author： liguobin
 * @Description：
 * @时间： 2018-3-7 下午3:35:17
 * @version： V1.0
 */
@Controller
public class SpringController {

    @Autowired
    private StudentService studentRestfulService;

    @ResponseBody
    @Produces({MediaType.APPLICATION_JSON + "charset='utf-8'"})
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public String getStudent(@PathVariable("id") Integer id) {
        Student student = studentRestfulService.getStudent(id);
        Object json = JSONObject.toJSON(student);
        return json.toString();
    }

    /**
     * @param ids
     * @return
     * @参数：{"ids":{"id":[1,2,3,4]
     */
    @ResponseBody
    @Produces({MediaType.APPLICATION_JSON + "charset='utf-8'"})
    @Consumes({MediaType.APPLICATION_JSON})
    @RequestMapping(value = "gets/{ids}", method = RequestMethod.GET)
    public String getAll(@PathVariable("ids") String ids) {
        Students students = studentRestfulService.getAllStudent(ids);
        Object json = JSONObject.toJSON(students);
        return json.toString();
    }
}
