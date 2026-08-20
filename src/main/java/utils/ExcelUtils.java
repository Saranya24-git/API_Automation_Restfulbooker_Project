package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import constants.PathConstants;

public class ExcelUtils
{
	 private XSSFWorkbook workbook;
	 private DataFormatter formatter = new DataFormatter();	
	
	public ExcelUtils() throws IOException
	{
	    FileInputStream fis = new FileInputStream(PathConstants.TEST_DATA_PATH);
	    workbook = new XSSFWorkbook(fis);
	    fis.close();
	}
	 
	 public <T> T getTestData(String sheetName,String testCaseId, Class<T> modelClass) 
		{
			Sheet sheet = workbook.getSheet(sheetName);			
			int totalRows = sheet.getLastRowNum();
			int totalColumns = sheet.getRow(0).getLastCellNum();
			for(int i=1;i<=totalRows;i++)
			{
				Row row = sheet.getRow(i);
				Cell cell = row.getCell(0);
				String currentTestCaseId = formatter.formatCellValue(cell);	
				if(currentTestCaseId.equals(testCaseId))
				{	
					T dataObject;
					try
					{
					   dataObject = modelClass.getDeclaredConstructor().newInstance();					
					   for(int j=1;j<totalColumns;j++)
					   {
						   Cell headerCell = sheet.getRow(0).getCell(j);
						   String headerName = formatter.formatCellValue(headerCell);
						   Cell currentCell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);	
						   String cellValue = formatter.formatCellValue(currentCell);
						   String setterMethodName = "set" + headerName;
						   Method setterMethod = null;
						   for (Method method : modelClass.getMethods())
						   {
						       if (method.getName().equals(setterMethodName))
						       {
						           setterMethod = method;
						           break;
						       }
						   }
						   Class<?> parameterType = setterMethod.getParameterTypes()[0];
						   Object convertedValue;
						   if (parameterType == String.class)
						   {
						       convertedValue = cellValue;
						   }
						   else if (parameterType == Integer.class)
						   {
						       convertedValue = Integer.valueOf(cellValue);
						   }
						   else if (parameterType == Boolean.class)
						   {
						       convertedValue = Boolean.valueOf(cellValue);
						   }
						   else if (parameterType == LocalDate.class)
						   {
						       DateTimeFormatter formatter;

						        if (cellValue.contains("-"))
						        {
						            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						        }
						        else if (cellValue.contains("/"))
						        {
						            formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
						        }
						        else
						        {
						            throw new IllegalArgumentException(
						                    "Unsupported date format: " + cellValue);
						        }

						        convertedValue = LocalDate.parse(cellValue, formatter);
						   }
						   else
						   {
						       throw new RuntimeException(
						           "Unsupported data type: "
						           + parameterType.getName());
						   }
						   
						   setterMethod.invoke(dataObject, convertedValue);						
					   }	
						return dataObject;	
					}
					catch(Exception e)
					{
						 throw new RuntimeException("Unable to map Excel data to "+ modelClass.getSimpleName(),e);
					}
				 }
			}				
		throw new RuntimeException("Test Case ID '" + testCaseId + "' not found in sheet '" + sheetName + "'");
		
	}
	 
	 public void closeWorkbook() throws IOException
		{
		    workbook.close();
		}
}