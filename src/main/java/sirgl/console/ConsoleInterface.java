package sirgl.console;

import sirgl.analysis.ExpressionSimplifier;
import sirgl.analysis.interceptors.ReplacementInterceptor;
import sirgl.analysis.interceptors.StatisticsInterceptor;
import sirgl.analysis.rules.ReplacementRule;
import sirgl.nodes.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class ConsoleInterface implements Runnable {
    private final ExpressionSimplifier simplifier;
    private final StatisticsInterceptor statisticsInterceptor;
    private boolean logging = false;


    public ConsoleInterface(ExpressionSimplifier simplifier) {
        this.simplifier = simplifier;
        simplifier.addInterceptor((from, to, rule) -> {
            if(logging) {
                System.out.println("Replacement (" + rule.getName() + "):");
                System.out.println("From: " + from);
                System.out.println("To:   " + to);
                System.out.println();
            }
        });
        statisticsInterceptor = new StatisticsInterceptor();
        simplifier.addInterceptor(statisticsInterceptor);
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.print("> ");
                String line = reader.readLine();
                if (line.length() == 0) {
                    continue;
                }
                if (line.trim().equals("exit")) {
                    return;
                }
                if (line.charAt(0) == '#') {
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
            case "debug on":
                logging = true;
                return;
            case "debug off":
                logging = false;
                return;
            case "stats":
                printStatistics();
                return;
            default:
                printHelp();
        }
    }

    private Node simplifyExpression(String expr) {
        return simplifier.simplify(expr).getResult();
    }

    private void printStatistics() {
        System.out.println("Statistics:");
        Map<ReplacementRule<?>, Integer> stats = statisticsInterceptor.getRuleStats();
        for (Map.Entry<ReplacementRule<?>, Integer> entry : stats.entrySet()) {
            System.out.println(String.format("%-30s %d", entry.getKey().getName(), entry.getValue()));
        }
    }

    private void printHelp() {

    }
}
