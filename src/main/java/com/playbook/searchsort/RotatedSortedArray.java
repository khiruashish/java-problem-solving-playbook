package com.playbook.searchsort;

/** A sorted array of DISTINCT values rotated at an unknown pivot: [4,5,6,7,0,1,2]. Search it and find its minimum in O(log n). */
public final class RotatedSortedArray {
    private RotatedSortedArray() {}

    /**
     * One binary search: at least one half of [lo, hi] is always sorted. Check whether target lies in the
     * sorted half; if yes go there, otherwise go to the other half.
     */
    public static int search(int[] a, int target) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] == target) return mid;
            if (a[lo] <= a[mid]) {                           // left half [lo, mid] is sorted
                if (a[lo] <= target && target < a[mid]) hi = mid - 1;
                else lo = mid + 1;
            } else {                                         // right half [mid, hi] is sorted
                if (a[mid] < target && target <= a[hi]) lo = mid + 1;
                else hi = mid - 1;
            }
        }
        return -1;
    }

    /** Index of the minimum (= rotation count). Compare mid with the RIGHT end. */
    public static int minIndex(int[] a) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] > a[hi]) lo = mid + 1;                // min is strictly right of mid
            else hi = mid;                                   // mid could be the min
        }
        return lo;
    }

    /** Alternative: find the pivot, then a normal binary search in the correct sorted part. */
    public static int searchViaPivot(int[] a, int target) {
        if (a.length == 0) return -1;
        int pivot = minIndex(a), n = a.length;
        int lo = 0, hi = n - 1;
        while (lo <= hi) {                                   // search in "virtual" sorted index space
            int mid = (lo + hi) >>> 1, real = (mid + pivot) % n;
            if (a[real] == target) return real;
            if (a[real] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }
}
