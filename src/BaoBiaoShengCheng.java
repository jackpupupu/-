import java.io.Serializable;

public class BaoBiaoShengCheng {
    // 学生报表数据结构
    public static class XueShengBaoBiao implements Serializable {
        // 学生基本信息
        private final String xueHao;              // 学号
        private final String xingMing;            // 姓名
        
        // 成绩信息
        private final String[] keMuMing;          // 科目名称数组
        private final double[] keMuChengJi;       // 科目成绩数组
        private double zongChengJi;         // 总成绩
        
        // 班级平均分信息
        private final double[] keMuBanJiPingJun;  // 科目班级平均分数组
        private double banJiZongChengJiPingJun;  // 班级总成绩平均值

        // 构造方法
        public XueShengBaoBiao(String xueHao, String xingMing, int keMuShu) {
            this.xueHao = xueHao;
            this.xingMing = xingMing;
            this.keMuMing = new String[keMuShu];
            this.keMuChengJi = new double[keMuShu];
            this.keMuBanJiPingJun = new double[keMuShu];
        }

        // 获取学号
        public String getXueHao() {
            return xueHao;
        }

        // 获取姓名
        public String getXingMing() {
            return xingMing;
        }

        // 获取科目成绩
        public double[] getKeMuChengJi() {
            return keMuChengJi;
        }

        // 获取总成绩
        public double getZongChengJi() {
            return zongChengJi;
        }

        // 获取科目班级平均分
        public double[] getKeMuBanJiPingJun() {
            return keMuBanJiPingJun;
        }

        // 获取班级总成绩平均值
        public double getBanJiZongChengJiPingJun() {
            return banJiZongChengJiPingJun;
        }

        // 设置科目名称和成绩
        public void setKeMuChengJi(String keMu, double chengJi, int i) {
            if (i >= 0 && i < keMuMing.length) {
                keMuMing[i] = keMu;
                keMuChengJi[i] = chengJi;
            }
        }

        // 设置总成绩
        public void setZongChengJi(double zongChengJi) {
            this.zongChengJi = zongChengJi;
        }

        // 设置科目班级平均分
        public void setKeMuBanJiPingJun(double pingJunFen, int i) {
            if (i >= 0 && i < keMuBanJiPingJun.length) {
                keMuBanJiPingJun[i] = pingJunFen;
            }
        }

        // 设置班级总成绩平均值
        public void setBanJiZongChengJiPingJun(double banJiZongChengJiPingJun) {
            this.banJiZongChengJiPingJun = banJiZongChengJiPingJun;
        }

        // 通过科目名获取成绩
        public double getKeMuChengJiByName(String keMu) {
            for (int i = 0; i < keMuMing.length; i++) {
                if (keMuMing[i] != null && keMuMing[i].equals(keMu)) {
                    return keMuChengJi[i];
                }
            }
            return 0;
        }
    }

    // 生成报表的主要方法
    public XueShengBaoBiao[] shengChengBaoBiao(XueSheng[] xueShengList, ChengJi[] chengJiList) {
        // 首先统计有多少个不同的科目
        String[] suoYouKeMu = huoQuSuoYouKeMu(chengJiList);
        int keMuShu = suoYouKeMu.length;
        
        // 创建报表数组
        XueShengBaoBiao[] baoBiaoList = new XueShengBaoBiao[xueShengList.length];
        int youXiaoXueShengShu = 0;  // 实际有成绩的学生数量
        
        // 用于存储班级总分的数组
        double[] keMuZongFen = new double[keMuShu];     // 每个科目的总分
        int[] keMuRenShu = new int[keMuShu];           // 每个科目的人数
        double zongChengJiHe = 0;                       // 班级总成绩之和

        // 第一步：计算班级各科目总分和参与人数
        for (int i = 0; i < xueShengList.length; i++) {
            ChengJi chengJi = huoQuXueShengChengJi(xueShengList[i].getXueHao(), chengJiList);
            
            if (chengJi != null) {
                double xueShengZongFen = 0;
                
                // 遍历所有科目
                for (int j = 0; j < suoYouKeMu.length; j++) {
                    String keMu = suoYouKeMu[j];
                    double chengJiZhi = huoQuKeMuChengJi(chengJi, keMu);
                    
                    if (chengJiZhi >= 0) {  // 如果有这门课的成绩
                        keMuZongFen[j] += chengJiZhi;
                        keMuRenShu[j]++;
                        xueShengZongFen += chengJiZhi;
                    }
                }
                
                zongChengJiHe += xueShengZongFen;
                youXiaoXueShengShu++;
            }
        }

        // 第二步：计算班级平均分
        double[] keMuPingJunFen = new double[keMuShu];
        for (int i = 0; i < keMuShu; i++) {
            if (keMuRenShu[i] > 0) {
                keMuPingJunFen[i] = keMuZongFen[i] / keMuRenShu[i];
            }
        }
        
        // 计算班级总成绩平均分
        double banJiZongChengJiPingJun = 0;
        if (youXiaoXueShengShu > 0) {
            banJiZongChengJiPingJun = zongChengJiHe / youXiaoXueShengShu;
        }

        // 第三步：生成每个学生的报表数据
        int baoBiaoIndex = 0;
        for (int i = 0; i < xueShengList.length; i++) {
            XueSheng xueSheng = xueShengList[i];
            ChengJi chengJi = huoQuXueShengChengJi(xueSheng.getXueHao(), chengJiList);
            
            if (chengJi != null) {
                // 创建学生报表对象
                XueShengBaoBiao baoBiao = new XueShengBaoBiao(xueSheng.getXueHao(), xueSheng.getXingMing(), keMuShu);
                double zongChengJi = 0;
                
                // 设置每个科目的成绩和班级平均分
                for (int j = 0; j < suoYouKeMu.length; j++) {
                    String keMu = suoYouKeMu[j];
                    double chengJiZhi = huoQuKeMuChengJi(chengJi, keMu);
                    
                    baoBiao.setKeMuChengJi(keMu, chengJiZhi, j);
                    baoBiao.setKeMuBanJiPingJun(keMuPingJunFen[j], j);
                    
                    if (chengJiZhi >= 0) {
                        zongChengJi += chengJiZhi;
                    }
                }
                
                baoBiao.setZongChengJi(zongChengJi);
                baoBiao.setBanJiZongChengJiPingJun(banJiZongChengJiPingJun);
                
                baoBiaoList[baoBiaoIndex++] = baoBiao;
            }
        }

        // 创建最终的报表数组（只包含有效数据）
        XueShengBaoBiao[] zuiZhongBaoBiao = new XueShengBaoBiao[youXiaoXueShengShu];
        System.arraycopy(baoBiaoList, 0, zuiZhongBaoBiao, 0, youXiaoXueShengShu);

        // 第四步：按总成绩从高到低排序（冒泡排序）
        for (int i = 0; i < zuiZhongBaoBiao.length - 1; i++) {
            for (int j = 0; j < zuiZhongBaoBiao.length - 1 - i; j++) {
                if (zuiZhongBaoBiao[j].getZongChengJi() < zuiZhongBaoBiao[j + 1].getZongChengJi()) {
                    // 交换位置
                    XueShengBaoBiao temp = zuiZhongBaoBiao[j];
                    zuiZhongBaoBiao[j] = zuiZhongBaoBiao[j + 1];
                    zuiZhongBaoBiao[j + 1] = temp;
                }
            }
        }
        
        return zuiZhongBaoBiao;
    }

    // 辅助方法：获取学生的成绩记录
    private ChengJi huoQuXueShengChengJi(String xueHao, ChengJi[] chengJiList) {
        for (ChengJi chengJi : chengJiList) {
            if (chengJi.getXueHao().equals(xueHao)) {
                return chengJi;
            }
        }
        return null;
    }

    // 辅助方法：获取某门课程的成绩
    private double huoQuKeMuChengJi(ChengJi chengJi, String keMu) {
        Double chengJiZhi = chengJi.getChengJi(keMu);
        return chengJiZhi != null ? chengJiZhi : -1;  // 返回-1表示没有这门课的成绩
    }

    // 辅助方法：获取所有科目名称
    private String[] huoQuSuoYouKeMu(ChengJi[] chengJiList) {
        // 使用一个足够大的临时数组来存储所有可能的科目
        String[] linShiKeMu = new String[100];  // 假设最多100个科目
        int keMuShu = 0;

        // 遍历所有成绩记录，收集不重复的科目名称
        for (ChengJi chengJi : chengJiList) {
            String[] dangQianKeMu = chengJi.getKeMuChengJi().keySet().toArray(new String[0]);
            for (String keMu : dangQianKeMu) {
                boolean cunZai = false;
                for (int i = 0; i < keMuShu; i++) {
                    if (linShiKeMu[i].equals(keMu)) {
                        cunZai = true;
                        break;
                    }
                }
                if (!cunZai) {
                    linShiKeMu[keMuShu++] = keMu;
                }
            }
        }

        // 创建最终的科目数组
        String[] zuiZhongKeMu = new String[keMuShu];
        System.arraycopy(linShiKeMu, 0, zuiZhongKeMu, 0, keMuShu);
        return zuiZhongKeMu;
    }
}