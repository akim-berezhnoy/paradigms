package search;

public class BinarySearch {

    // Pred:
    //    args != null &&
    //    args.length > 0 &&
    //    forall el in args : el is parsable integer value &&
    //    forall i,j in [1..args.length-1]: i < j -> int(array[i]) >= int(array[j])
    // Post:
    //    System.out << R: forall i in [0..args.length-2]:
    //                     (i < R -> int(arg[i+1]) > value) && (R <= i -> int(args[i+1]) <= value),
    //                     R in [0..array.length-1]
    public static void main(String[] args) {
        int value = Integer.parseInt(args[0]);
        int[] array = new int[args.length - 1];

        int sum = 0;

        for (int i = 1; i < args.length; ++i) {
            array[i - 1] = Integer.parseInt(args[i]);
            sum ^= array[i - 1]&1;
        }

        int ans;
        if (sum == 1) {
            ans = binarySearchIterative(array, value, 0, array.length);
        } else {
            ans = binarySearchRecursive(array, value, 0, array.length);
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
    private static int binarySearchIterative(int[] array, int value, int l, int r) {
        // Inv:
        //   forall i,j in [l..r-1]: i < j -> array[i] >= array[j] &&
        //   l, r in [0..array.length] &&
        //   l' <= r' &&
        //   forall i in [l..r-1] : i < l' -> array[i] > value &&
        //   forall j in [l..r-1] : j >= r' -> value >= array[j]
        while (l < r) {
            // Inv && l' < r'
            if (value >= array[(l + r) / 2]) {
                // Inv && l' < r' && value >= array[(l'+r')/2]
                r = (l + r) / 2;
                // Inv && l' <= r'
            } else {
                // Inv && l' < r' && array[(l'+r')/2] > value &&
                l = (l + r) / 2 + 1;
                // Inv && l' <= r'
            }
            // Inv && l' <= r'
        }
        // Inv && l' >= r' ->
        // Inv && l' == r' ->
        // forall i in [l..r-1] : (i < l' -> array[i] > value) && (i >= l' -> value >= array[i])
        return l;
    }

    // Pred:
    //    array != null &&
    //    forall i,j in [l..r-1]: i < j -> array[i] >= array[j] &&
    //    l, r in [0..array.length] &&
    //    l <= r
    // Post:
    //    R: forall i in [l..r-1]:
    //       (i < R -> array[i] > value) && (R <= i -> array[i] <= value)
    private static int binarySearchRecursive(int[] array, int value, int l, int r) {
        // C:
        //   forall i,j in [l..r-1]: i < j -> array[i] >= array[j] &&
        //   l, r in [0..array.length] &&
        //   l' <= r' &&
        //   forall i in [l..r-1] : i < l' -> array[i] > value &&
        //   forall j in [l..r-1] : j >= r' -> value >= array[j]
        if (l < r) {
            // C && l' < r'
            if (value >= array[(l + r) / 2]) {
                // C && l' < r' && value >= array[(l'+r')/2]
                return binarySearchRecursive(array, value, l, (l + r) / 2);
            } else {
                // C && l' < r' && array[(l'+r')/2] > value
                return binarySearchRecursive(array, value, (l + r) / 2 + 1, r);
            }
        } else {
            // C && l' < r' -> C && l' == r' -> forall i in [l..r-1] : (i < l' -> array[i] > value) && (i >= l' -> value >= array[i])
            return l;
        }
    }
}
