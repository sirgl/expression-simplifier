package sirgl.console;

import sirgl.analysis.ExpressionSimplifier;
import sirgl.nodes.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInterface implements Runnable {
    private final ExpressionSimplifier simplifier;

    public ConsoleInterface(ExpressionSimplifier simplifier) {
        this.simplifier = simplifier;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.print("> ");
                String line = reader.readLine();
                if(line.length() == 0) {
                    continue;
                }
                if(line.trim().equals("exit")) {
                    return;
                }
                if(line.charAt(0) == '#') {
                    handleCommand(line.substring(1));
                    continue;
                }
                System.out.println(simplifyExpression(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCommand(String line) {
        switch (line) {
            case "help":
                return;
            case "debug on":
                return;
            case "stats":
                return;
        }
    }

    private Node simplifyExpression(String expr) {
        return simplifier.simplify(expr).getResult();
    }
}
