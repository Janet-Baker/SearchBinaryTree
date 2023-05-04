import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Table {
    public static void saveAsCsvTable(String outputPath, ArrayList<Integer> all, ArrayList<Integer> times) {
        // 准备输出内容
        StringBuilder content = new StringBuilder("标签个数,识别次数\n");
        Iterator<Integer> allIt = all.iterator();
        Iterator<Integer> timesIt = times.iterator();
        while (allIt.hasNext()) {
            content.append(allIt.next()).append(",").append(timesIt.next()).append("\n");
        }
        writer(outputPath, content);
    }
    public static void saveAsCsvTable(String outputPath, ArrayList<Integer> all, ArrayList<Integer> times, ArrayList<Long> durations) {
        // 准备输出内容
        StringBuilder content = new StringBuilder("标签个数,识别次数,耗时\n");
        Iterator<Integer> allIt = all.iterator();
        Iterator<Integer> timesIt = times.iterator();
        Iterator<Long> durationsIt = durations.iterator();
        while (allIt.hasNext()) {
            content.append(allIt.next()).append(",").append(timesIt.next()).append(",").append(durationsIt.next()).append("\n");
        }
        writer(outputPath, content);
    }

    private static void writer(String outputPath, StringBuilder content) {
        try {
            File outFile = new File(outputPath);
            // 创建目录
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            FileWriter out = new FileWriter(outFile);
            // 写入并关闭文件
            out.write(content.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
