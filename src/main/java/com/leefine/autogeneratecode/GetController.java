package com.leefine.autogeneratecode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetController {

	public static void generateController(List<Map<String, Object>> lsTable, String folder, String tableName,
			String objectName, String packageName) throws IOException {

		String javaTemplate = "package #package#.controller;";
		javaTemplate += "\r\n";
		javaTemplate += "@Controller";
		javaTemplate += "\r\n";
		javaTemplate += "@RequestMapping(\"xxx\")";
		javaTemplate += "\r\n";
		javaTemplate += "public class XxxController extends BaseController{";
		javaTemplate += "\r\n";
		javaTemplate += "@Autowired \r\n private XxxService xxxService;";
		javaTemplate += "\r\n";
		javaTemplate += "@GetMapping(\"index\")";
		javaTemplate += "\r\n";		
		javaTemplate += "public String selectListPage(@RequestParam(defaultValue = \"1\") int p, Model md)";
		javaTemplate += "\r\n";
		javaTemplate += "{";
		javaTemplate += "\r\n";
		javaTemplate += "Pager pager = new Pager(p, getRelPath() + \"/xxx/index\", \"id desc\");";
		javaTemplate += "\r\n";
		javaTemplate += "Map<String, Object> param = new HashMap<String, Object>();";
		javaTemplate += "\r\n";
		javaTemplate += "param.put(\"page\", pager);";
		javaTemplate += "\r\n";
		javaTemplate += "List<Xxx> lsXxx = xxxService.selectListPage(param);";
		javaTemplate += "\r\n";
		javaTemplate += "md.addAttribute(\"lsXxx\", lsXxx);";
		javaTemplate += "\r\n";
		javaTemplate += "md.addAttribute(\"pager\", pager);";
		javaTemplate += "\r\n";
		javaTemplate += "return \"/xxx/index\";";
		javaTemplate += "\r\n";
		javaTemplate += "}";


		javaTemplate += "@GetMapping(\"create\")";
		javaTemplate += "public String create() { return \"/xxx/create\"; }";

		javaTemplate += "@PostMapping(\"create\")";
		javaTemplate += "public String create(Xxx obj) {";
		javaTemplate += "setCRTOrMDF(obj, getCurrentUser());";
		javaTemplate += "xxxService.insert(obj); setOperatingLogDescr(\"Create \"+obj.getId(), OperatingLogType.Create);";
		javaTemplate += "return \"redirect:/xxx/index\";";
		javaTemplate += "}";



		javaTemplate += "@GetMapping(\"edit/{id}\")";
		javaTemplate += "public String edit(@PathVariable int id, Model md) { md.addAttribute(\"obj\", xxxService.selectByID(id));return \"/xxx/edit\"; }";

		javaTemplate += "@PostMapping(\"edit\")";
		javaTemplate += "public String edit(Xxx obj) {";
		javaTemplate += "setCRTOrMDF(obj, getCurrentUser());";
		javaTemplate += "xxxService.update(obj); setOperatingLogDescr(\"Update \"+obj.getId(), OperatingLogType.Update);";
		javaTemplate += "return \"redirect:/xxx/index\";";
		javaTemplate += "}";

		javaTemplate += "\r\n";
		javaTemplate += "}";

		javaTemplate = javaTemplate.replaceAll("#package#", packageName).replaceAll("Xxx", objectName).replaceAll("xxx",
				objectName.toLowerCase());

		folder = folder + "/controller/";
		File fd = new File(folder);

		if (!fd.exists() && !fd.isDirectory()) {
			System.out.println("//" + folder + "不存在，创建。。。");
			fd.mkdirs();
		}

		System.out.println(folder + objectName + "Controller.java 创建。。。");
		File f = new File(folder + objectName + "Controller.java");
		BufferedWriter o = new BufferedWriter(new FileWriter(f));
		o.write(javaTemplate);
		o.close();

	}
}
