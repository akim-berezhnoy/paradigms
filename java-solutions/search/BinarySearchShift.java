package search;

public class BinarySearchShift {

    // Pred:
    //    args != null &&
    //    args.length > 0 &&
    //    forall el in args : el is parsable integer value &&
    //    exists! x:
    //      forall i,j in [0..x-1]: i < j -> int(array[i]) >= int(array[j]) &&
    //      forall i,j in [x..array.length-1]: i < j -> int(array[i]) >= int(array[j]) &&
    //      forall i in [0..x-1], j in [x..array.length-1]: array[i] <= array[j]
    // Post:
    //    R:
    //     forall i,j in [0..R-1]: i < j -> int(array[i]) >= int(array[j]) &&
    //     forall i,j in [R..array.length-1]: i < j -> int(array[i]) >= int(array[j]) &&
    //     forall i in [0..R-1], j in [R..array.length-1]: array[i] <= array[j]
    public static void main(String[] args) {
        int[] array = new int[args.length];

        int sum = 0;

        for (int i = 0; i < args.length; ++i) {
            array[i] = Integer.parseInt(args[i]);
            sum ^= array[i]&1;
        }

        int ans;
        if (sum == 1) {
            ans = binarySearchIterative(array);
        } else {
            ans = binarySearchRecursive(array, 0, array.length-1, array[array.length-1]);
        }
        System.out.println(ans);
    }

    // Pred:
    //    array != null &&
    //    forall i,j in [l..r-1]: i < j -> array[i] >= array[j] &&
    //    l, r in [0..array.length] &&
    //    l <= r
    // Post:
    //    R: forall i in [l..r-1]:
    //       (i < R -> array[i] > value) && (R <= i -> array[i] <= value)
    public static int binarySearchIterative(int[] arr) {
        int l = 0;
        int r = arr.length-1;
        int last = arr[r];
        while (r > l) {
            int m = (l+r)/2;
            if (arr[m] < last) {
                l = m+1;
            } else {
                r = m;
            }
        }
        return l;
    }

    public static int binarySearchRecursive(int[] arr, int l, int r, int last) {
        if (r > l) {
            int m = (l+r)/2;
            if (arr[m] < last) {
                return binarySearchRecursive(arr, m+1, r, last);
            } else {
                return binarySearchRecursive(arr, l, m, last);
            }
        }
        return l;
    }

}
