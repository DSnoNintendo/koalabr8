package koalabr8.game;

import java.util.Stack;

public class MoveStack {
    private static Stack<Move> stack = new Stack<>();
    private static Stack<Move> temp = new Stack<>();

    public static Move pop() { return stack.pop(); }

    public static Move peek() { return stack.peek(); }

    public static void push(Move m) { stack.push(m); }

    public static void pushToTemp(Move m) { temp.push(m); }

    public static int size() { return stack.size(); }

    public static boolean isEmpty() { return stack.isEmpty(); }

    public static void update() {
        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
    }

}
