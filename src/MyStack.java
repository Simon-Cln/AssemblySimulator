import java.util.Stack;

public class MyStack {

    static Stack<Integer> myStack = new Stack<>();

    MyStack(){};

    public static void push(int integer){
        myStack.push(integer);
    }

    public static int pop(){
        return(myStack.pop());
    }

    public static void clear(){ myStack.clear(); }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("");
        str.append("Top\n");
        Object[] arr = myStack.toArray();
        for (int i=arr.length-1; i>=0; i--){
            str.append(arr[i]+"\n");
        }
        str.append("Bottom\n");
        return str.toString();
    }

}