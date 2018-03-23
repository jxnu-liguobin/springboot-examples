package cn.edu.jxnu;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.jxnu.entity.Student;
import cn.edu.jxnu.entity.Students;
import cn.edu.jxnu.service.StudentService;

import com.alibaba.fastjson.JSONObject;

/**
 * Spring 前端控制
 * 
 * 通过这个返回正确，作对比
 * 
 * @author： liguobin
 * @Description：
 * @时间： 2018-3-7 下午3:35:17
 * @version： V1.0
 * 
 */
@Controller
public class SpringController {

	@Autowired
	private StudentService studentRestfulService;

	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON + "charset='utf-8'" })
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public String getStudent(@PathVariable("id") Integer id) {
		Student student = studentRestfulService.getStudent(id);
		Object json = JSONObject.toJSON(student);
		return json.toString();
	}

	/**
	 * @参数：{"ids":{"id":[1,2,3,4]
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON + "charset='utf-8'" })
	@Consumes({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "gets/{ids}", method = RequestMethod.GET)
	public String getAll(@PathVariable("ids") String ids) {
		Students students = studentRestfulService.getAllStudent(ids);
		Object json = JSONObject.toJSON(students);
		return json.toString();
	}
}
