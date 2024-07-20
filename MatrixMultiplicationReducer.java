import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MatrixMultiplicationReducer extends Reducer<Text, Text, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Map<Integer, Integer> aMap = new HashMap<>();
        Map<Integer, Integer> bMap = new HashMap<>();

        for (Text val : values) {
            String[] tokens = val.toString().split(",");
            int index = Integer.parseInt(tokens[1]);
            int value = Integer.parseInt(tokens[2]);

            if (tokens[0].equals("A")) {
                aMap.put(index, value);
            } else if (tokens[0].equals("B")) {
                bMap.put(index, value);
            }
        }

        int result = 0;
        for (Map.Entry<Integer, Integer> entry : aMap.entrySet()) {
            int index = entry.getKey();
            int aValue = entry.getValue();
            int bValue = bMap.containsKey(index) ? bMap.get(index) : 0;
            result += aValue * bValue;
        }

        context.write(key, new IntWritable(result));
    }
}
