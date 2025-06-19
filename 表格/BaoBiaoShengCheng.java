import java.io.Serializable;
import java.util.*;

public class BaoBiaoShengCheng {
    // 学生报表数据结构
    public static class XueShengBaoBiao implements Serializable {
        // 学生基本信息
        private String xueHao;                            // 学号
        private String xingMing;                          // 姓名
        
        // 成绩信息
        private Map<String, Double> keMuChengJi;          // 各科目成绩
        private double zongChengJi;                       // 总成绩
        
        // 班级平均分信息
        private Map<String, Double> keMuBanJiPingJun;    // 各科目班级平均值
        private double banJiZongChengJiPingJun;          // 班级总成绩平均值

        // 构造方法
        public XueShengBaoBiao(String xueHao, String xingMing) {
            this.xueHao = xueHao;
            this.xingMing = xingMing;
            this.keMuChengJi = new HashMap<String, Double>();
            this.keMuBanJiPingJun = new HashMap<String, Double>();
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
        public Map<String, Double> getKeMuChengJi() {
            return keMuChengJi;
        }

        // 获取总成绩
        public double getZongChengJi() {
            return zongChengJi;
        }

        // 获取科目班级平均分
        public Map<String, Double> getKeMuBanJiPingJun() {
            return keMuBanJiPingJun;
        }

        // 获取班级总成绩平均值
        public double getBanJiZongChengJiPingJun() {
            return banJiZongChengJiPingJun;
        }

        // 设置科目成绩
        public void setKeMuChengJi(Map<String, Double> keMuChengJi) {
            this.keMuChengJi = keMuChengJi;
        }

        // 设置总成绩
        public void setZongChengJi(double zongChengJi) {
            this.zongChengJi = zongChengJi;
        }

        // 设置科目班级平均分
        public void setKeMuBanJiPingJun(Map<String, Double> keMuBanJiPingJun) {
            this.keMuBanJiPingJun = keMuBanJiPingJun;
        }

        // 设置班级总成绩平均值
        public void setBanJiZongChengJiPingJun(double banJiZongChengJiPingJun) {
            this.banJiZongChengJiPingJun = banJiZongChengJiPingJun;
        }
    }

    // 生成报表的主要方法
    public List<XueShengBaoBiao> shengChengBaoBiao(List<XueSheng> xueShengList, Map<String, ChengJi> chengJiMap) {
        // 创建报表列表
        List<XueShengBaoBiao> baoBiaoList = new ArrayList<XueShengBaoBiao>();
        
        // 创建存储班级总分和人数的变量
        Map<String, Double> keMuZongFen = new HashMap<String, Double>();    // 存储每个科目的总分
        Map<String, Integer> keMuRenShu = new HashMap<String, Integer>();   // 存储每个科目的人数
        double zongChengJiHe = 0;                                           // 班级总成绩之和
        int youXiaoXueShengShu = 0;                                        // 有成绩的学生数量

        // 第一步：计算班级各科目总分和参与人数
        for (int i = 0; i < xueShengList.size(); i++) {
            XueSheng xueSheng = xueShengList.get(i);
            ChengJi chengJi = chengJiMap.get(xueSheng.getXueHao());
            
            // 如果该学生有成绩记录
            if (chengJi != null) {
                Map<String, Double> keMuChengJi = chengJi.getKeMuChengJi();
                double xueShengZongFen = 0;
                
                // 遍历该学生的所有科目成绩
                for (String keMu : keMuChengJi.keySet()) {
                    double fenshu = keMuChengJi.get(keMu);
                    
                    // 累加科目总分
                    if (keMuZongFen.containsKey(keMu)) {
                        keMuZongFen.put(keMu, keMuZongFen.get(keMu) + fenshu);
                    } else {
                        keMuZongFen.put(keMu, fenshu);
                    }
                    
                    // 累加科目人数
                    if (keMuRenShu.containsKey(keMu)) {
                        keMuRenShu.put(keMu, keMuRenShu.get(keMu) + 1);
                    } else {
                        keMuRenShu.put(keMu, 1);
                    }
                    
                    xueShengZongFen = xueShengZongFen + fenshu;
                }
                
                zongChengJiHe = zongChengJiHe + xueShengZongFen;
                youXiaoXueShengShu = youXiaoXueShengShu + 1;
            }
        }

        // 第二步：计算班级平均分
        Map<String, Double> keMuPingJunFen = new HashMap<String, Double>();
        // 计算每个科目的平均分
        for (String keMu : keMuZongFen.keySet()) {
            double zongFen = keMuZongFen.get(keMu);
            int renShu = keMuRenShu.get(keMu);
            double pingJunFen = zongFen / renShu;
            keMuPingJunFen.put(keMu, pingJunFen);
        }
        
        // 计算班级总成绩平均分
        double banJiZongChengJiPingJun = 0;
        if (youXiaoXueShengShu > 0) {
            banJiZongChengJiPingJun = zongChengJiHe / youXiaoXueShengShu;
        }

        // 第三步：生成每个学生的报表数据
        for (int i = 0; i < xueShengList.size(); i++) {
            XueSheng xueSheng = xueShengList.get(i);
            ChengJi chengJi = chengJiMap.get(xueSheng.getXueHao());
            
            // 如果该学生有成绩记录
            if (chengJi != null) {
                // 创建学生报表对象
                XueShengBaoBiao baoBiao = new XueShengBaoBiao(xueSheng.getXueHao(), xueSheng.getXingMing());
                
                // 获取该学生的所有科目成绩
                Map<String, Double> keMuChengJi = chengJi.getKeMuChengJi();
                
                // 计算该学生的总成绩
                double zongChengJi = 0;
                for (String keMu : keMuChengJi.keySet()) {
                    zongChengJi = zongChengJi + keMuChengJi.get(keMu);
                }
                
                // 设置学生报表数据
                baoBiao.setKeMuChengJi(keMuChengJi);
                baoBiao.setZongChengJi(zongChengJi);
                baoBiao.setKeMuBanJiPingJun(keMuPingJunFen);
                baoBiao.setBanJiZongChengJiPingJun(banJiZongChengJiPingJun);
                
                // 添加到报表列表
                baoBiaoList.add(baoBiao);
            }
        }

        // 第四步：按总成绩从高到低排序
        for (int i = 0; i < baoBiaoList.size() - 1; i++) {
            for (int j = 0; j < baoBiaoList.size() - 1 - i; j++) {
                if (baoBiaoList.get(j).getZongChengJi() < baoBiaoList.get(j + 1).getZongChengJi()) {
                    // 交换位置
                    XueShengBaoBiao temp = baoBiaoList.get(j);
                    baoBiaoList.set(j, baoBiaoList.get(j + 1));
                    baoBiaoList.set(j + 1, temp);
                }
            }
        }
        
        return baoBiaoList;
    }
}