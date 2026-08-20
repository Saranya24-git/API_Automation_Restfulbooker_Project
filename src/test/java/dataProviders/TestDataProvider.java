package dataProviders;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import annotations.TestDataSheet;

import utils.ExcelUtils;


public class TestDataProvider
{
	@DataProvider(name="TestData")
	public Object[][] provideTestData(Method method)throws IOException
	{
		ExcelUtils excel = new ExcelUtils();
		String testCaseId = method.getName().split("_")[0];
		Class<?> testClass = method.getDeclaringClass();
		TestDataSheet annotation = testClass.getAnnotation(TestDataSheet.class);
		String sheetname = annotation.sheetName();
		Class<?> modelClass = annotation.model();
		Object data = excel.getTestData(sheetname, testCaseId, modelClass);
		excel.closeWorkbook();
		
		return new Object[][]
				{
					{ data }
				};
				
	}
}