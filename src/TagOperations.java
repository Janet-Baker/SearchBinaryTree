import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class TagOperations {
    public static ArrayList<StringBuilder> CreateTags(int n) {
        // 最终返回的标签列表
        ArrayList<StringBuilder> list = new ArrayList<>();
        // 使用 HashSet 防止生成重复的标签
        HashSet<StringBuilder> set = new HashSet<>();
        Random rd = new Random();
        // capacity 表示标签ID的长度
        // 16位是有重复ID的，这很不好；64位又太慢。
        // 标签ID是capacity(=16)位的二进制数
        int capacity = 32;
        while (set.size() < n) {
            StringBuilder s = new StringBuilder(capacity);
            // j 为标签ID位数。每一位都是随机生成的0或1。
            for (int j = 0; j < capacity; j++) {
                s.append(rd.nextInt(2));
            }
            set.add(s);
        }
        // 将 HashSet 转换为 ArrayList
        list.addAll(set);

        System.out.println(list.size());
        return list;
    }

    public static int seek(StringBuilder signal, ArrayList<StringBuilder> list) {
        int count = 0;
        int seekResult;
        // 记录符合前缀的第一个标签的id
        StringBuilder first = new StringBuilder();
        for (StringBuilder stringBuilder : list) {
            if (stringBuilder.toString().startsWith(signal.toString())) {
                count++;
                if (count == 1) {
                    // 记录符合前缀的第一个标签的id
                    first = stringBuilder;
                }
            }
        }

        switch (count) {
            case 0:
                // System.out.print("发出信号为 " + signal + "  无响应");
                seekResult = 0;
                break;
            case 1:
                // System.out.print("发出信号为 " + signal + "  识别成功");
                // 识别成功后删除在该树的标签
                String finalFirst = first.toString();
                list.removeIf(e -> e.toString().equals(finalFirst));
                seekResult = 1;
                break;
            default:
                // 有冲突标签，识别失败
                seekResult = 2;
                // System.out.print("发出信号为 " + signal + "  冲突个数" + count);
                break;
        }
        return seekResult;
    }
}
