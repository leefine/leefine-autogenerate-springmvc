package com.leefine.autogeneratecode;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.leefine.autogeneratecode.dao.TableMapper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:spring-config.xml");

		String tableName = "achv_testindex";
		String objectName = "TestIndex";
		String saveFolder = "D:\\GC";
		String packageName = "com.leefine.eweb.archive";

		@SuppressWarnings("resource")
		Scanner br = new Scanner(System.in);

		while (true) {
			System.out.println("Table Name:");
			tableName = br.nextLine();
			System.out.println("Generate Object Name(Class Name):");
			objectName = br.nextLine();
			System.out.println("Folder to save:");
			saveFolder = br.nextLine();
			System.out.println("Package Name:");
			packageName = br.nextLine();

			TableMapper tableMapper = (TableMapper) context.getBean("tableMapper");
			List<Map<String, Object>> lsTable = tableMapper.getTable(tableName);
			if (lsTable.size() < 1) {
				System.out.println("该表不存在！");
				return;
			}
			saveFolder = saveFolder + "/" + objectName.toLowerCase();
			File fd = new File(saveFolder);
			if (!fd.exists() && !fd.isDirectory()) {
				System.out.println("//" + saveFolder + "不存在，创建。。。");
				fd.mkdirs();
			}

			// 创建VO
			GetVo.generateVo(lsTable, saveFolder, objectName, packageName);

			// 创建Dao
			GetMapper.generateMapper(lsTable, saveFolder, tableName,
					objectName, packageName);
			// 创建服务
			GetService.generateService(lsTable, saveFolder, tableName,
					objectName, packageName);
			// 创建控制层
			GetController.generateController(lsTable, saveFolder, tableName,
					objectName, packageName);

			// 创建Web
			//GetWeb.generateWeb(lsTable, saveFolder, objectName, packageName);

			System.out
					.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Finish^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			System.out
					.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~New Begin~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
	}
}
