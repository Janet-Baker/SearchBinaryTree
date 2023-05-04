import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public final class TagOperations {
    // capacity 表示二进制数的深度限制
    // 16位是有重复的，这很不好；64位又太吃内存。
    // 标签ID是capacity(=32)位的二进制数
    public static final int capacity = 32;

    // CreateTags(int n) 创建 n 个标签
    public static ArrayList<String> createTags(int amountOfTagsToCreate) {
        // 最终返回的标签列表
        // 使用 HashSet 防止生成重复的标签
        HashSet<String> setOfTags = new HashSet<>();
        Random rd = new Random();
        while (setOfTags.size() < amountOfTagsToCreate) {
            StringBuilder s = new StringBuilder(capacity);
            // j 为标签ID位数。每一位都是随机生成的0或1。
            for (int j = 0; j < capacity; j++) {
                s.append(rd.nextInt(2));
            }
            setOfTags.add(s.toString());
        }
        // 将 HashSet 转换为 ArrayList
        // ArrayList<String> tagList = new ArrayList<>(setOfTags);
        return new ArrayList<>(setOfTags);
    }

    public static int seek(StringBuilder signal, ArrayList<String> tagsToProcess) {
        int count = 0;
        // 记录符合前缀的第一个标签的id
        String first = null;
        String finalSignal = signal.toString();
        for (String string : tagsToProcess) {
            if (string.startsWith(finalSignal)) {
                count++;
                if (count == 1) {
                    // 记录符合前缀的第一个标签的id
                    first = string;
                } else if (count == 2){
                    // 有冲突，不用继续循环了
                    return 2;
                }
            }
        }

        switch (count) {
            case 0:
                // System.out.print("发出信号为 " + signal + "  无响应");
                return 0;
            case 1:
                // System.out.print("发出信号为 " + signal + "  识别成功");
                // 识别成功后删除在该树的标签
                String finalFirst = first;
                tagsToProcess.removeIf(e -> e.equals(finalFirst));
                return 1;
            default:
                return 2;
        }
    }
}
