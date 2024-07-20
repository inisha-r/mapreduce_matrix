import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MatrixMultiplicationMapper extends Mapper<Object, Text, Text, Text> {
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] tokens = value.toString().split(",");
        if (tokens.length == 4) {
            String matrixName = tokens[0];
            int i = Integer.parseInt(tokens[1]);
            int j = Integer.parseInt(tokens[2]);
            int val = Integer.parseInt(tokens[3]);

            if (matrixName.equals("A")) {
                for (int k = 0; k < 2; k++) {  // Assuming 2 is the dimension of matrix B columns
                    context.write(new Text(i + "," + k), new Text("A," + j + "," + val));
                }
            } else if (matrixName.equals("B")) {
                for (int i = 0; i < 2; i++) {  // Assuming 2 is the dimension of matrix A rows
                    context.write(new Text(i + "," + j), new Text("B," + j + "," + val));
                }
            }
        }
    }
}
