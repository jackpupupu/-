import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ZhuJieMian extends JFrame {
    private XueShengGuanLi xueShengGuanLi;
    private ChengJiGuanLi chengJiGuanLi;
    private BaoBiaoShengCheng baoBiaoShengCheng;
    public ZhuJieMian() {
        setTitle("学生成绩管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        xueShengGuanLi = new XueShengGuanLi();
        chengJiGuanLi = new ChengJiGuanLi();
        baoBiaoShengCheng = new BaoBiaoShengCheng();
        new ExcelDaoChu();

        JTabbedPane tabbedPane = new JTabbedPane();

        // 学生管理界面
        JPanel xueShengPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("学生管理", xueShengPanel);
        chuangJianXueShengGuanLiJieMian(xueShengPanel);

        // 成绩管理界面
        JPanel chengJiPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("成绩管理", chengJiPanel);
        chuangJianChengJiGuanLiJieMian(chengJiPanel);

        // 报表生成界面
        JPanel baoBiaoPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("成绩报表", baoBiaoPanel);
        chuangJianBaoBiaoJieMian(baoBiaoPanel);

        add(tabbedPane);
        setVisible(true);
    }

    private void chuangJianXueShengGuanLiJieMian(JPanel panel) {
        // 添加学生
        JPanel addPanel = new JPanel(new GridLayout(4, 2));
        JTextField nameField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField dobField = new JTextField();
        JButton addButton = new JButton("添加学生");

        addPanel.add(new JLabel("姓名:"));
        addPanel.add(nameField);
        addPanel.add(new JLabel("性别:"));
        addPanel.add(genderField);
        addPanel.add(new JLabel("出生年月日:"));
        addPanel.add(dobField);
        addPanel.add(addButton);

        addButton.addActionListener(e -> {
            XueSheng xs = new XueSheng(nameField.getText(), genderField.getText(), dobField.getText());
            xueShengGuanLi.tianJiaXueSheng(xs);
            JOptionPane.showMessageDialog(this, "学生添加成功，学号: " + xs.getXueHao());
        });

        // 删除学生
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField deleteXueHaoField = new JTextField(10);
        JButton deleteButton = new JButton("删除学生");
        deletePanel.add(new JLabel("学号:"));
        deletePanel.add(deleteXueHaoField);
        deletePanel.add(deleteButton);
        deleteButton.addActionListener(e -> {
            String xueHao = deleteXueHaoField.getText();
            if (xueShengGuanLi.shanChuXueSheng(xueHao)) {
                JOptionPane.showMessageDialog(this, "删除成功");
            } else {
                JOptionPane.showMessageDialog(this, "未找到该学号的学生");
            }
        });

        // 修改学生
        JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField updateXueHaoField = new JTextField(8);
        JTextField updateNameField = new JTextField(6);
        JTextField updateGenderField = new JTextField(4);
        JTextField updateDobField = new JTextField(8);
        JButton updateButton = new JButton("修改学生");
        updatePanel.add(new JLabel("学号:"));
        updatePanel.add(updateXueHaoField);
        updatePanel.add(new JLabel("新姓名:"));
        updatePanel.add(updateNameField);
        updatePanel.add(new JLabel("新性别:"));
        updatePanel.add(updateGenderField);
        updatePanel.add(new JLabel("新出生年月日:"));
        updatePanel.add(updateDobField);
        updatePanel.add(updateButton);
        updateButton.addActionListener(e -> {
            String xueHao = updateXueHaoField.getText();
            String newName = updateNameField.getText();
            String newGender = updateGenderField.getText();
            String newDob = updateDobField.getText();
            if (xueShengGuanLi.xiuGaiXueSheng(xueHao, newName, newGender, newDob)) {
                JOptionPane.showMessageDialog(this, "修改成功");
            } else {
                JOptionPane.showMessageDialog(this, "未找到该学号的学生");
            }
        });

        panel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel(new GridLayout(3, 1));
        northPanel.add(addPanel);
        northPanel.add(deletePanel);
        northPanel.add(updatePanel);
        panel.add(northPanel, BorderLayout.NORTH);

        // 显示学生列表
        JTextArea studentListArea = new JTextArea();
        studentListArea.setEditable(false);
        JButton refreshButton = new JButton("刷新学生列表");
        refreshButton.addActionListener(e -> {
            studentListArea.setText("");
            for (XueSheng xs : xueShengGuanLi.getXueShengLieBiao()) {
                studentListArea.append(xs.toString() + "\n");
            }
        });
        panel.add(new JScrollPane(studentListArea), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);
    }

    private void chuangJianChengJiGuanLiJieMian(JPanel panel) {
        // 批量添加成绩（支持同一科目下不同学生不同成绩）
        JPanel addScorePanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField courseField = new JTextField(8);
        inputPanel.add(new JLabel("科目:"));
        inputPanel.add(courseField);
        addScorePanel.add(inputPanel, BorderLayout.NORTH);
        JTextArea batchInputArea = new JTextArea(5, 30);
        batchInputArea.setBorder(BorderFactory.createTitledBorder("每行输入：学号,成绩"));
        addScorePanel.add(new JScrollPane(batchInputArea), BorderLayout.CENTER);
        JButton addBatchScoreButton = new JButton("批量录入成绩");
        addScorePanel.add(addBatchScoreButton, BorderLayout.SOUTH);
        addBatchScoreButton.addActionListener(e -> {
            String course = courseField.getText();
            String[] lines = batchInputArea.getText().split("\\n");
            int success = 0, fail = 0;
            for (String line : lines) {
                String[] parts = line.trim().split(",");
                if (parts.length == 2) {
                    String xueHao = parts[0].trim();
                    try {
                        double score = Double.parseDouble(parts[1].trim());
                        java.util.Collections.singletonList(xueHao);
                        chengJiGuanLi.piLiangTianJiaChengJi(java.util.Collections.singletonList(xueHao), course, score);
                        success++;
                    } catch (NumberFormatException ex) {
                        fail++;
                    }
                } else if (!line.trim().isEmpty()) {
                    fail++;
                }
            }
            JOptionPane.showMessageDialog(this, "成功录入：" + success + "，失败：" + fail);
        });

        // 单个学生成绩录入
        JPanel singleScorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField singleXueHaoField = new JTextField(8);
        JTextField singleCourseField = new JTextField(6);
        JTextField singleScoreField = new JTextField(4);
        JButton singleAddScoreButton = new JButton("录入单个学生成绩");
        singleScorePanel.add(new JLabel("学号:"));
        singleScorePanel.add(singleXueHaoField);
        singleScorePanel.add(new JLabel("科目:"));
        singleScorePanel.add(singleCourseField);
        singleScorePanel.add(new JLabel("成绩:"));
        singleScorePanel.add(singleScoreField);
        singleScorePanel.add(singleAddScoreButton);
        singleAddScoreButton.addActionListener(e -> {
            String xueHao = singleXueHaoField.getText();
            String keMu = singleCourseField.getText();
            String chengJiStr = singleScoreField.getText();
            try {
                double chengJi = Double.parseDouble(chengJiStr);
                List<String> xueHaoList = java.util.Collections.singletonList(xueHao);
                chengJiGuanLi.piLiangTianJiaChengJi(xueHaoList, keMu, chengJi);
                JOptionPane.showMessageDialog(this, "成绩录入成功！");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "成绩请输入数字！");
            }
        });

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(addScorePanel);
        topPanel.add(singleScorePanel);
        panel.add(topPanel, BorderLayout.NORTH);

        // 查询成绩
        JPanel queryPanel = new JPanel(new GridLayout(2, 2));
        JTextField queryInput = new JTextField();
        JButton queryByIdButton = new JButton("按学号查询");
        JButton queryByNameButton = new JButton("按姓名查询");
        JTextArea queryResultArea = new JTextArea();
        queryResultArea.setEditable(false);

        queryPanel.add(new JLabel("学号/姓名:"));
        queryPanel.add(queryInput);
        queryPanel.add(queryByIdButton);
        queryPanel.add(queryByNameButton);

        queryByIdButton.addActionListener(e -> {
            String xueHao = queryInput.getText();
            ChengJi cj = chengJiGuanLi.chaZhaoChengJiByXueHao(xueHao);
            if (cj != null) {
                XueSheng xs = xueShengGuanLi.chaZhaoXueShengByXueHao(xueHao);
                queryResultArea.setText("姓名: " + xs.getXingMing() + ", 学号: " + xs.getXueHao() + ", 成绩: " + cj.getKeMuChengJi());
            } else {
                queryResultArea.setText("学号不存在！");
            }
        });

        queryByNameButton.addActionListener(e -> {
            String xingMing = queryInput.getText();
            List<ChengJi> chengJiList = chengJiGuanLi.chaZhaoChengJiByXingMing(xueShengGuanLi.getXueShengLieBiao(), xingMing);
            if (!chengJiList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (ChengJi cj : chengJiList) {
                    XueSheng xs = xueShengGuanLi.chaZhaoXueShengByXueHao(cj.getXueHao());
                    sb.append("姓名: ").append(xs.getXingMing()).append(", 学号: ").append(xs.getXueHao()).append(", 成绩: ").append(cj.getKeMuChengJi()).append("\n");
                }
                queryResultArea.setText(sb.toString());
            } else {
                queryResultArea.setText("姓名不存在！");
            }
        });

        panel.add(queryPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(queryResultArea), BorderLayout.SOUTH);
    }

    private void chuangJianBaoBiaoJieMian(JPanel panel) {
        JButton generateReportButton = new JButton("生成学生学习情况报表");
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        JButton exportExcelButton = new JButton("导出到Excel");

        generateReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XueSheng[] xueShengArr = xueShengGuanLi.getXueShengLieBiao().toArray(new XueSheng[0]);
                ChengJi[] chengJiArr = chengJiGuanLi.getChengJiMap().values().toArray(new ChengJi[0]);
                BaoBiaoShengCheng.XueShengBaoBiao[] baoBiaoArr = baoBiaoShengCheng.shengChengBaoBiao(xueShengArr, chengJiArr);
                String text = "";
                for (int i = 0; i < baoBiaoArr.length; i++) {
                    BaoBiaoShengCheng.XueShengBaoBiao sbb = baoBiaoArr[i];
                    text += "学号: " + sbb.getXueHao()
                          + ", 姓名: " + sbb.getXingMing()
                          + ", 数学: " + sbb.getKeMuChengJiByName("数学")
                          + ", Java: " + sbb.getKeMuChengJiByName("Java")
                          + ", 体育: " + sbb.getKeMuChengJiByName("体育")
                          + ", 总成绩: " + sbb.getZongChengJi()
                          + ", 班级总成绩平均值: " + sbb.getBanJiZongChengJiPingJun()
                          + "\n";
                }
                reportArea.setText(text);
            }
        });

        exportExcelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XueSheng[] xueShengArr = xueShengGuanLi.getXueShengLieBiao().toArray(new XueSheng[0]);
                ChengJi[] chengJiArr = chengJiGuanLi.getChengJiMap().values().toArray(new ChengJi[0]);
                BaoBiaoShengCheng.XueShengBaoBiao[] baoBiaoArr = baoBiaoShengCheng.shengChengBaoBiao(xueShengArr, chengJiArr);
                ExcelDaoChu.exportToExcel(baoBiaoArr, "成绩表.xlsx");
                JOptionPane.showMessageDialog(null, "成绩报表已导出到 成绩表.xlsx");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateReportButton);
        buttonPanel.add(exportExcelButton);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // 启动登录界面
        SwingUtilities.invokeLater(DengLuJieMian::new);
    }
}


