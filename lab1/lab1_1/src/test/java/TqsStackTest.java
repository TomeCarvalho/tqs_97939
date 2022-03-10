import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TqsStackTest {
    private TqsStack<Integer> stack;

    @BeforeEach
    void init() {
        stack = new TqsStack<>();
    }

    @DisplayName("A stack is empty on construction.")
    @Test
    void testEmptyOnConstruction() {
        assertTrue(stack.isEmpty());
    }

    @DisplayName("A stack has size 0 on construction.")
    @Test
    void testSize0OnConstruction() {
        assertEquals(0, stack.size());
    }

    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n.")
    @Test
    void testNPushes() {
        int n = 3;
        for (int i = 0; i < n; i++)
            stack.push(i);
        assertFalse(stack.isEmpty());
        assertEquals(n, stack.size());
    }

    @DisplayName("If one pushes x then pops, the value popped is x.")
    @Test
    void testPushThenPop() {
        int x = 0;
        stack.push(x);
        assertEquals(x, stack.pop());
    }

    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same.")
    @Test
    void testPushThenPeek() {
        int x = 0;
        stack.push(x);
        assertEquals(x, stack.peek());
        int prevSize = stack.size();
        assertEquals(prevSize, stack.size());
    }

    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0.")
    @Test
    void testNPops() {
        int n = 3;
        for (int i = 0; i < n; i++)
            stack.push(i);  // Pushing is already tested in testNPushes
        for (int i = 0; i < n; i++)
            stack.pop();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @DisplayName("Popping from an empty stack does throw a NoSuchElementException.")
    @Test
    void testPopEmpty() {
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException.")
    @Test
    void testPeekEmpty() {
        assertThrows(NoSuchElementException.class, stack::peek);
    }

    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException.")
    @Test
    void testFullStackPush() {
        stack = new TqsStack<>(1);
        stack.push(0);
        assertThrows(IllegalStateException.class, () -> stack.push(1));
    }
}
