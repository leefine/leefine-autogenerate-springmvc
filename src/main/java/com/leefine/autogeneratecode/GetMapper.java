package com.leefine.autogeneratecode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetMapper {

	public static void generateMapper(List<Map<String, Object>> lsTable,
			String folder, String tableName, String objectName,
			String packageName) throws IOException {
		String template = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		template += "\r\n";
		template += "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";
		template += "\r\n";
		template += "<mapper namespace=\"#package#.dao.XxxMapper\">";
		template += "\r\n";
		template += "<select id=\"selectListPage\" parameterType=\"hashmap\" resultType=\"Xxx\">select * from #tableName#</select>";
		template += "\r\n";
		template += "<select id=\"selectByID\" parameterType=\"int\" resultType=\"Xxx\">select * from #tableName# where id=#{id}</select>";
		template += "\r\n";
		template += "<delete id=\"deleteByID\">delete from #tableName# where id=#{id}</delete>";
		template += "\r\n";
		template += "<insert id=\"insert\" parameterType=\"Xxx\" useGeneratedKeys=\"true\" keyProperty=\"id\">#insert#</insert>";
		template += "\r\n";
		template += "<update id=\"update\" parameterType=\"Xxx\">#update#</update>";
		template += "\r\n";
		template += "</mapper>";

		String insert = "insert into " + tableName + "(";
		for (Map<String, Object> map : lsTable) {
			if (!map.get("colName").toString().toLowerCase().equals("id"))
				insert += map.get("colName").toString().toLowerCase() + ",";
		}
		insert += ") values(";
		for (Map<String, Object> map : lsTable) {
			if (!map.get("colName").toString().toLowerCase().equals("id"))
				insert += "#{" + map.get("colName").toString().toLowerCase()
						+ "},";
		}
		insert += ")";

		String update = "update " + tableName + " set ";
		for (Map<String, Object> map : lsTable) {
			if (!map.get("colName").toString().toLowerCase().equals("id"))
				update += map.get("colName").toString().toLowerCase() + "=#{"
						+ map.get("colName").toString().toLowerCase() + "},";
		}

		update += "where id=#{id}";

		template = template.replaceAll("#package#", packageName)
				.replaceAll("#tableName#", tableName)
				.replaceAll("Xxx", objectName)
				.replaceAll("#insert#", insert.replaceAll(",\\)", ")"))
				.replaceAll("#update#", update.replaceAll(",where", " where"));

		folder = folder + "/dao/";
		File fd = new File(folder);

		if (!fd.exists() && !fd.isDirectory()) {
			System.out.println("//"+folder+"不存在，创建。。。");
			fd.mkdirs();
		} 

		System.out.println(folder + objectName + "Mapper.xml 创建。。。");
		File f = new File(folder + objectName + "Mapper.xml");
		BufferedWriter o = new BufferedWriter(new FileWriter(f));
		o.write(template);
		o.close();

		String javaTemplate = "package #package#.dao;";
		javaTemplate += "\r\n";
		javaTemplate += "public interface XxxMapper extends SuperMapper {";
		javaTemplate += "\r\n";
		javaTemplate += "public List<Xxx> selectListPage(Map<String, Object> param);";
		javaTemplate += "\r\n";
		javaTemplate += "public Xxx selectByID(int id);";
		javaTemplate += "\r\n";
		javaTemplate += "public int deleteByID(int id);";
		javaTemplate += "\r\n";
		javaTemplate += "public int insert(Xxx xxx);	";
		javaTemplate += "\r\n";
		javaTemplate += "public int update(Xxx xxx);";
		javaTemplate += "\r\n}";

		javaTemplate = javaTemplate.replaceAll("#package#", packageName)
				.replaceAll("Xxx", objectName)
				.replaceAll("xxx", objectName.toLowerCase());

		System.out.println(folder + objectName + "Mapper.java 创建。。。");
		f = new File(folder + objectName + "Mapper.java");
		o = new BufferedWriter(new FileWriter(f));
		o.write(javaTemplate);
		o.close();

	}
}
