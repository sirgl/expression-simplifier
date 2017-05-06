package sirgl.console;

import sirgl.analysis.AnalysisError;
import sirgl.analysis.ErrorHighlight;
import sirgl.analysis.ExpressionSimplifier;
import sirgl.analysis.SimplificationResult;
import sirgl.analysis.interceptors.ReplacementInterceptor;
import sirgl.analysis.interceptors.StatisticsInterceptor;
import sirgl.analysis.rules.ReplacementRule;
import sirgl.nodes.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                if (line.trim().equals("quit")) {
                    return;
                }
                if (line.charAt(0) == '#') {
                    handleCommand(line.substring(1));
                    continue;
                }
                simplifyExpression(line);
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

    private void simplifyExpression(String expr) {
        SimplificationResult result = simplifier.simplify(expr);
        if(result.hasErrors()) {
            for (AnalysisError error : result.getErrors()) {
                System.out.println("Parse error: " + error.getMessage());
                System.out.println(expr);
                printHighlights(error.getHighlights());
            }
            return;
        }
        System.out.println(result.getResult());
    }

    private void printHighlights(List<ErrorHighlight> highlightList) {
        List<ErrorHighlight> sorted = highlightList.stream()
                .sorted(Comparator.comparingInt(ErrorHighlight::getStart))
                .collect(Collectors.toList());
        int current = 0;
        for (int i = 0; ; i++) {
            ErrorHighlight currentHighlight = sorted.get(current);
            if(currentHighlight.inRange(i)) {
                System.out.print("^");
            } else if (currentHighlight.getEnd() < i) {
                current++;
                if(sorted.size() == current) {
                    System.out.println();
                    return;
                }
            }
        }
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
