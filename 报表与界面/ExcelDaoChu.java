
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelDaoChu {

    public void daoChuChengJiBiao(List<BaoBiaoShengCheng.XueShengBaoBiao> baoBiaoLieBiao, String luJing) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("学生成绩报表");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = {"学号", "姓名", "数学", "Java", "体育", "总成绩", "班级总成绩平均值"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 填充数据
        int rowNum = 1;
        for (BaoBiaoShengCheng.XueShengBaoBiao sbb : baoBiaoLieBiao) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sbb.xueHao);
            row.createCell(1).setCellValue(sbb.xingMing);
            row.createCell(2).setCellValue(sbb.keMuChengJi.getOrDefault("数学", 0.0));
            row.createCell(3).setCellValue(sbb.keMuChengJi.getOrDefault("Java", 0.0));
            row.createCell(4).setCellValue(sbb.keMuChengJi.getOrDefault("体育", 0.0));
            row.createCell(5).setCellValue(sbb.zongChengJi);
            row.createCell(6).setCellValue(sbb.banJiZongChengJiPingJun);
        }

        // 写入文件
        try (FileOutputStream outputStream = new FileOutputStream(luJing)) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


