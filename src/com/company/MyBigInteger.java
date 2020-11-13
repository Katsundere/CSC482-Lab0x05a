package com.company;

import java.util.Arrays;

public class MyBigInteger {

    public String Value;
    public Boolean signCheck;

    public MyBigInteger() {
        Value = "0";
        signCheck = false;
    }
    public MyBigInteger(String strVal) {
        Value = strVal;
        if (strVal.charAt(0) == '-') {
            signCheck = true;
            Value = Value.substring(1);
        } else {
            signCheck = false;
        }
    }
    public void SetValue(String str) {
        if (str.charAt(0) == '-') {
            signCheck = true;
            Value = str.substring(1);
        } else {
            signCheck = false;
            Value = str;
        }
    }
    public String Value() //see about trying hexadecimal
    {
        if (signCheck) {
            return '-' + Value;
        } else {
            return Value;
        }
    }
    public String AbbreviatedValue() {
        if (Value.length() <= 12) {
            return Value;
        } else {
            return Value.substring(0, 5) + "..." + Value.substring(Value.length() - 5);
        }
    }
    public MyBigInteger Plus(MyBigInteger x) {
        MyBigInteger result = new MyBigInteger();
        signCheck = false;
        int carry = 0;
        int sum;
        int character,characterX;

        if (this.Value.charAt(0) == '-' && x.Value.charAt(0) == '-') {
            signCheck = true;
            this.Value = x.Value.substring(1);
            x.Value = x.Value.substring(1);
        } else if (this.Value.charAt(0) == '-') {
            this.Value = this.Value.substring(1);
            return x.Minus(this);
        } else if (x.Value.charAt(0) == '-') {
            x.Value = x.Value.substring(1);
            return this.Minus(x);
        }
        padZeros(x);

        for (int i = this.Value.length() - 1; i >= 0; i--) {
            character = ConvertToChar(this.Value.charAt(i));
            characterX = ConvertToInt(x.Value.charAt(i));
            sum = character + characterX + carry;
            carry = 0;

            if(sum> 9){
                carry = 1;
                sum -= 10;
            }

            char temp = ConvertToChar(sum);
            String insertStr = String.valueOf(temp);
            if(i == this.Value.length()-1){
                result.Value = insertStr;
            }else{
                result.Value = insertStr + result.Value();
            }
        }
        if(carry == 1){
            char temp = ConvertToChar(1);
            String insertStr = String.valueOf(temp);
            result.Value = insertStr + result.Value();
        }
        if(signCheck){
            result.Value = "-" + result.Value();
        }
        return result;
    }
    public MyBigInteger Minus(MyBigInteger x) {
        MyBigInteger result = new MyBigInteger();
        int val, valX;
        int diff;

        if (x.Value.charAt(0) == '-') {
            x.Value = x.Value.substring(1);
            return this.Plus(x);
        } else if (this.Value.charAt(0) == '-') {
            this.Value = this.Value.substring(1);
            result = this.Plus(x);
            result.Value = "-" + result.Value();
            return result;
        }
        if(x.Value.length() > this.Value.length()){
            result =x.Minus(this);
            result.Value = "-" + result.Value;
            return result;
        }
        else if (x.Value.length() == this.Value.length()){
            boolean pass = false;
            int i = 0;
            while(!pass && i < this.Value.length()){
                val = ConvertToInt(this.Value.charAt(i));
                valX = ConvertToInt(x.Value.charAt(i));
                if(valX > val){
                    result = x.Minus(this);
                    result.Value = "-" + result.Value;
                    return result;
                }
                else if(val > valX){
                    pass = true;
                }
                i++;
            }
            if(!pass){
                return result;
            }
        }
        padZeros(x);
        for(int i = this.Value.length() - 1; i >= 0; i--){
            val = ConvertToChar(this.Value.charAt(i));
            valX = ConvertToChar(x.Value.charAt(i));
            if(valX <= val){
                diff = val - valX;
                char temp = ConvertToChar(diff);
                String insertStr = String.valueOf(temp);
                if(i == this.Value.length()-1){
                    result.Value = insertStr;
                }else{
                    result.Value += insertStr;
                }
            }else{
                int borrow = i -1;
                while(ConvertToInt(this.Value.charAt(borrow)) == 0){
                    char [] temp = this.Value.toCharArray();
                    temp[borrow] = '9';
                    this.Value = String.valueOf(borrow);
                    borrow--;
                }
                char[] temp = this.Value.toCharArray();
                temp[borrow] = ConvertToChar(ConvertToInt(this.Value.charAt(borrow))-1);
                this.Value =String.valueOf(temp);
                val = val + 10;
                diff = val - valX;
                char tempChar = ConvertToChar(diff);
                String insertStr = String.valueOf(tempChar);
                if(i == this.Value.length() -1){
                    result.Value = insertStr;
                }else{
                    result.Value += insertStr;
                }
            }
        }
        return result;
    }
    public MyBigInteger Times(MyBigInteger x) { // referenced geeksforgeeks.org/multiply-large-numbers-represented-as-strings/
        int len1 = x.Value.length();
        int len2 = this.Value.length();
        int index1 = 0;
        int index2 = 0;
        int why = 0;
        int[] result = new int[len1+len2];
        MyBigInteger resultZero = new MyBigInteger("0");
        signCheck = false;
        int prod, val, valX;
        int factorCarry = 0;

        if(this.Value.charAt(0) == '-' && x.Value.charAt(0) != '-'){
            signCheck = true;
            this.Value = this.Value.substring(1);
        }
        else if (this.Value.charAt(0) != '-' && x.Value.charAt(0) == '-'){
            signCheck = true;
            x.Value = x.Value.substring(1);
        }else if (this.Value.charAt(0) == '-' && x.Value.charAt(0) == '-'){
            this.Value = this.Value.substring(1);
            x.Value = x.Value.substring(1);
        }
        //padZeros(x);
        for (int i = len1 - 1; i >= 0; i--){
            factorCarry = 0;
            valX = ConvertToChar(x.Value.charAt(i))- '0';
            index2 = 0;
            for(int j = len2 -1; j >= 0; j--){
                val = ConvertToChar(this.Value.charAt(j)) -'0';
                prod = val * valX + result[index1 + index2] + factorCarry;
                factorCarry = prod / 10;
                result[index1 + index2] = prod % 10;
                index2++;

            }
            if(factorCarry > 0){
                result[index1 + index2] += factorCarry;
            }
            index1++;
        }

        int i = result.length -1;
        while(i >= 0 && result[i] == 0) {
            i--;
        }
        if (i == -1){
            return resultZero;
        }
        String s = "";
        while(i >= 0){
            s += result[i--];
        }
        System.out.print(s);
        MyBigInteger noMoreZeros = new MyBigInteger(s);
        noMoreZeros = removeZeros(noMoreZeros);
        return noMoreZeros;
    }

    public  String padZeros(MyBigInteger x) {
        int len = this.Value.length();
        int lenX = x.Value.length();
        int lenDiff;
        if (len > lenX) {
            lenDiff = len - lenX;
            char[] padding = new char[lenDiff];
            Arrays.fill(padding, '0');
            String fill = new String(padding);
            return x.Value = fill + x.Value;
        } else{
            lenDiff = lenX - len;
            char[] padding = new char[lenDiff];
            Arrays.fill(padding, '0');
            String fill = new String(padding);
            return this.Value = fill + this.Value;

        }
    }
    public MyBigInteger removeZeros(MyBigInteger result){
        int i = 0;
        while(result.Value.charAt(i) == '0' && result.Value.length() > 1){
            result.Value = result.Value.substring(1);
        }
        if(signCheck){
            result.Value = "-" + result.Value();
        }
        return result;
    }
    public char ConvertToChar(int i){
        return (char) (i + 48);
    }
    public int ConvertToInt(char i){
        return i - 48;
    }
}

