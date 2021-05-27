package com.example.handlingformsubmission;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;

@CrossOrigin
@RestController
@RequestMapping("/")
public class EmployeeController {

    @PostMapping(value="/")
    public String register(@RequestBody Employee employee){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee);
            Employee emp = mapper.readValue(jsonStr, Employee.class);

            System.out.println(jsonStr);
            emp.setRelationship("Employee");

            String filename="regDetails.xlsx";
            FileInputStream  fs=new FileInputStream(filename);

            Workbook book= WorkbookFactory.create(fs);
            fs.close();
            Sheet worksheet=book.getSheetAt(0);
            int lastRow=worksheet.getLastRowNum();

            Row row=worksheet.createRow(++lastRow);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getName());
            row.createCell(2).setCellValue(emp.getAge());
            row.createCell(3).setCellValue(emp.getGender());
            row.createCell(4).setCellValue(emp.getRelationship());
            row.createCell(5).setCellValue(emp.getDose());
            if(emp.getDose()!=1)
                row.createCell(6).setCellValue(emp.getVaccine());
            else
                row.createCell(6).setCellValue("Any");
            row.createCell(7).setCellValue(emp.getLocation());

            for(Dependent dependent:emp.getDependents()){
                row=worksheet.createRow(++lastRow);
                row.createCell(0).setCellValue(emp.getId());
                row.createCell(1).setCellValue(dependent.getName());
                row.createCell(2).setCellValue(dependent.getAge());
                row.createCell(3).setCellValue(dependent.getGender());
                row.createCell(4).setCellValue(dependent.getRelationship());
                row.createCell(5).setCellValue(dependent.getDose());
                if(dependent.getDose()!=1)
                    row.createCell(6).setCellValue(dependent.getVaccine());
                else
                    row.createCell(6).setCellValue("Any");
                row.createCell(7).setCellValue(emp.getLocation());
            }
            FileOutputStream fileOutputStream=new FileOutputStream(filename);
            book.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return jsonStr;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
}
