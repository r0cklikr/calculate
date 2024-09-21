package com.rock.ljjkr.utils;


import com.rock.ljjkr.exception.MatchException;
import com.rock.ljjkr.exception.MathException;

import java.io.IOException;

import java.util.*;

public class CalculateUtil {

    private static int ter = 0;
    private static List<Integer> finds = new ArrayList<>();
    private static List<String> stockmidexpress = new ArrayList<>();
    private static List<Pair<Character, Integer>> checkpile = new ArrayList<>();
    private static List<Pair<Character, Integer>> checkpilestack = new ArrayList<>();
    private static LinkedList<String> stocknum = new LinkedList<>();
    private static Stack<Character> operate = new Stack<>();
    private static Map<String, Double> data = new HashMap<>();
    private static String midexpress;
    public static String res="";


    private static boolean judge(char ch) {
        if (ch == '+' || ch == '-' || ch == '*' ||
                ch == '/' || ch == '^' || ch == '(' || ch == ')' ||
                ch == '[' || ch == ']' || ch == '{' || ch == '}')
            return true;
        else return false;
    }

    private static boolean judgenum(String ss) {
        if (ss.length() == 1 && !judge(ss.charAt(0))) return true;
        else if (ss.length() == 1 && judge(ss.charAt(0))) return false;
        else if (ss.length() != 1) {
            return true;
        }
        return true;
    }

    private static boolean judgeonlyoperator(char ch) {
        if (ch == '+' || ch == '-' || ch == '*' ||
                ch == '/' || ch == '^') return true;
        else return false;
    }

    private static boolean judgeplace() {
        for (int i = 0; i < midexpress.length(); i++) {
            if (judgeonlyoperator(midexpress.charAt(i))) {
                if (i - 1 < 0 || i + 1 == midexpress.length() || judgeonlyoperator(midexpress.charAt(i - 1)) || judgeonlyoperator(midexpress.charAt(i + 1)))
                    return true;
            }
        }
        return true;
    }

    private static void showmeun() {

        System.out.println("Welcome to use this calculator");
        System.out.println("*****************************************");
        System.out.println("1、根据中缀表达示计算值");
        System.out.println("2、显示历史计算记录");
        System.out.println("3、清空历史记录");
        System.out.println("4、退出表达式计算器");
        System.out.println("Tips:该计算器可以计算+-*/^,支持()[]{},支持小数，可检测括号是否匹配");
        System.out.println("*****************************************");
        System.out.println("选择你需要的功能：");


    }

    public static String calculate() {
        for (int i = 0; stocknum.size() != 1; ) {
            String abt = stocknum.get(i);
            if (abt.charAt(0) == '+') {
                double mmm = Double.parseDouble(stocknum.get(i - 1));
                double mmm1 = Double.parseDouble(stocknum.get(i - 2));
                String kkkk = Double.toString(mmm + mmm1);
                stocknum.add(i - 2, kkkk);
                stocknum.subList(i - 1, i + 2).clear();
                i -= 2;
            } else if (abt.charAt(0) == '-') {
                double mmm = Double.parseDouble(stocknum.get(i - 1));
                double mmm1 = Double.parseDouble(stocknum.get(i - 2));
                String kkkk = Double.toString(mmm1 - mmm);
                stocknum.add(i - 2, kkkk);
                stocknum.subList(i - 1, i + 2).clear();
                i -= 2;
            } else if (abt.charAt(0) == '*') {
                double mmm = Double.parseDouble(stocknum.get(i - 1));
                double mmm1 = Double.parseDouble(stocknum.get(i - 2));
                String kkkk = Double.toString(mmm1 * mmm);
                stocknum.add(i - 2, kkkk);
                stocknum.subList(i - 1, i + 2).clear();
                i -= 2;
            } else if (abt.charAt(0) == '/') {
                double mmm = Double.parseDouble(stocknum.get(i - 1));
                double mmm1 = Double.parseDouble(stocknum.get(i - 2));
                if (mmm == 0) {
                    ter = 1;

                    System.out.println("除数不能为0");
                    throw new MathException("除数不能为0");
                }
                String kkkk = Double.toString(mmm1 / mmm);
                stocknum.add(i - 2, kkkk);
                stocknum.subList(i - 1, i + 2).clear();
                i -= 2;
            } else if (abt.charAt(0) == '^') {
                double mmm = Double.parseDouble(stocknum.get(i - 1));
                double mmm1 = Double.parseDouble(stocknum.get(i - 2));
                String kkkk = Double.toString(Math.pow(mmm1, mmm));
                stocknum.add(i - 2, kkkk);
                stocknum.subList(i - 1, i + 2).clear();
                i -= 2;
            }
            i++;
        }
        double ans = Double.parseDouble(stocknum.get(0));

        if (ter == 0) {
            //data.put(midexpress, ans);
            //System.out.printf("%.5f\n", ans);
            return String.valueOf(ans);
        }
        return "";
    }

    private static String showmidexpress() {
        for (String temp : stockmidexpress) {
            if (judgenum(temp)) {
                stocknum.add(temp);
            } else if (temp.charAt(0) == ')') {
                while (operate.peek() != '(') {
                    stocknum.add(String.valueOf(operate.peek()));
                    operate.pop();
                }
                operate.pop();
            } else if (temp.charAt(0) == ']') {
                while (operate.peek() != '[') {
                    stocknum.add(String.valueOf(operate.peek()));
                    operate.pop();
                }
                operate.pop();
            } else if (temp.charAt(0) == '}') {
                while (operate.peek() != '{') {
                    stocknum.add(String.valueOf(operate.peek()));
                    operate.pop();
                }
                operate.pop();
            } else if (operate.empty() || (temp.charAt(0) == '*' || temp.charAt(0) == '/' || temp.charAt(0) == '^') && (operate.peek() == '+' || operate.peek() == '-') || temp.charAt(0) == '^' && (operate.peek() == '*' || operate.peek() == '/') || temp.charAt(0) == '(' || operate.peek() == '(' || temp.charAt(0) == '[' || operate.peek() == '[' || temp.charAt(0) == '{' || operate.peek() == '{') {
                operate.push(temp.charAt(0));
            } else {
                stocknum.add(String.valueOf(operate.peek()));
                if (temp.charAt(0) == '+' || temp.charAt(0) == '-') {
                    while (true) {
                        operate.pop();
                        if (operate.empty() || operate.peek() == '(' || operate.peek() == '[' || operate.peek() == '{')
                            break;
                        stocknum.add(String.valueOf(operate.peek()));
                    }
                } else if (temp.charAt(0) == '*' || temp.charAt(0) == '/') {
                    while (true) {
                        operate.pop();
                        if (operate.empty() || operate.peek() == '+' || operate.peek() == '-' || operate.peek() == '(' || operate.peek() == '[' || operate.peek() == '{')
                            break;
                        stocknum.add(String.valueOf(operate.peek()));
                    }
                } else {
                    while (true) {
                        operate.pop();
                        if (operate.empty() || operate.peek() == '*' || operate.peek() == '/' || operate.peek() == '(' || operate.peek() == '+' || operate.peek() == '-' || operate.peek() == '[' || operate.peek() == '{')
                            break;
                        stocknum.add(String.valueOf(operate.peek()));
                    }
                }
                operate.push(temp.charAt(0));
            }
        }
        while (!operate.empty()) {
            stocknum.add(String.valueOf(operate.peek()));
            operate.pop();
        }
        return calculate();
    }

    private static int check() {
        for (int i = 0; i < stockmidexpress.size(); i++) {
            char ch = stockmidexpress.get(i).charAt(0);
            if (ch == '[' || ch == ']' || ch == '(' || ch == ')' || ch == '{' || ch == '}') {
                checkpile.add(new Pair<>(ch, i));
            }
        }

        if (checkpile.isEmpty()) return 1;
        else {
            int p = 1;
            checkpilestack.add(checkpile.get(0));
            while (true) {
                if (checkpilestack.isEmpty() && p == checkpile.size()) {
                    return 1;
                }
                if (!checkpilestack.isEmpty() && p == checkpile.size()) {
                    System.out.println("输入的中缀表达式括号匹配存在问题!,错误处已用!标注");
                    res="输入的中缀表达式括号匹配存在问题!,错误处已用!标注\n";
                    for (String s : stockmidexpress) {
                        System.out.print(s);
                        res=res+s;
                    }
                    res=res+"\n";
                    System.out.println();
                    finds.clear();
                    for (Pair<Character, Integer> pair : checkpilestack) {
                        finds.add(pair.getValue());
                    }
                    Collections.sort(finds);
                    int j = 0;

                    for (int i = 0; i < stockmidexpress.size(); i++) {
                        if (j < finds.size() && finds.get(j) == i) {
                            System.out.print("!");
                            res=res+"!";
                            j++;
                        } else {
                            int size = stockmidexpress.get(i).length();
                            for (int k = 0; k < size; k++) {
                                System.out.print(" ");
                                res=res+" ";
                            }
                        }
                    }
                    throw new MatchException("匹配错误");
                    //System.out.println();
                   // return 0;
                }
                checkpilestack.add(checkpile.get(p));
                p++;
                if (checkpilestack.size() <= 1) continue;
                Pair<Character, Integer> temp = checkpilestack.get(checkpilestack.size() - 2);
                Pair<Character, Integer> temp1 = checkpilestack.get(checkpilestack.size() - 1);
                if ((temp.getKey() == '[' && temp1.getKey() == ']') ||
                        (temp.getKey() == '(' && temp1.getKey() == ')') ||
                        (temp.getKey() == '{' && temp1.getKey() == '}')) {
                    checkpilestack.remove(checkpilestack.size() - 1);
                    checkpilestack.remove(checkpilestack.size() - 1);
                }
            }
        }
    }

    private static void create() {

        for (int i = 0; i < midexpress.length(); ) {
            if(i==0&&i+1<=midexpress.length()-1&&midexpress.charAt(i)=='-'){
                midexpress='0'+midexpress;

                continue;
            }
            int count = 0;
            for (int p = 0; p < midexpress.length() - 1; p++) {
                if (midexpress.charAt(p) == '(' && midexpress.charAt(p + 1) == '-') {
                    midexpress = midexpress.substring(0, p + 1) + "0" + midexpress.substring(p + 1);
                }
            }

            if (judge(midexpress.charAt(i))) {
                String ch = String.valueOf(midexpress.charAt(i));
                stockmidexpress.add(ch);
                i++;
            } else {
                for (int j = i + 1; ; j++) {
                    if (j == midexpress.length()) break;
                    else if (judge(midexpress.charAt(j))) {
                        break;
                    } else if (!judge(midexpress.charAt(j))) {
                        count++;
                    }
                }
                String ss = midexpress.substring(i, i + count + 1);
                stockmidexpress.add(ss);
                i = i + count + 1;
            }
        }
    }

    public static String start(String ex)  {
        //BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        // Scanner scanner = new Scanner(System.in);

        //showmeun();
        String select = "1";

        if (select.length() == 1 && (select.charAt(0) == '1' || select.charAt(0) == '2' || select.charAt(0) == '3' || select.charAt(0) == '4')) {
            if (select.charAt(0) == '1') {
                //System.out.println("若要结束请输入：end");

                ter = 0;
                stockmidexpress.clear();
                checkpile.clear();
                checkpilestack.clear();
                stocknum.clear();
               // System.out.println("请输入中缀表达式：");
                midexpress = ex;

                if (judgeplace()) {

                    create();
                    if (check() == 1) {
                        return showmidexpress();
                    }
                } else {
                    System.out.println("输入有误");
                }

            }

        }
        
        return "";
    }


}
