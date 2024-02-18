import java.util.HashMap;
import java.util.Map;

class Hw2 {
    public static void main(String[] args) {
        Map<Character, int[]> schema = new HashMap<>();
        schema.put('A', new int[] { -1, -1, -1, 1, 1, -1, 1, 1 });
        schema.put('B', new int[] { -1, -1, 1, -1, 1, 1, 1, -1 });
        schema.put('C', new int[] { -1, 1, -1, 1, 1, 1, -1, -1 });
        schema.put('D', new int[] { -1, 1, -1, -1, -1, -1, 1, -1 });

        int[][] signals = new int[6][schema.get('A').length];

        // Transmit C
        signals[0] = transmitBit(schema.get('C'), 1);
        signals[1] = combineStreams(transmitBit(schema.get('B'), 1), transmitBit(schema.get('C'), 1));
        // Transmit A + NOT B
        signals[2] = combineStreams(transmitBit(schema.get('A'), 1), transmitBit(schema.get('B'), 0));
        // Transmit A + NOT B + C
        signals[3] = combineStreams(transmitBit(schema.get('A'), 1), transmitBit(schema.get('B'), 0),
                transmitBit(schema.get('C'), 1));

        // Transmit A + B + C + D
        signals[4] = combineStreams(transmitBit(schema.get('A'), 1), transmitBit(schema.get('B'), 1),
                transmitBit(schema.get('C'), 1), transmitBit(schema.get('D'), 1));

        // Transmit A + B + NOT C + D
        signals[5] = combineStreams(transmitBit(schema.get('A'), 1), transmitBit(schema.get('B'), 1),
                transmitBit(schema.get('C'), 0), transmitBit(schema.get('D'), 1));

        // For each signal print what the letter gets
        for (int i = 0; i < signals.length; i++) {
            for (char letter : schema.keySet()) {
                int result = pullOutSignal(schema.get(letter), signals[i]);
                System.out.println("Signal " + i + " for " + letter + ": " + result);
            }
        }

    }

    // bit either 1 or 0
    public static int[] transmitBit(int[] schema, int bit) {
        if (bit != 1 && bit != 0) {
            throw new IllegalArgumentException("bit must be either 1 or -1");
        }

        int[] result = new int[schema.length];

        for (int i = 0; i < schema.length; i++) {
            if (bit == 0) {
                result[i] = schema[i] * -1;
            } else {
                result[i] = schema[i];
            }
        }

        return result;
    }

    public static int[] combineStreams(int[]... streams) {
        int length = streams[0].length;
        for (int i = 1; i < streams.length; i++) {
            if (streams[i].length != length) {
                throw new IllegalArgumentException("All streams must have the same length");
            }
        }

        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            int sum = 0;
            for (int j = 0; j < streams.length; j++) {
                sum += streams[j][i];
            }
            result[i] = sum;
        }

        return result;
    }

    public static int pullOutSignal(int[] schema, int[] signal) {
        int sum = 0;
        for (int i = 0; i < schema.length; i++) {
            sum += schema[i] * signal[i];
        }
        return sum / schema.length;
    }

}
