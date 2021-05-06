import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class doTaskTest {

    @ParameterizedTest
    @MethodSource("dataForDoSubArr")
    public void testArrayParam(int[] arr, int[] result) {
        Assertions.assertArrayEquals(result, doTask.doSubArr(arr));
    }

    public static Stream<Arguments> dataForDoSubArr() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{4, 1}, new int[]{1}));
        out.add(Arguments.arguments(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}));
        out.add(Arguments.arguments(new int[]{1, 2, 4, 4, 2, 3, 1, 7}, new int[]{2, 3, 1, 7}));
        return out.stream();
    }

    @Test
    public void shouldRuntimeException() {
        Assertions.assertThrows(RuntimeException.class, () -> doTask.doSubArr(new int[]{1, 2, 3}));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void shouldIllegalArgumentException(int[] emptyArr) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> doTask.doSubArr(emptyArr));
    }

    //-----------------------для 2 задачи----------------------------------

    @ParameterizedTest
    @MethodSource("dataForCheckNumber")
    public void testBoolArrayParam(int[] arr, boolean result) {
        Assertions.assertEquals(doTask.doCheckNumber(arr), result);
    }

    public static Stream<Arguments> dataForCheckNumber() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[] {1, 1, 4, 4}, true));
        out.add(Arguments.arguments(new int[] {1, 1, 4, 4, 5}, false));
        out.add(Arguments.arguments(new int[] {4, 4}, false));
        out.add(Arguments.arguments(new int[] {1, 1}, false));
        return out.stream();
    }

}
