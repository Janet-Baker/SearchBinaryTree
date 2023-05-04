import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 取仿真次数
        System.out.println("仿真次数");
        int count = new Scanner(System.in).nextInt();
        if (count <= 0) return;

        // 保存每次仿真的成功次数
        ArrayList<Integer> times = new ArrayList<>();
        // 保存每次仿真的标签数
        ArrayList<Integer> all = new ArrayList<>();
        // 对每一次仿真
        // countLeft 为剩余仿真次数
        for (int countLeft = count; countLeft > 0; countLeft--) {
            // 仿真的成功次数
            int time = 0;
            // 成功个数
            int success = 0;
            // 取标签总数
            System.out.println("标签总数为");
            int n = new Scanner(System.in).nextInt();
            // 保存标签总数，用于画图
            all.add(n);
            // 保存标签的列表
            ArrayList<String> tagList = TagOperations.createTags(n);
            // 保存二进制前缀的列表
            ArrayList<StringBuilder> signalList = new ArrayList<>();
            // 二进制前缀 当前发出信号
            StringBuilder currentSignal = new StringBuilder(TagOperations.capacity);
            // 查找结果
            int seekResult;
            // 初始化前缀列表（二进制树，先根序存储）
            signalList.add(new StringBuilder(currentSignal).append('0'));
            signalList.add(new StringBuilder(currentSignal).append('1'));
            // 对每一个二进制前缀
            while (signalList.size() > 0) {
                // 取出二进制前缀
                currentSignal = signalList.get(0);
                // value 查找结果
                seekResult = TagOperations.seek(currentSignal, tagList);
                // 次数+1
                time++;
                // 从列表中删除
                StringBuilder finalCurrentSignal = currentSignal;
                signalList.removeIf(e -> e.toString().equals(finalCurrentSignal.toString()));
                // 根据返回值进行操作
                switch (seekResult) {
                    case 0:
                        // 无响应
                        break;
                    case 1:
                        // 识别成功
                        success++;
                        break;
                    case 2:
                        // 有冲突，增加前缀长度再次查询
                        signalList.add(0, new StringBuilder(currentSignal).append("0"));
                        signalList.add(1, new StringBuilder(currentSignal).append("1"));
                        break;
                    default:
                        // 未知错误
                        throw new RuntimeException("判断匹配结果时发生未知错误");
                }
                // System.out.println("    当前成功识别总数为：" + success);
            }

            // 识别结束
            System.out.println("识别次数为" + time + "次，标签总数为：" + success + "  识别完成！");
            times.add(time);
        }
        // 模拟结束，画图
        {
            Picture pic = new Picture();
            CategoryDataset categoryDataset = pic.createDataset(all, times);
            JFreeChart jFreeChart = pic.createChart(categoryDataset);
            pic.saveAsFile(jFreeChart, "out/jpg/line.jpg", 600, 400);
        }
    }
}