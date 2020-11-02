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
    public MyBigInteger Times(MyBigInteger x) {
        MyBigInteger result = new MyBigInteger();
        MyBigInteger resultTemp = new MyBigInteger();
        signCheck = false;
        int prod, val, valX;
        int factorCarry = 0;
        int addition = 0;

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
        padZeros(x);
        for (int i = x.Value.length() - 1; i >= 0; i--){
            valX = ConvertToInt(x.Value.charAt(i));
            for(int j = this.Value.length() -1; j >= 0; j--){
                val = ConvertToInt(this.Value.charAt(j));
                prod = val * valX + factorCarry;
                if(prod > 9){
                    factorCarry = prod / 10;
                    prod %= 10;
                }else{
                    factorCarry = 0;
                }
                char temp = ConvertToChar(prod);
                String insertStr = String.valueOf(temp);
                if (j == x.Value.length() -1){
                    resultTemp.Value = insertStr;
                }else{
                    resultTemp.Value += insertStr;
                }
            }
            for (int k = 0; k < addition; k++){
                resultTemp.Value += "0";
            }
            result = result.Plus(resultTemp);
            System.out.print(Integer.parseInt(resultTemp.Value()));
            resultTemp.Value = "0";
            addition++;
        }

        removeZeros(result);
        return result;
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

