package sirgl.analysis;

import sirgl.analysis.rules.ReplacementRule;
import sirgl.lexer.LangTokenStream;
import sirgl.nodes.Node;
import sirgl.nodes.Root;
import sirgl.parser.LangParser;
import sirgl.parser.ParsingException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionSimplifier {
    private Map<Class<?>, List<ReplacementRule<Node>>> ruleMap = new HashMap<>();

    public ExpressionSimplifier(List<ReplacementRule> rules) {
        for (ReplacementRule rule : rules) {
            Class applicableClass = rule.getApplicableClass();
            List<ReplacementRule<Node>> classRules = ruleMap.getOrDefault(applicableClass, new ArrayList<>());
            //noinspection unchecked
            classRules.add(rule);
            ruleMap.put(applicableClass, classRules);
        }
    }

    public SimplificationResult simplify(String str) {
        try {
            LangTokenStream tokenStream = new LangTokenStream(new StringReader(str));
            Node node = new LangParser(tokenStream).parse();
            Root root = new Root(node);
            TreeWalker walker = new TreeWalker(this::acceptNode);
            walker.walk(node);
            Node rootValue = root.getValue();
            rootValue.setParent(null);
            return new SimplificationResult(rootValue);
        } catch (IOException | ParsingException e) {
            e.printStackTrace(); // TODO
        }
        return null;
    }

    private Node acceptNode(Node node) {
        Class<? extends Node> nodeClass = node.getClass();
        List<ReplacementRule<Node>> rules = ruleMap.get(nodeClass);
        if (rules == null) {
            return null;
        }
        for (ReplacementRule<Node> rule : rules) {
            Node replacement = rule.tryReplace(node);
            if (replacement != null) {
                return onSuccessfulRuleAppliance(node, replacement);
            }
        }
        return null;
    }

    private Node onSuccessfulRuleAppliance(Node node, Node replacement) {
        node.replaceTo(replacement);
        return replacement;
    }
}
