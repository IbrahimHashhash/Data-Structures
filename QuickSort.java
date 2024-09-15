public class QuickSort {
    public static void sort(Comparable[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            System.out.println("Pivot: " + arr[pi]);
            System.out.println("Partition: ");
            print(arr, low, high);
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }

    private static int partition(Comparable[] arr, int low, int high) {
        Comparable pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) < 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(Comparable[] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void print(Comparable[] arr, int low, int high) {
        for (int i = low; i <= high; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
