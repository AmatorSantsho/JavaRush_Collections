package com.javarush.task.task34.task3404;

/* 
Рекурсия для мат. выражения
*/
public class Solution {
    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        solution.recursion("(10*1)", 0); //expected output 0.5 6
    }

    public void recursion(final String expression, int countOperation) throws Exception {



        for (char x:expression.toCharArray()){
            if (x=='+'||x=='-'||x=='*'||x=='^')
                countOperation++;
        }
        for (char x:expression.toCharArray()){
            if (x=='i')
                countOperation++;
        }
        for (char x:expression.toCharArray()){
            if (x=='c')
                countOperation++;
        }
        for (char x:expression.toCharArray()){
            if (x=='t')
                countOperation++;
        }
        //System.out.print(String.format("%.2f", calculus(expression))+" "+countOperation);
        System.out.print(Math.rint(calculus(expression)*100.0)/100.0+" "+countOperation);

        //implement
    }
    // Calculates the quantity of Symbol in current String pattern
// Возвращает сколько заданных символов находится в данной строке
    private static int countSymbol(char symbol, String pattern) {
        int countS = 0;
        for (int i = 0; i < pattern.length(); ++i)
            if (pattern.charAt(i) == symbol) countS++;
        return countS;
    }

    // If the number of open and close bracets are equal
    // Возвращает (истина/ложь) соответствует ли количество открывающих скобок количеству закрывающих
    private static boolean ifBracketsEqual(String pattern) {
        return (countSymbol('(', pattern) == countSymbol(')', pattern));
    }

    // Index of first or last found character in the string
    // if fromBegin is true returns index of first character equals symbol, else of the last and -1 if no character found.
    // Индекс первого с начала или с конца строки заданного символа fromBegin = true - с начала иначе с конце - возвращает
    // -1 - если такого символа нет
    private static int indexOfCharacter(char symbol, String patternString, boolean fromBegin) {
        int index = -1;
        int flag = 0;
        char[] pattern = patternString.toCharArray();
        for (int i = 0; i < pattern.length; ++i) {
            if (pattern[i] == symbol && flag == 0)
                if (fromBegin) return i;
                else index = i;
            if (pattern[i] == '(') ++flag;
            if (pattern[i] == ')') --flag;
        }
        return index;
    }

    // Index of arythmetical operator
    // Индекс арифметического оператора
    private static int indexOfOperator(String pattern) {
        int indOp;
        if ((indOp = indexOfCharacter('+', pattern, false)) != -1) return indOp;
        if ((indOp = indexOfCharacter('-', pattern, false)) != -1) return indOp == 0 ? -1 : indOp;
        if ((indOp = indexOfCharacter('*', pattern, false)) != -1) return indOp;
        if ((indOp = indexOfCharacter('^', pattern, false)) != -1) return indOp;
       // if ((indOp = indexOfCharacter('%', pattern, false)) != -1) return indOp;
        return -1;
    }

    // if we have open and close bracets on the whole expression like (2+3) remove bracets (2+3)-(4-5) - keep
    // Если выражание целиком закрыто скобками - то убираем скобки
    private static String removeBrackets(String patternString) {
        int indOp = indexOfOperator(patternString);
        if (patternString.length() > 1 && indOp == -1)
            if (patternString.charAt(0) == '(' && patternString.charAt(patternString.length() - 1) == ')')
                patternString = removeBrackets(patternString.substring(1, patternString.length() - 1));
        return patternString;
    }

    // Remove some string from given string
    // Убираем название функции из строки sin(строка) => строка
    private static String cutPattern(String pattern, int length) {
        return pattern.substring(length, pattern.length() - 1);
    }

    // The main part - find the value
    // Вычисляем значанеи выражения
    private static double mathOperator(String pattern) throws Exception {
        double sum = 0;
        String[] parts;
        String operat;


        try {
            parts = pattern.split("\\(");
            operat = parts[0];

            switch (switchOperator(operat)) {

                case 2:
                    pattern = cutPattern(pattern, 4);
                    sum += Math.sin(Math.PI * calculus(pattern) / 180);
                    break;
                case 3:
                    pattern = cutPattern(pattern, 4);
                    sum += Math.cos(Math.PI * calculus(pattern) / 180);
                    break;
                case 4:
                    pattern = cutPattern(pattern, 4);
                    sum += Math.tan(Math.PI * calculus(pattern) / 180);
                    break;

                default:
                    //Toast.makeText(context,  pattern, Toast.LENGTH_SHORT).show();
                    sum += Double.parseDouble(pattern);
                    break;

            }
            return sum;
        } catch (Exception e) {
            throw new Exception("Check pattern: " + pattern);
        }

    }

    // For switch enumarates functions
    // В зависимости от названия функции получаем ее индекс для switch
    private static int switchOperator(String pattern) {

        //if (pattern.equalsIgnoreCase("e")) return 1;
        if (pattern.equalsIgnoreCase("sin")) return 2;
        if (pattern.equalsIgnoreCase("cos")) return 3;
        if (pattern.equalsIgnoreCase("tan")) return 4;


        return 0;
    }

    // Рекурсивная функция и точка входа
    static double calculus(String pattern) throws Exception {

        pattern = pattern.replace(" ", "");
        if (pattern.charAt(0) == '-') {
            return (calculus("0" + pattern));
        }
        pattern = removeBrackets(pattern);
        if (!ifBracketsEqual(pattern)) throw new Exception("Braces unequal");
        int indOp = indexOfOperator(pattern);
        if (indOp == -1) {
            if (pattern.toCharArray()[0] == '-') return (-calculus(pattern.substring(1)));
            else
                return (mathOperator(pattern));
        }

        switch (pattern.toCharArray()[indOp]) {
            case '*':
                return calculus(pattern.substring(0, indOp)) * calculus(pattern.substring(indOp + 1));
            //break;
            case '+':
                return calculus(pattern.substring(0, indOp)) + calculus(pattern.substring(indOp + 1));
            //break;
            case '^':
                return calculus(pattern.substring(0, indOp)) / calculus(pattern.substring(indOp + 1));
            // break;
            case '-':
                return calculus(pattern.substring(0, indOp)) - calculus(pattern.substring(indOp + 1));
            //break;

            default:
                return 0;
        }
    }

    public Solution() {
        //don't delete
    }
}
