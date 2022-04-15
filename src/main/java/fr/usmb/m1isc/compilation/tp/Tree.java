package fr.usmb.m1isc.compilation.tp;

import java.util.ArrayList;

public class Tree {
    private String value;
    private Tree[] leaves;
    private NodeEnum type;

    public Tree(NodeEnum nodetype, String value, Tree left, Tree right) {
        if(left != null || right != null){
            this.leaves = new Tree[2];
            leaves[0] = left;
            leaves[1] = right;
        }
        this.value = value;
        this.type = nodetype;
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

    public String generateDataSegment(){
        String res = "";
        if(this.type == NodeEnum.LET){
            res += "\t"+this.leaves[0].value+" DD\n";
        }
        if(leaves != null) {
            if (leaves[0] != null) res += leaves[0].generateDataSegment();
            if (leaves[1] != null) res += leaves[1].generateDataSegment();
        }
        return res;
    }

    public String generateNodeCode(){
        String res = "";
        switch(type){
            case INTEGER: //DONE
                res += "\tmov eax " + value + "\n";
                break;
            case IDENTIFIER: //DONE
                res += "\tmov eax " + value + "\n";
                break;
            case PLUS: //DONE
                res += leaves[1].generateNodeCode();
                res += "\tpush eax\n";
                res += leaves[0].generateNodeCode();
                res += "\tpop ebx\n";
                res += "\tadd eax, ebx\n";
                break;
            case MINUS: //DONE
                res += leaves[1].generateNodeCode();
                res += "\tpush eax\n";
                res += leaves[0].generateNodeCode();
                res += "\tpop ebx\n";
                res += "\tsub eax, ebx\n";
                break;
            case MULTI: //DONE
                res += leaves[0].generateNodeCode();
                res += "\tpush eax\n";
                res += leaves[1].generateNodeCode();
                res += "\tpop ebx\n";
                res += "\tmul eax, ebx\n";
                break;
            case DIVIDE: //DONE
                res += leaves[0].generateNodeCode();
                res += "\tpush eax\n";
                res += leaves[1].generateNodeCode();
                res += "\tpop ebx\n";
                res += "\tdiv ebx, eax\n";
                res += "\tmov eax, ebx\n";
                break;
            case MOD:
                //TODO
                break;
            case EQUAL: //Not used in LET affectation
                //TODO
                break;
            case INF:
                //TODO
                break;
            case SUP:
                //TODO
                break;
            case INF_EQ:
                //TODO
                break;
            case SUP_EQ:
                //TODO
                break;
            case LET:
                res += leaves[1].generateNodeCode();
                res += "\tmov "+leaves[0].value+", eax\n";
                break;
            case IF:
                //TODO
                break;
            case THEN:
                //TODO
                break;
            case ELSE:
                //TODO
                break;
            case WHILE:
                //TODO
                break;
            case DO:
                //TODO
                break;
            case AND:
                //TODO
                break;
            case OR:
                //TODO
                break;
            case NOT:
                //TODO
                break;
            case INPUT:
                //TODO
                break;
            case OUTPUT:
                //TODO
                break;
            case SEMI: //DONE
                res += leaves[0].generateNodeCode();
                res += leaves[1].generateNodeCode();
                break;


        }
        return res;
    }
}
