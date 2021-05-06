import java.util.Arrays;

public class doTask {

    private doTask() {

    }

    public static int[] doSubArr(int[] arr) {

        int[] newArr;
        int index = -1;
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Массив не может быть пустым");
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == 4) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            newArr = new int[arr.length - index - 1];
            System.arraycopy(arr, index + 1, newArr, 0, arr.length - index - 1);
        } else {
            throw new RuntimeException("Массив не содержит элемента равного 4");
        }

        return newArr;
    }

    public static boolean doCheckNumber(int[] arr) {

        return Arrays.stream(arr).anyMatch(number -> number == 1)
                && Arrays.stream(arr).anyMatch(number -> number == 4)
                && (Arrays.stream(arr).noneMatch(number -> (number != 1 && number != 4)));
    }


}
