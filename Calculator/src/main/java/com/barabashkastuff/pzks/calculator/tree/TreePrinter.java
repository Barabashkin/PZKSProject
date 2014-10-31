package com.barabashkastuff.pzks.calculator.tree;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TreePrinter Class
 *
 * @author barabashka
 * @version 31-Oct-14
 */
@Component
@Scope("prototype")
public class TreePrinter {

    private TreePrinter() {
    }

    public String printNode(BinaryNode root) {
        int maxLevel = maxLevel(root);
        return printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private String printNodeInternal(List<BinaryNode> nodes, int level, int maxLevel) {
        StringBuffer stringBuffer = new StringBuffer();
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return "";

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        stringBuffer.append(printWhitespaces(firstSpaces));

        List<BinaryNode> newNodes = new ArrayList<BinaryNode>();
        for (BinaryNode node : nodes) {
            if (node != null) {
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                stringBuffer.append(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        stringBuffer.append(System.getProperty("line.separator"));

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeft() != null)
                    stringBuffer.append("/");
                else
                    printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (nodes.get(j).getRight() != null)
                    stringBuffer.append("\\");
                else
                    printWhitespaces(1);

                printWhitespaces(endgeLines + endgeLines - i);
            }

            stringBuffer.append(System.getProperty("line.separator"));
        }

        stringBuffer.append(printNodeInternal(newNodes, level + 1, maxLevel));
        return stringBuffer.toString();
    }

    private String printWhitespaces(int count) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < count; i++)
            stringBuffer.append(" ");
        return stringBuffer.toString();
    }

    private int maxLevel(BinaryNode node) {
        if (node == null)
            return 0;

        return Math.max(maxLevel(node.getLeft()), maxLevel(node.getRight())) + 1;
    }

    private boolean isAllElementsNull(List list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}