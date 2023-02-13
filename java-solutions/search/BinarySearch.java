package search;

public class BinarySearch {

    /*
        Pred:
         args != null
         args.length > 0


     */

    public static void main(String[] args) {
        int value = Integer.parseInt(args[0]);
        int[] array = new int[args.length - 1];
        for (int i = 1; i < args.length; ++i) array[i - 1] = Integer.parseInt(args[i]);
//        System.out.println(binarySearchIterative(array, value, 0, array.length));
        System.out.println(binarySearchRecursive(array, value, 0, array.length));
    }

    // Pred:
    //  forall i,j in [l..r-1] : i < j -> array[i] >= array[j] &&
    //  array != null &&
    //  l, r in [0..array.length] : l <= r

    // Post:
    //  R : forall i in [l..r-1] : (i < R -> array[i] > value) && (R <= i -> array[i] <= value)
    private static int binarySearchIterative(int[] array, int value, int l, int r) {

        // array[l'] >= value >= array[r'] &&
        // forall i in [l..r-1] : i < l' -> array[i] > value &&
        // forall j in [l..r-1] : j >= r' -> value >= array[j] &&
        // l' <= r'
        while (l < r) {
            // array[l'] >= value >= array[r'] &&
            // forall i in [l..r-1] : i < l' -> array[i] > value &&
            // forall j in [l..r-1] : j >= r' -> value >= array[j] &&
            // l' < r'
            if (value >= array[(l + r) / 2]) {
                // array[l'] >= value >= array[r'] && value >= array[(l'+r')/2] &&
                // forall i in [l..r-1] : i < l' -> array[i] > value &&
                // forall j in [l..r-1] : j >= (l'+r')/2 -> value >= array[j] &&
                // l' < r'
                r = (l + r) / 2;
                // array[l'] >= value >= array[r'] &&
                // forall i in [l..r-1] : i < l' -> array[i] > value &&
                // forall j in [l..r-1] : j >= r' -> value >= array[j] &&
                // l' <= r'
            } else {
                // array[l'] >= value >= array[r'] && array[(l'+r')/2] > value &&
                // forall i in [l..r-1] : i < (l'+r')/2 -> array[i] > value &&
                // forall j in [l..r-1] : j >= r' -> value >= array[j] &&
                // l' < r'
                l = (l + r) / 2 + 1;
                // array[l'] >= value >= array[r'] &&
                // forall i in [l..r-1] : i < l' -> array[i] > value &&
                // forall j in [l..r-1] : j >= r' -> value >= array[j] &&
                // l' <= r'
            }
            // array[l'] >= value >= array[r'] &&
            // forall i in [l..r-1] : i < l' -> array[i] > value &&
            // forall j in [l..r-1] : j >= r' -> value >= array[j] &&
            // l' <= r'

            // Branch post-condition:
            //  array[l'] >= value >= array[r'] &&
            //  forall i in [l..r-1] : i < l' -> array[i] > value &&
            //  forall j in [l..r-1] : j >= r' -> value >= array[j] &&
            //  l' <= r'

            //  array[l'] >= value >= array[r'] &&
            //  forall i in [l..r-1] : i < l' -> array[i] > value &&
            //  forall j in [l..r-1] : j >= r' -> value >= array[j]

        }
        //  I && l' == r' -> forall i in [l..r-1] : (i < l' -> array[i] > value) && (i >= l' -> value >= array[i])
        return l;
    }

    // Function post-condition:
    //  return value:
    //   x : forall i in [l..r-1] : (i < l' -> array[i] > value) && (i >= l' -> value >= array[i])

    // Function pre-condition:
    //  array != null &&
    //  value in Z && -2^31 <= value <= 2^31-1 &&
    //  l, r in [0..array.length] && l <= r &&
    //  forall i,j in [l..r-1] : i < j -> array[i] >= array[j]

    private static int binarySearchRecursive(int[] array, int value, int l, int r) {

        // l' == l
        // l' == r

        // Branch pre-condition:
        //  array[l'] >= value >= array[r'] &&
        //  forall i in [l..r-1] : i < l' -> array[i] > value &&
        //  forall j in [l..r-1] : j >= r' -> value >= array[j]

        if (l < r) {

            // Branch pre-condition:
            //  array[l'] >= value >= array[r'] &&
            //  forall i in [l..r-1] : i < l' -> array[i] > value &&
            //  forall j in [l..r-1] : j >= r' -> value >= array[j] &&
            //  l < r

            if (value >= array[(l + r) / 2]) {

                // Return pre-condition:
                //  array[l'] >= value >= array[r'] &&
                //  forall i in [l..r-1] : i < l' -> array[i] > value &&
                //  forall j in [l..r-1] : j >= r' -> value >= array[j] &&
                //  l < r &&
                //  value >= array[(l+r)/2]

                // note:
                //  forall i,j in [l..r-1] : i < j -> array[i] >= array[j] &&
                //  value >= array[(l+r)/2] ->
                //  (forall j in [l..r-1] : j >= (l+r)/2 -> value >= array[j]) &&

                return binarySearchRecursive(array, value, l, (l + r) / 2);
            } else {
                return binarySearchRecursive(array, value, (l + r) / 2 + 1, r);
            }
        } else {
            return l;
        }

        // Function post-condition:
        //  return value:
        //   x : forall i in [l..r-1] : (i < l' -> array[i] > value) && (i >= l' -> value >= array[i])

    }
}
