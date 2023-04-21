import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Table {
    public static void saveAsCsvTable(String outputPath, ArrayList<Integer> all, ArrayList<Integer> times) {
        try {
            File outFile = new File(outputPath);
            // 创建目录
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            FileWriter out = new FileWriter(outFile);
            // 准备输出内容
            StringBuilder content = new StringBuilder();
            Iterator<Integer> allIt = all.iterator();
            Iterator<Integer> timesIt = times.iterator();
            while (allIt.hasNext()) {
                content.append(allIt.next()).append(",").append(timesIt.next()).append("\n");
            }
            // 写入并关闭文件
            out.write(content.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
