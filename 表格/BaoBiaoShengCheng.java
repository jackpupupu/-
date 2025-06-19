import java.io.Serializable;
import java.util.*;

public class BaoBiaoShengCheng {
    // 学生报表数据结构
    public static class XueShengBaoBiao implements Serializable {
        private String xueHao;                            // 学号
        private String xingMing;                          // 姓名
        private Map<String, Double> keMuChengJi;          // 各科目成绩
        private double zongChengJi;                       // 总成绩
        private Map<String, Double> keMuBanJiPingJun;    // 各科目班级平均值
        private double banJiZongChengJiPingJun;          // 班级总成绩平均值

        public XueShengBaoBiao(String xueHao, String xingMing) {
            this.xueHao = xueHao;
            this.xingMing = xingMing;
            this.keMuChengJi = new HashMap<>();
            this.keMuBanJiPingJun = new HashMap<>();
        }

        // Getters
        public String getXueHao() { return xueHao; }
        public String getXingMing() { return xingMing; }
        public Map<String, Double> getKeMuChengJi() { return keMuChengJi; }
        public double getZongChengJi() { return zongChengJi; }
        public Map<String, Double> getKeMuBanJiPingJun() { return keMuBanJiPingJun; }
        public double getBanJiZongChengJiPingJun() { return banJiZongChengJiPingJun; }

        // Setters
        public void setKeMuChengJi(Map<String, Double> keMuChengJi) {
            this.keMuChengJi = keMuChengJi;
        }
        public void setZongChengJi(double zongChengJi) {
            this.zongChengJi = zongChengJi;
        }
        public void setKeMuBanJiPingJun(Map<String, Double> keMuBanJiPingJun) {
            this.keMuBanJiPingJun = keMuBanJiPingJun;
        }
        public void setBanJiZongChengJiPingJun(double banJiZongChengJiPingJun) {
            this.banJiZongChengJiPingJun = banJiZongChengJiPingJun;
        }
    }

    // 生成报表核心方法
    public List<XueShengBaoBiao> shengChengBaoBiao(List<XueSheng> xueShengList, Map<String, ChengJi> chengJiMap) {
        // 存储最终的报表数据
        List<XueShengBaoBiao> baoBiaoList = new ArrayList<>();
        
        // 用于存储各科目总分和参与人数
        Map<String, Double> keMuZongFen = new HashMap<>();
        Map<String, Integer> keMuRenShu = new HashMap<>();
        double zongChengJiHe = 0;
        int youXiaoXueShengShu = 0;

        // 第一次遍历：计算各科目总分和参与人数
        for (XueSheng xueSheng : xueShengList) {
            ChengJi chengJi = chengJiMap.get(xueSheng.getXueHao());
            if (chengJi != null) {
                Map<String, Double> keMuChengJi = chengJi.getKeMuChengJi();
                double xueShengZongFen = 0;
                
                for (Map.Entry<String, Double> entry : keMuChengJi.entrySet()) {
                    String keMu = entry.getKey();
                    Double fenshu = entry.getValue();
                    
                    keMuZongFen.merge(keMu, fenshu, Double::sum);
                    keMuRenShu.merge(keMu, 1, Integer::sum);
                    xueShengZongFen += fenshu;
                }
                
                zongChengJiHe += xueShengZongFen;
                youXiaoXueShengShu++;
            }
        }

        // 计算各科目平均分和班级总成绩平均值
        Map<String, Double> keMuPingJunFen = new HashMap<>();
        for (String keMu : keMuZongFen.keySet()) {
            double pingJunFen = keMuZongFen.get(keMu) / keMuRenShu.get(keMu);
            keMuPingJunFen.put(keMu, pingJunFen);
        }
        double banJiZongChengJiPingJun = youXiaoXueShengShu > 0 ? zongChengJiHe / youXiaoXueShengShu : 0;

        // 第二次遍历：构建学生报表对象
        for (XueSheng xueSheng : xueShengList) {
            ChengJi chengJi = chengJiMap.get(xueSheng.getXueHao());
            if (chengJi != null) {
                XueShengBaoBiao baoBiao = new XueShengBaoBiao(xueSheng.getXueHao(), xueSheng.getXingMing());
                
                Map<String, Double> keMuChengJi = chengJi.getKeMuChengJi();
                double zongChengJi = 0;
                
                for (Map.Entry<String, Double> entry : keMuChengJi.entrySet()) {
                    zongChengJi += entry.getValue();
                }
                
                baoBiao.setKeMuChengJi(keMuChengJi);
                baoBiao.setZongChengJi(zongChengJi);
                baoBiao.setKeMuBanJiPingJun(keMuPingJunFen);
                baoBiao.setBanJiZongChengJiPingJun(banJiZongChengJiPingJun);
                
                baoBiaoList.add(baoBiao);
            }
        }

        // 按总成绩降序排序
        Collections.sort(baoBiaoList, (a, b) -> Double.compare(b.getZongChengJi(), a.getZongChengJi()));
        
        return baoBiaoList;
    }
}