import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelDaoChu {
    /**
     * 导出学生成绩报表到Excel文件（只用Java基础知识，参数为数组）
     * @param baoBiaoArray 学生报表对象数组
     * @param filePath 导出文件路径（如 D:/output.xlsx）
     */
    public static void exportToExcel(BaoBiaoShengCheng.XueShengBaoBiao[] baoBiaoArray, String filePath) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("学生成绩报表");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = {"学号", "姓名", "数学", "Java", "体育", "总成绩", "班级总成绩平均值"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 填充数据
        for (int i = 0; i < baoBiaoArray.length; i++) {
            BaoBiaoShengCheng.XueShengBaoBiao b = baoBiaoArray[i];
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(b.getXueHao());
            row.createCell(1).setCellValue(b.getXingMing());
            // 假设科目顺序为：数学、Java、体育
            double[] keMuChengJi = b.getKeMuChengJi();
            for (int j = 0; j < 3; j++) {
                row.createCell(j + 2).setCellValue(keMuChengJi.length > j ? keMuChengJi[j] : 0);
            }
            row.createCell(5).setCellValue(b.getZongChengJi());
            row.createCell(6).setCellValue(b.getBanJiZongChengJiPingJun());
        }

        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 写入文件
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

