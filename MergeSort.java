public class MergeSort {
    public static void sort(Comparable [] data, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            System.out.println("Dividing: " + low + " " + mid + " " + high);
            sort(data, low, mid);
            sort(data, mid + 1, high);
            merge(data, low, mid, high);
        }
    }

    public static void merge(Comparable [] data, int low, int mid, int high) {
        int n = high - low + 1;
        Comparable[] temp = new Comparable[n];
        int i = low, j = mid + 1, k = 0;
        while ((i <= mid) && (j <= high)) {
            if (data[j].compareTo(data[i]) <= 0)
                temp[k++] = data[j++];
            else
                temp[k++] = data[i++];
        }
        if (i <= mid)
            for (; i <= mid;)
                temp[k++] = data[i++];
        else
            for (; j <= high;)
                temp[k++] = data[j++];
        for (k = 0; k < n; k++)
            data[low + k] = temp[k];

        System.out.println("Merging: " + low + " " + mid + " " + high);
        printArray(data, low, high);
    }

    private static void printArray(Comparable[] arr, int low, int high) {
        for (int i = low; i <= high; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
