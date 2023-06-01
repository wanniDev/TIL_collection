package me.java.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 Commit Offset

 offset 값으로 이루어진 배열이 주어집니다. offset은 0 이상의 정수이며 중복은 없습니다.
 이 배열의 값들은 index-0부터 차례로 처리되며, 각 offset이 주어질때마다 우리는 해당 offset과 함께 현재까지 받은 offset들중 최대한 높은 offset을 commit하려고합니다.

 Commit을 하기 위해서는 0부터 해당 offset까지의 값들이 모두 있어야 합니다.
 즉, offset 2를 commit하기 위해서는 0, 1, 2를 모두 가지고있어야합니다.
 만약 해당 offset을 받은 시점에 추가로 commit 할 수 없으면 -1을 리턴합니다.

 Offset들로 이루어진 배열이 주어지고 차례로 처리했을 때, 각각의 시점에서 commit할 수 있는 가장 높은 offset(처리할 수 없다면 -1)을 담은 배열을 리턴하세요.

 예제 1:
 입력: [2, 0, 1]
 출력: [-1, 0, 2]
 설명:
 2: 2를 commit 하기 위해서 0과 1이 필요한데 아직 없으므로 -1을 리턴합니다
 0: 현재까지 [0, 2]의 offset을 받았으며 처리할 수 있는 가장 큰 offset은 0입니다
 1: 이제 1의 offset이 생겼으므로 현재까지 [0, 1, 2]의 offset을 받았으며, 이 때 처리할 수 있는 가장 높은 offset은 2입니다

 예제 2:
 입력: [0, 1, 2]
 출력: [0, 1, 2]

 예제 3:
 입력: [2, 1, 0, 5, 4]
 출력: [-1, -1, 2, -1, -1]

 예제 4:
 입력: [2, 1, 0, 5, 4, 3]
 출력: [-1, -1, 2, -1, -1, 5]

 예제 5:
 입력: [2, 1, 0, 5, 4, 3, 9, 7, 6, 8]
 출력: [-1, -1, 2, -1, -1, 5, -1, -1, 7, 9]

 예제 6:
 입력: [3, 0, 2, 4, 1, 7, 6, 5, 9]
 출력: [-1, 0, -1, -1, 4, -1, -1, 7, -1]

 구현할 method:
 public int[] commitOffsets(int[] offsets) {
 // implementation
 }

 */
public class CommitOffset {
    public int[] commitOffsets(int[] offsets) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] result = new int[offsets.length];

        for (int i = 0; i < offsets.length; i++) {
            int offset = offsets[i];
            map.put(offset, offset);
            int highest = offset, lowest = offset;

            if (map.containsKey(offset - 1))
                lowest = map.get(offset - 1);
            if (map.containsKey(offset + 1))
                highest = map.get(offset + 1);

            map.put(lowest, highest);
            map.put(highest, lowest);

            result[i] = lowest == 0 ? highest : -1;
        }
        return result;
    }

    public static void main(String[] args) {
        CommitOffset sol = new CommitOffset();
        System.out.println(Arrays.toString(sol.commitOffsets(new int[] {2, 0, 1})));
        System.out.println(Arrays.toString(sol.commitOffsets(new int[] {0, 1, 2})));
        System.out.println(Arrays.toString(sol.commitOffsets(new int[] {2, 1, 0, 5, 4})));
        System.out.println(Arrays.toString(sol.commitOffsets(new int[] {2, 1, 0, 5, 4, 3})));
        System.out.println(Arrays.toString(sol.commitOffsets(new int[] {2, 1, 0, 5, 4, 3, 9, 7, 6, 8})));
        System.out.println(Arrays.toString(sol.commitOffsets(new int[] {3, 0, 2, 4, 1, 7, 6, 5, 9})));
    }
}
