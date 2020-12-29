public class Method {
    static final int size = 10000000;
    static final int half = size / 2;

    public void firstMethod() {
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++)
            arr[i] = 1;
        long a = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++)
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        System.out.println("Время работы первого метода: " + (System.currentTimeMillis() - a));
        System.out.println();
    }

    public void secondMethod() {
        float[] arr = new float[size];
        float[] arr1 = new float[half];
        float[] arr2 = new float[size - half];
        for (int i = 0; i < arr.length; i++)
            arr[i] = 1;

        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, half);
        System.arraycopy(arr, half, arr2, 0, size - half);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr1.length; i++)
                    arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                System.arraycopy(arr1, 0, arr1, 0, arr1.length);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr2.length; i++)
                    arr2[i] = (float) (arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                System.arraycopy(arr2, 0, arr2, 0, arr2.length);
            }
        });

        t1.start();
        t2.start();

        System.arraycopy(arr1, 0, arr, 0, half);
        System.arraycopy(arr2, 0, arr, half, half);
        System.out.println("Время работы второго метода: " + (System.currentTimeMillis() - a));
    }
}
