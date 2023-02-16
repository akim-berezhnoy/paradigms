package search;

public class BinarySearchMax {

    // Pred:
    //    args != null &&
    //    args.length > 0 &&
    //    forall el in args : el is parsable integer value &&
    //    exists! x:
    //      forall i,j in [0..x]: i < j -> int(args[i]) < int(args[j]) &&
    //      forall i,j in [x+1..args.length-1]: i < j -> int(args[i]) < int(args[j]) &&
    //      forall i in [0..x], j in [x+1..args.length-1]: args[i] > args[j]
    // Post:
    //    args[R]:
    //      forall i,j in [0..x]: i < j -> int(args[i]) < int(args[j]) &&
    //      forall i,j in [x+1..args.length-1]: i < j -> int(args[i]) < int(args[j]) &&
    //      forall i in [0..x], j in [x+1..args.length-1]: args[i] > args[j]
    public static void main(String[] args) {
        // StartInf
        int[] array = new int[args.length];
        // StartInf && array'.length = args.length
        int sum = 0;
        // StartInf && array'.length = args.length && sum % 2 = 0
        int i = 0;
        // StartInf && array'.length = args.length && sum % 2 = 0 && i = 0

        // Inv: forall j in [0, i) array[array.length-1-i..array.length-1] = int(args[0..i]) && sum%2 = sum(args[0..i])%2
        while (i < args.length) {
            // StartInf && Inv && i < args.length
            array[args.length-i-1] = Integer.parseInt(args[i]);
            // StartInf && Inv (для i+1) &&
            // forall j in [0, i+1) array[array.length-1-2-i..array.length-1] = int(args[0..i+1])
            sum ^= array[i]&1;
            // StartInf && Inv (для i+1) &&
            // forall j in [0, i+1) array[array.length-1-2-i..array.length-1] = int(args[0..i+1])
            // sum для i+1

            // StartInf && Inv (всё для i+1) && sum для i+1
            i = i + 1;
            // StartInf && Inv (всё для i) && sum для i+1
        }
        // P:
        // args != null &&
        // args.length > 0 &&
        // forall el in args : el is parsable integer value &&
        // exists! x:
        //   forall i,j in [0..x]: i < j -> int(args[i]) > int(args[j]) &&
        //   forall i,j in [x+1..args.length-1]: i < j -> int(args[i]) > int(args[j]) &&
        //   forall i in [0..x], j in [x+1..args.length-1]: args[i] < args[j]
        int ans;
        if (sum == 1) {
            // P && sum % 2 == 1
            ans = binarySearchIterative(array);
        } else {
            // P && sum % 2 == 0
            ans = binarySearchRecursive(array, 0, array.length-1, array[array.length-1]);
        }
        // ans = args[R]:
        //      forall i,j in [0..x]: i < j -> int(args[i]) > int(args[j]) &&
        //      forall i,j in [x+1..args.length-1]: i < j -> int(args[i]) > int(args[j]) &&
        //      forall i in [0..x], j in [x+1..args.length-1]: args[i] < args[j]
        System.out.println(ans);
    }

    // Pred:
    //    args != null &&
    //    args.length > 0 &&
    //    forall el in args : el is parsable integer value &&
    //    exists! x:
    //      forall i,j in [0..x]: i < j -> int(args[i]) > int(args[j]) &&
    //      forall i,j in [x+1..args.length-1]: i < j -> int(args[i]) > int(args[j]) &&
    //      forall i in [0..x], j in [x+1..args.length-1]: args[i] < args[j]
    // Post:
    //    args[R]:
    //      forall i,j in [0..x]: i < j -> int(args[i]) > int(args[j]) &&
    //      forall i,j in [x+1..args.length-1]: i < j -> int(args[i]) > int(args[j]) &&
    //      forall i in [0..x], j in [x+1..args.length-1]: args[i] < args[j]
    public static int binarySearchIterative(int[] arr) {
        // Let StartInf:
        //    args != null &&
        //    args.length > 0 &&
        //    forall el in args : el is parsable integer value &&
        //    exists! x:
        //      forall i,j in [0..x]: i < j -> int(args[i]) > int(args[j]) &&
        //      forall i,j in [x+1..args.length-1]: i < j -> int(args[i]) > int(args[j]) &&
        //      forall i in [0..x], j in [x+1..args.length-1]: args[i] < args[j]
        int l = 0;
        // StartInf && l' = 0
        int r = arr.length-1;
        // StartInf && l' = 0 && r' = arr.length-1
        int last = arr[r];
        // StartInf &&
        // l' = 0 &&
        // r' = arr.length-1 &&
        // last == arr[arr.length-1]

        // Inv:
        //   l' <= x <= r'
        //   last' = array[array.length-1]
        while (l < r) {
            // Inv && StartInf &&
            // l' < r'
            if (arr[(l+r)/2] < last) {
                // Inv && StartInf &&
                // l' < r' && arr[(l'+r')/2] < last -> (l'+r')/2 < x -> (l'+r')/2+1 <= x
                l = (l+r)/2+1;
                // Inv && l' < r' && l' <= x -> l' > l(old)
            } else {
                // Inv && StartInf &&
                // l' < r' && arr[(l'+r')/2] >= last -> x <= (l'+r')/2
                r = (l+r)/2;
                // Inv && l' < r' && x <= r' -> r' < r(old)
            }
            // Inv
        }
        // StartInf && Inv && l >= r -> l = r = x
        return arr[l];
    }

    // Pred:
    //    args != null &&
    //    args.length > 0 &&
    //    forall el in args : el is parsable integer value &&
    //    exists! x:
    //      forall i,j in [0..x]: i < j -> int(args[i]) > int(args[j]) &&
    //      forall i,j in [x+1..args.length-1]: i < j -> int(args[i]) > int(args[j]) &&
    //      forall i in [0..x], j in [x+1..args.length-1]: args[i] < args[j] &&
    //      l <= x <= r
    //      last = array[array.length-1]
    // Post:
    //    args[R]:
    //      forall i,j in [0..x]: i < j -> int(args[i]) > int(args[j]) &&
    //      forall i,j in [x+1..args.length-1]: i < j -> int(args[i]) > int(args[j]) &&
    //      forall i in [0..x], j in [x+1..args.length-1]: args[i] < args[j]
    public static int binarySearchRecursive(int[] arr, int l, int r, int last) {
        // Let StartInf:
        //    args != null &&
        //    args.length > 0 &&
        //    forall el in args : el is parsable integer value &&
        //    exists! x:
        //      forall i,j in [0..x]: i < j -> int(args[i]) > int(args[j]) &&
        //      forall i,j in [x+1..args.length-1]: i < j -> int(args[i]) > int(args[j]) &&
        //      forall i in [0..x], j in [x+1..args.length-1]: args[i] < args[j]
        // Inv:
        //   l' <= x <= r'
        if (l < r) {
            // Inv && StartInf && l' < r'
            if (arr[(l+r)/2] < last) {
                // Inv && StartInf &&
                // l' < r' &&
                // arr[(l'+r')/2] < array[array.length-1] -> (l'+r')/2 < x -> (l'+r')/2+1 <= x
                return binarySearchRecursive(arr, (l+r)/2+1, r, last);
            } else {
                // Inv && StartInf &&
                // l' < r' &&
                // arr[(l'+r')/2] >= array[array.length-1] -> x <= (l'+r')/2
                return binarySearchRecursive(arr, l, (l+r)/2, last);
            }
        }
        // StartInf && Inv && l' >= r' -> l' = r' = x
        return arr[l];
    }

}
