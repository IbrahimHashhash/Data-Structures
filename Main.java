public class Main {
    public static void main(String[] args) {
        Integer[] array = {5,8,7,4,2};

        System.out.println("Merge Sort: Original Array:");
        print(array);

        MergeSort.sort(array, 0, array.length - 1);
        System.out.println("----------------------------");

        Integer[] arrays = {5,8,7,11,12,4,2};
        System.out.println("Quick Sort: Original Array:");
        print(arrays);

        QuickSort.sort(arrays, 0, arrays.length - 1);

    }

    public static void print(Comparable[] array) {
        for (Comparable element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
