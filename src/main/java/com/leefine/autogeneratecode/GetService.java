package com.leefine.autogeneratecode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetService {

	public static void generateService(List<Map<String, Object>> lsTable,
			String folder, String tableName, String objectName,
			String packageName) throws IOException {

		String javaTemplate = "package #package#.service;";
		javaTemplate += "\r\n";
		javaTemplate += "@Service";
		javaTemplate += "\r\n";
		javaTemplate += "public class XxxService{";
		javaTemplate += "\r\n";
		javaTemplate += "@Autowired \r\n private XxxMapper xxxMapper;";
		javaTemplate += "\r\n";
		javaTemplate += "public List<Xxx> selectListPage(Map<String, Object> param){return xxxMapper.selectListPage(param);}";
		javaTemplate += "\r\n";
		javaTemplate += "public Xxx selectByID(int id){return xxxMapper.selectByID(id);}";
		javaTemplate += "\r\n";
		javaTemplate += "public int deleteByID(int id){return xxxMapper.deleteByID(id);}";
		javaTemplate += "\r\n";
		javaTemplate += "public int insert(Xxx xxx){return xxxMapper.insert(xxx);}";
		javaTemplate += "\r\n";
		javaTemplate += "public int update(Xxx xxx){return xxxMapper.update(xxx);}";
		javaTemplate += "\r\n}";

		javaTemplate = javaTemplate.replaceAll("#package#", packageName)
				.replaceAll("Xxx", objectName)
				.replaceAll("xxx", objectName.toLowerCase());

		folder = folder + "/service/";
		File fd = new File(folder);

		if (!fd.exists() && !fd.isDirectory()) {
			System.out.println("//"+folder+"不存在，创建。。。");
			fd.mkdirs();
		} 

		System.out.println(folder + objectName + "Service.java 创建。。。");
		File f = new File(folder + objectName + "Service.java");
		BufferedWriter o = new BufferedWriter(new FileWriter(f));
		o.write(javaTemplate);
		o.close();
	}
}
