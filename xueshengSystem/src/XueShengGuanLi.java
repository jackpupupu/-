import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XueShengGuanLi {
    private static final String XUE_SHENG_WEN_JIAN = "xuesheng.dat";
    private List<XueSheng> xueShengLieBiao;

    public XueShengGuanLi() {
        xueShengLieBiao = zaiRuXueSheng();
    }

    public void tianJiaXueSheng(XueSheng xueSheng) {
        xueShengLieBiao.add(xueSheng);
        baoCunXueSheng();
    }

    public boolean shanChuXueSheng(String xueHao) {
        boolean removed = xueShengLieBiao.removeIf(s -> s.getXueHao().equals(xueHao));
        if (removed) {
            baoCunXueSheng();
        }
        return removed;
    }

    public boolean xiuGaiXueSheng(String xueHao, String newXingMing, String newXingBie, String newChuShengNianYueRi) {
        for (XueSheng s : xueShengLieBiao) {
            if (s.getXueHao().equals(xueHao)) {
                s.setXingMing(newXingMing);
                s.setXingBie(newXingBie);
                s.setChuShengNianYueRi(newChuShengNianYueRi);
                baoCunXueSheng();
                return true;
            }
        }
        return false;
    }

    public XueSheng chaZhaoXueShengByXueHao(String xueHao) {
        for (XueSheng s : xueShengLieBiao) {
            if (s.getXueHao().equals(xueHao)) {
                return s;
            }
        }
        return null;
    }

    public List<XueSheng> chaZhaoXueShengByXingMing(String xingMing) {
        List<XueSheng> result = new ArrayList<>();
        for (XueSheng s : xueShengLieBiao) {
            if (s.getXingMing().contains(xingMing)) { // 模糊匹配
                result.add(s);
            }
        }
        return result;
    }

    private void baoCunXueSheng() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(XUE_SHENG_WEN_JIAN))) {
            oos.writeObject(xueShengLieBiao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取学生数据文件，返回List<XueSheng>，如果没有则返回空list
    @SuppressWarnings("unchecked") // 这个注解可以消除类型转换的警告
    private List<XueSheng> zaiRuXueSheng() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(XUE_SHENG_WEN_JIAN));
            // 这里强制类型转换，前提是写入时也是List<XueSheng>
            List<XueSheng> list = (List<XueSheng>) ois.readObject();
            ois.close();
            return list;
        } catch (FileNotFoundException e) {
            // 文件不存在就返回空的list
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<XueSheng> getXueShengLieBiao() {
        return xueShengLieBiao;
    }
}


