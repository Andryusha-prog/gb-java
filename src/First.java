import java.util.ArrayList;
import java.util.Arrays;

public class First {
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4};
        Integer[] newArr = change(array, 1, 3);
        System.out.println(Arrays.toString(newArr));

        Double[] arrD = {1.2, 2.3, 3.4};
        ArrayList<Double> list = new ArrayList<>(transform(arrD));
        System.out.println(list);
    }

    public static <T> T[] change(T[] arr, int index1, int index2) {//Первое задание
        T temp;
        temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
        return arr;
    }

    public static <E> ArrayList<E> transform(E[] array) {//Второе задание
        return new ArrayList<>(Arrays.asList(array));
        /*
        for(int i = 0; i < arr.length; i++)
            list.add(arr[i]);
         */
    }
}
