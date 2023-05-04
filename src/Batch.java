import java.util.ArrayList;

public class Batch {
    public static void main(String[] args) {
        // 取仿真次数
        int count = 10000;
//        int count = new Scanner(System.in).nextInt();
        // 保存每次仿真的成功次数
        ArrayList<Integer> times = new ArrayList<>();
        // 保存每次测试的持续时间用于统计
        ArrayList<Long> durations = new ArrayList<>();
        // 保存每次仿真的标签数
        ArrayList<Integer> all = new ArrayList<>();
        // 查找结果
        int seekResult;
        // 对每一次仿真
        // countLeft 为剩余仿真次数
        for (int iterator = 1; iterator <= count; iterator++) {
            // 仿真的成功次数
            int time = 0;
            long startTime = System.currentTimeMillis();
            // 取标签总数
            // 保存标签总数，用于画图
            all.add(iterator);
            // 保存标签的列表
            ArrayList<String> tagList = TagOperations.createTags(iterator);
            // 保存二进制前缀的列表
            ArrayList<StringBuilder> signalList = new ArrayList<>();
            // 二进制前缀 当前发出信号
            StringBuilder currentSignal = new StringBuilder(TagOperations.capacity);
            // 初始化二进制前缀列表
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
                signalList.removeIf(e -> e.equals(finalCurrentSignal));
                // 根据返回值进行操作
                if (seekResult == 2) {// 有冲突，增加前缀长度再次查询
                    signalList.add(0, new StringBuilder(currentSignal).append("0"));
                    signalList.add(1, new StringBuilder(currentSignal).append("1"));
                }
            }
            // 识别结束
            // 保存耗时
            long endTime = System.currentTimeMillis();
            durations.add(endTime - startTime);
            // 保存成功次数
            times.add(time);
        }
        // 制表
        Table.saveAsCsvTable("out/csv/analysis.csv", all, times, durations);
        // 模拟结束，画图
//        Picture pic = new Picture();
//        CategoryDataset categoryDataset = pic.createDataset(all, times);
//        JFreeChart jFreeChart = pic.createChart(categoryDataset);
//        pic.saveAsFile(jFreeChart, "out/jpg/line.jpg", 600, 400);
    }
}
