public class testClass1 {

    @BeforeSuite
    public static void test1() {
        System.out.println("test1 with @BeforeSuite started!");
    }

    @Test(value = 4)
    public static void test2() {
        System.out.println("test2 started!");
    }

    @Test(value = 10)
    public static void test3() {
        System.out.println("test3 started!");
    }

    @Test
    public static void test5() {
        System.out.println("test5 started!");
    }

    @AfterSuite
    public static void test4() {
        System.out.println("test4 with @AfterSuite started!");
    }
}
