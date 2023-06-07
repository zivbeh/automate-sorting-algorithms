package sortings;

import interfaces.sorter;

import java.util.Arrays;

public class zivSorter implements sorter {
    public static void main(String[] args) {
        int[] tester = new int[]{3,15,9,7,2,1,6,5};
        System.out.println(Arrays.toString(tester));
        zivSort(tester);
        System.out.println(Arrays.toString(tester));
    }

    private static void zivSort(int[] arr) {
        zivSort(arr,0,arr.length-1);
    }

    private static void zivSort(int[] arr, int start,int end) {
        int pivot = arr[(start+end)/2];//arr[(start+end)/2];
//        System.out.print("   " + start + "   " + end+"    "+pivot+"    ");
//        System.out.println(Arrays.toString(arr));
        if (start == end-1){return;}

        int boundary = end;
        for (int i = start; i < end; i++) {
            if (arr[i]<=pivot) {
                continue;
            } else {
                boundary = findNextItemToSwap(arr,i,end,pivot,i)-1;
            }
        }
        if (boundary == end) {return;}
        zivSort(arr, start, boundary);
        zivSort(arr, boundary,end);
    }

    public static int findNextItemToSwap(int[] arr, int start,int end,int pivot,int switchPos) {
        for (int i = start; i <= end; i++) {
            if(arr[i] <= pivot) {
                int temp = arr[switchPos];
                arr[switchPos] = arr[i];
                arr[i] = temp;
                if(i+1>end) {
                    return switchPos;
                }
                return findNextItemToSwap(arr,i+1,end,pivot,start+1);
            }
        }
        return start;
    }

    @Override
    public void sort(int[] arr) {
        zivSort(arr);
    }
}
