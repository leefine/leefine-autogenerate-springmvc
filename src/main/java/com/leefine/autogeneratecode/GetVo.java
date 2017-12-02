package com.leefine.autogeneratecode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetVo {

	public static void generateVo(List<Map<String, Object>> lsTable,
			String folder, String objectName, String packageName)
			throws IOException {

		StringBuilder sb = new StringBuilder();
		sb.append("package #package#.vo;\r\n");
		sb.append("\r\n");
		sb.append("@Alias(\"Xxx\")");
		sb.append("\r\n");
		sb.append("public class Xxx extends BaseVo {###pro###}");
		sb.append("\r\n");

		StringBuilder pro = new StringBuilder();
		for (Map<String, Object> map : lsTable) {
			pro.append("\r\n");
			if (map.get("colType").toString().contains("varchar")) {
				pro.append("private String "
						+ map.get("colName").toString().toLowerCase() + ";");

			} else if (map.get("colType").toString().contains("numeric")) {
				pro.append("private int "
						+ map.get("colName").toString().toLowerCase() + ";");

			} else if (map.get("colType").toString().contains("date")) {
				pro.append("private Date "
						+ map.get("colName").toString().toLowerCase() + ";");
			}
			pro.append("\r\n");
			pro.append(getSETGET(map.get("colName").toString().toLowerCase(),
					map.get("colType").toString()));
		}

		String result = sb.toString().replaceAll("Xxx", objectName)
				.replaceAll("###pro###", pro.toString())
				.replaceAll("#package#", packageName);

		folder = folder + "/vo/";
		File fd = new File(folder);

		if (!fd.exists() && !fd.isDirectory()) {
			System.out.println("//"+folder+"不存在，创建。。。");
			fd.mkdirs();
		} 

		System.out.println("//"+folder + objectName + ".java 创建。。。");
		File f = new File(folder + objectName + ".java");
		BufferedWriter o = new BufferedWriter(new FileWriter(f));
		o.write(result);
		o.close();
	}

	private static String getSETGET(String fldName, String type) {
		String first = fldName.substring(0, 1).toUpperCase();
		String rest = fldName.substring(1, fldName.length());
		String filed = new StringBuffer(first).append(rest).toString();
		String result = "";
		if (type.contains("varchar")) {
			result += "public void set" + filed + "(String " + fldName
					+ "){this." + fldName + " = " + fldName + ";}";
			result += "public String get" + filed + "(){return this." + fldName
					+ ";}";

		} else if (type.contains("numeric")) {
			result += "public void set" + filed + "(int " + fldName + "){this."
					+ fldName + " = " + fldName + ";}";
			result += "public int get" + filed + "(){return this." + fldName
					+ ";}";

		} else if (type.contains("date")) {
			result += "public void set" + filed + "(Date " + fldName
					+ "){this." + fldName + " = " + fldName + ";}";
			result += "public Date get" + filed + "(){return this." + fldName
					+ ";}";
		}

		return result;
	}
}
