
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class YongHu {
    private static final String YONG_HU_WEN_JIAN = "yonghu.txt";
    private static Map<String, String> yongHuXinXi = new HashMap<>();

    static {
        zaiRuYongHu();
    }

    private static void zaiRuYongHu() {
        try (BufferedReader reader = new BufferedReader(new FileReader(YONG_HU_WEN_JIAN))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    yongHuXinXi.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            // 如果文件不存在，则创建默认用户
            if (yongHuXinXi.isEmpty()) {
                yongHuXinXi.put("admin", "123456");
                baoCunYongHu();
            }
        }
    }

    private static void baoCunYongHu() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(YONG_HU_WEN_JIAN))) {
            for (Map.Entry<String, String> entry : yongHuXinXi.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean yanZheng(String yongHuMing, String miMa) {
        return yongHuXinXi.containsKey(yongHuMing) && yongHuXinXi.get(yongHuMing).equals(miMa);
    }

    public static void tianJiaYongHu(String yongHuMing, String miMa) {
        yongHuXinXi.put(yongHuMing, miMa);
        baoCunYongHu();
    }
}


