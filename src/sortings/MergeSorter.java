package sortings;

import interfaces.sorter;

public class MergeSorter implements sorter {
    public static void mergeSort(int[] arr) {
        mergeSort(arr,0,arr.length-1);
    }

    private static void mergeSort(int[] arr, int start, int end) {
        if (start>=end) {return;}
        int mid = (start+end)/2;
        mergeSort(arr, start, mid); // left
        mergeSort(arr, mid+1, end);// right
        merge(arr,start,mid,mid+1,end);
    }

    public static void merge(int[] arr, int s1, int e1, int s2, int e2) {
        int[] temp = new int[arr.length];
        int left = s1;
        int right = s2;
        int next = s1;

        while(left <= e1 && right <= e2) {
            if (arr[left] < arr[right]) {
                temp[next] = arr[left];
                left++;
            } else {
                temp[next] = arr[right];
                right++;
            }
            next++;
        }

        if (left <= e1) {
            for (int i = left; i <= e1; i++) {
                temp[next] = arr[i];
                next++;
            }
        }

        if (right <= e2) {
            for (int i = right; i <= e2; i++) {
                temp[next] = arr[i];
                next++;
            }
        }

        for (int i = s1; i <= e2; i++) {
            arr[i] = temp[i];
        }
    }

    @java.lang.Override
    public void sort(int[] arr) {
        mergeSort(arr);
    }

}
