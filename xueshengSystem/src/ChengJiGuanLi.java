import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChengJiGuanLi {
    private static final String CHENG_JI_WEN_JIAN = "chengji.dat";
    private Map<String, ChengJi> chengJiMap;

    public ChengJiGuanLi() {
        chengJiMap = zaiRuChengJi();
    }

    public void piLiangTianJiaChengJi(List<String> xueHaoLieBiao, String keMu, double chengJi) {
        for (String xueHao : xueHaoLieBiao) {
            ChengJi studentChengJi = chengJiMap.getOrDefault(xueHao, new ChengJi(xueHao));
            studentChengJi.tianJiaChengJi(keMu, chengJi);
            chengJiMap.put(xueHao, studentChengJi);
        }
        baoCunChengJi();
    }

    public ChengJi chaZhaoChengJiByXueHao(String xueHao) {
        return chengJiMap.get(xueHao);
    }

    public List<ChengJi> chaZhaoChengJiByXingMing(List<XueSheng> xueShengLieBiao, String xingMing) {
        List<ChengJi> result = new ArrayList<>();
        for (XueSheng xs : xueShengLieBiao) {
            if (xs.getXingMing().contains(xingMing)) { // 模糊匹配
                ChengJi cj = chengJiMap.get(xs.getXueHao());
                if (cj != null) {
                    result.add(cj);
                }
            }
        }
        return result;
    }

    public void shengChengCeShiShuJu(List<XueSheng> xueShengLieBiao, int shuLiang) {
        Random random = new Random();
        String[] keMu = {"数学", "Java", "体育"};

        for (int i = 0; i < shuLiang; i++) {
            // 确保学号唯一性，这里简化处理，实际应从XueShengGuanLi获取
            String xueHao = "test_" + (i + 1);
            // 假设学生信息已存在，这里仅生成成绩
            ChengJi studentChengJi = new ChengJi(xueHao);
            for (String k : keMu) {
                // 以80分为中心的正态分布成绩
                double score = random.nextGaussian() * 10 + 80; // 假设标准差为10
                score = Math.max(0, Math.min(100, score)); // 确保成绩在0-100之间
                studentChengJi.tianJiaChengJi(k, score);
            }
            chengJiMap.put(xueHao, studentChengJi);
        }
        baoCunChengJi();
    }

    public void daoRuChengJi(String luJing) {
        // 文本文件导入，假设格式为：学号,科目,成绩
        try (BufferedReader reader = new BufferedReader(new FileReader(luJing))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String xueHao = parts[0];
                    String keMu = parts[1];
                    double chengJi = Double.parseDouble(parts[2]);
                    ChengJi studentChengJi = chengJiMap.getOrDefault(xueHao, new ChengJi(xueHao));
                    studentChengJi.tianJiaChengJi(keMu, chengJi);
                    chengJiMap.put(xueHao, studentChengJi);
                }
            }
            baoCunChengJi();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void daoChuChengJi(String luJing) {
        // 导出到文本文件，格式为：学号,科目1:成绩1,科目2:成绩2...
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(luJing))) {
            for (Map.Entry<String, ChengJi> entry : chengJiMap.entrySet()) {
                StringBuilder sb = new StringBuilder(entry.getKey());
                for (Map.Entry<String, Double> keMuEntry : entry.getValue().getKeMuChengJi().entrySet()) {
                    sb.append(",").append(keMuEntry.getKey()).append(":").append(keMuEntry.getValue());
                }
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void baoCunChengJi() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CHENG_JI_WEN_JIAN))) {
            oos.writeObject(chengJiMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取成绩数据文件，返回Map<String, ChengJi>，如果没有则返回空map
    @SuppressWarnings("unchecked") // 这个注解可以消除类型转换的警告
    private Map<String, ChengJi> zaiRuChengJi() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CHENG_JI_WEN_JIAN));
            // 这里强制类型转换，前提是写入时也是Map<String, ChengJi>
            Map<String, ChengJi> map = (Map<String, ChengJi>) ois.readObject();
            ois.close();
            return map;
        } catch (FileNotFoundException e) {
            // 文件不存在就返回空的map
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public Map<String, ChengJi> getChengJiMap() {
        return chengJiMap;
    }
}


