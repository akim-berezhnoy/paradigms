package expression.parser;

import java.util.HashMap;
import java.util.Set;

public class PrefixTree {
    public static class Node {
        final private HashMap<Character, Node> outbounds = new HashMap<>();
        private boolean terminal = false;
    }
    final private Node root = new Node();

    final private Set<String> words;

    final private int longestWord;

    public PrefixTree(final Set<String> words) {
        this.words = words;
        int longestWord = 0;
        for (String word : this.words) {
            add(word);
            longestWord = Math.max(word.length(), longestWord);
        }
        this.longestWord = longestWord;
    }

    public boolean hasPrefix(String prefix) {
        Node currentNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            currentNode = currentNode.outbounds.getOrDefault(prefix.charAt(i), null);
            if (currentNode == null) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(String prefix) {
        Node currentNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            currentNode = currentNode.outbounds.getOrDefault(prefix.charAt(i), null);
            if (currentNode == null) {
                return false;
            }
        }
        return currentNode.terminal;
    }

    public void add(String codeWord) {
        Node currentNode = root;
        for (int i = 0; i < codeWord.length(); i++) {
            char ch = codeWord.charAt(i);
            currentNode.outbounds.putIfAbsent(ch, new Node());
            currentNode = currentNode.outbounds.get(ch);
        }
        currentNode.terminal = true;
    }

    public int getLongestWord() {
        return longestWord;
    }

    @Override
    public String toString() {
        StringBuilder visual = new StringBuilder("{");
        for (String word : words) {
            visual.append('"').append(word).append('"').append(',').append(' ');
        }
        if (visual.length() != 1) {
            visual.delete(visual.length()-2, visual.length()).append('}');
        }
        return visual.toString();
    }
}
