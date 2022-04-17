package fr.usmb.m1isc.compilation.tp;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Tree {
    private String value;
    private Tree[] leaves;
    private NodeEnum type;

    private int cpt;

    public Tree(NodeEnum nodetype, String value, Tree left, Tree right) {
        if(left != null || right != null){
            this.leaves = new Tree[2];
            leaves[0] = left;
            leaves[1] = right;
        }
        this.value = value;
        this.type = nodetype;
        this.cpt = 0;
    }

    public Tree(NodeEnum nodetype, String value, Tree left) {
        this(nodetype, value, left, null);
    }

    public Tree(NodeEnum nodetype, String value) {
        this(nodetype, value, null, null);
    }


    @Override
    public String toString() {
        if (leaves == null) {
            return String.format("%s", value);
        } else {
            String res = "(" + value;
            for (int i = 0; i < leaves.length; i++) {
                if(leaves[i] != null) {
                    res += " " + leaves[i].toString();
                }
            }
            res += ")";
            return res;
        }
    }

    public String getCode(){
        String res = "";
        res += "DATA SEGMENT\n";
        res += generateDataSegment();
        res += "DATA ENDS\n";
        res += "CODE SEGMENT\n";
        res += generateNodeCode();
        res += "CODE ENDS\n";
        return res;
    }

    public Set<String> getAllVars(){
        Set<String> res = new LinkedHashSet<>();
        if(this.type == NodeEnum.LET){
            res.add(this.leaves[0].value);
        }
        if(leaves != null) {
            if (leaves[0] != null) res.addAll(leaves[0].getAllVars());
            if (leaves[1] != null) res.addAll(leaves[1].getAllVars());
        }
        return res;
    }

    public String generateDataSegment(){
        String res = "";
        for(String var : getAllVars()){
            res += "\t" + var + " DD\n";
        }
        return res;
    }

    public String generateNodeCode(){
        String res = "";
        int cpt_tmp = ++cpt;
        switch(type){
            case INTEGER:
                res += "\tmov eax, " + value + "\n";
                break;
            case IDENTIFIER:
                res += "\tmov eax, " + value + "\n";
                break;
            case PLUS:
                res += leaves[1].generateNodeCode();
                res += "\tpush eax\n";
                res += leaves[0].generateNodeCode();
                res += "\tpop ebx\n";
                res += "\tadd eax, ebx\n";
                break;
            case MINUS:
                res += leaves[1].generateNodeCode();
                res += "\tpush eax\n";
                res += leaves[0].generateNodeCode();
                res += "\tpop ebx\n";
                res += "\tsub eax, ebx\n";
                break;
            case MULTI:
                res += leaves[0].generateNodeCode();
                res += "\tpush eax\n";
                res += leaves[1].generateNodeCode();
                res += "\tpop ebx\n";
                res += "\tmul eax, ebx\n";
                break;
            case DIVIDE:
                res += leaves[0].generateNodeCode();
                res += "\tpush eax\n";
                res += leaves[1].generateNodeCode();
                res += "\tpop ebx\n";
                res += "\tdiv ebx, eax\n";
                res += "\tmov eax, ebx\n";
                break;
            case MOD:
                res += leaves[1].generateNodeCode();
                res += "\tpush eax\n";
                res += leaves[0].generateNodeCode();
                res += "\tpop ebx\n";
                res += "\tmov ecx, eax\n";
                res += "\tdiv ecx, ebx\n";
                res += "\tmul ecx, ebx\n";
                res += "\tsub eax, ecx\n";
                break;
            case INF:
                res += leaves[0].generateNodeCode();
                res += "\tpush eax\n";
                res += leaves[1].generateNodeCode();
                res += "\tpop ebx\n";
                res += "\tsub eax, ebx\n";
                res += "\tjle faux_inf_" + cpt_tmp + "\n";
                res += "\tmov eax, 1\n";
                res += "\tjmp sortie_inf_" + cpt_tmp + "\n";
                res += "faux_inf_" + cpt_tmp + ":\n";
                res += "\tmov eax, 0\n";
                res += "sortie_inf_" + cpt_tmp + ":\n";
                break;
            case SUP:
                System.err.println("> is not implemented");
            case INF_EQ:
                System.err.println("<= is not implemented");
                break;
            case SUP_EQ:
                System.err.println(">= is not implemented");
                break;
            case LET:
                res += leaves[1].generateNodeCode();
                res += "\tmov "+leaves[0].value+", eax\n";
                break;
            case IF:
                this.cpt = cpt_tmp + 1;
                res += leaves[0].generateNodeCode();
                res += "\tjz else_"+cpt_tmp+"\n";
                res += leaves[1].leaves[0].generateNodeCode();
                res += "\tjmp fin_if_"+cpt_tmp+"\n";
                res += "else_"+cpt_tmp+":\n";
                res += leaves[1].leaves[1].generateNodeCode();
                res += "fin_if_"+cpt_tmp+":\n";
                break;
            case WHILE:
                this.cpt = cpt_tmp + 1;
                res+= "debut_while_"+cpt_tmp+":\n";
                res += leaves[0].generateNodeCode();
                res += "\tjz sortie_while_"+cpt_tmp+"\n";
                res += leaves[1].generateNodeCode();
                res += "\tjmp debut_while_"+cpt_tmp+"\n";
                res += "sortie_while_"+cpt_tmp+":\n";
                break;
            case AND:
                System.err.println("AND is not implemented");
                break;
            case OR:
                System.err.println("OR is not implemented");
                break;
            case NOT:
                System.err.println("NOT is not implemented");
                break;
            case INPUT:
                res += "\tin eax\n";
                break;
            case OUTPUT:
                res += leaves[0].generateNodeCode();
                res += "\tout eax\n";
                break;
            case SEMI:
                res += leaves[0].generateNodeCode();
                res += leaves[1].generateNodeCode();
                break;
            default:
                res += leaves[0].generateNodeCode();
        }
        return res;
    }
}
