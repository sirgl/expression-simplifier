package sirgl.analysis;

import sirgl.analysis.interceptors.ChainInvalidatingInterceptor;
import sirgl.analysis.interceptors.ReplacementInterceptor;
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
    private Map<Class<?>, List<ReplacementRule<Node>>> ruleMapPreEnter = new HashMap<>();
    private Map<Class<?>, List<ReplacementRule<Node>>> ruleMapPostEnter = new HashMap<>();
    private List<ReplacementInterceptor> interceptors = new ArrayList<>();

    public ExpressionSimplifier(List<ReplacementRule> preEnterRules, List<ReplacementRule> postEnterRules, List<ReplacementInterceptor> interceptors) {
        this.interceptors.add(new ChainInvalidatingInterceptor());
        this.interceptors.addAll(interceptors);
        rulesToMap(preEnterRules, ruleMapPreEnter);
        rulesToMap(postEnterRules, ruleMapPostEnter);
    }

    public void addInterceptor(ReplacementInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public ExpressionSimplifier(List<ReplacementRule> preEnterRules, List<ReplacementRule> postEnterRules) {
        this(preEnterRules, postEnterRules, new ArrayList<>());
    }

    private void rulesToMap(List<ReplacementRule> rules, Map<Class<?>, List<ReplacementRule<Node>>> ruleMap) {
        for (ReplacementRule rule : rules) {
            Class applicableClass = rule.getApplicableClass();
            List<ReplacementRule<Node>> classRules = this.ruleMapPreEnter.getOrDefault(applicableClass, new ArrayList<>());
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
            TreeWalker walker = new TreeWalker(this::acceptNodeBeforeEnter, this::acceptNodeAfterEnter);
            walker.walk(root);
            Node rootValue = root.getValue();
            rootValue.setParent(null);
            return new SimplificationResult(rootValue);
        } catch (IOException | ParsingException e) {
            e.printStackTrace(); // TODO
        }
        return null;
    }

    private Node acceptNodeBeforeEnter(Node node) {
        return acceptNode(node, ruleMapPreEnter);
    }

    private Node acceptNodeAfterEnter(Node node) {
        return acceptNode(node, ruleMapPostEnter);
    }

    private Node acceptNode(Node node, Map<Class<?>, List<ReplacementRule<Node>>> ruleMap) {
        Class<? extends Node> nodeClass = node.getClass();
        List<ReplacementRule<Node>> rules = ruleMap.get(nodeClass);
        if (rules == null) {
            return null;
        }
        for (ReplacementRule<Node> rule : rules) {
            Node replacement = rule.tryReplace(node);
            if (replacement != null) {
                return onSuccessfulRuleAppliance(node, replacement, rule);
            }
        }
        return null;
    }

    private Node onSuccessfulRuleAppliance(Node node, Node replacement, ReplacementRule<Node> rule) {
        for (ReplacementInterceptor interceptor : interceptors) {
            interceptor.intercept(node, replacement, rule);
        }
        node.replaceTo(replacement);
        return replacement;
    }
}
