import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BaoBiaoShengCheng {

    public static class XueShengBaoBiao {
        public String xueHao;
        public String xingMing;
        public Map<String, Double> keMuChengJi;
        public double zongChengJi;
        public Map<String, Double> keMuBanJiPingJun;
        public double banJiZongChengJiPingJun;

        public XueShengBaoBiao(String xueHao, String xingMing, Map<String, Double> keMuChengJi, double zongChengJi, Map<String, Double> keMuBanJiPingJun, double banJiZongChengJiPingJun) {
            this.xueHao = xueHao;
            this.xingMing = xingMing;
            this.keMuChengJi = keMuChengJi;
            this.zongChengJi = zongChengJi;
            this.keMuBanJiPingJun = keMuBanJiPingJun;
            this.banJiZongChengJiPingJun = banJiZongChengJiPingJun;
        }
    }

    public List<XueShengBaoBiao> shengChengBaoBiao(List<XueSheng> xueShengLieBiao, Map<String, ChengJi> chengJiMap) {
        List<XueShengBaoBiao> baoBiaoLieBiao = new ArrayList<>();

        // 计算各科目班级平均分
        Map<String, Double> keMuZongFen = new HashMap<>();
        Map<String, Integer> keMuRenShu = new HashMap<>();
        for (ChengJi cj : chengJiMap.values()) {
            for (Map.Entry<String, Double> entry : cj.getKeMuChengJi().entrySet()) {
                keMuZongFen.put(entry.getKey(), keMuZongFen.getOrDefault(entry.getKey(), 0.0) + entry.getValue());
                keMuRenShu.put(entry.getKey(), keMuRenShu.getOrDefault(entry.getKey(), 0) + 1);
            }
        }
        Map<String, Double> keMuBanJiPingJun = new HashMap<>();
        for (Map.Entry<String, Double> entry : keMuZongFen.entrySet()) {
            keMuBanJiPingJun.put(entry.getKey(), entry.getValue() / keMuRenShu.get(entry.getKey()));
        }

        // 计算班级总成绩平均分
        double banJiZongChengJiZongHe = 0;
        int youXiaoXueShengShu = 0;

        for (XueSheng xs : xueShengLieBiao) {
            ChengJi cj = chengJiMap.get(xs.getXueHao());
            if (cj != null) {
                double zongChengJi = cj.getKeMuChengJi().values().stream().mapToDouble(Double::doubleValue).sum();
                banJiZongChengJiZongHe += zongChengJi;
                youXiaoXueShengShu++;

                baoBiaoLieBiao.add(new XueShengBaoBiao(xs.getXueHao(), xs.getXingMing(), cj.getKeMuChengJi(), zongChengJi, keMuBanJiPingJun, 0.0));
            }
        }

        double banJiZongChengJiPingJun = youXiaoXueShengShu > 0 ? banJiZongChengJiZongHe / youXiaoXueShengShu : 0;

        // 更新每个学生的班级总成绩平均分
        for (XueShengBaoBiao sbb : baoBiaoLieBiao) {
            sbb.banJiZongChengJiPingJun = banJiZongChengJiPingJun;
        }

        // 按总成绩降序排序
        Collections.sort(baoBiaoLieBiao, Comparator.comparingDouble(s -> -s.zongChengJi));

        return baoBiaoLieBiao;
    }
}


