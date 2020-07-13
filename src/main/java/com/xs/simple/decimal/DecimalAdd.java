package com.xs.simple.decimal;

/**
 * 高精度加法
 * 目前仅仅支持非负数。
 *
 * @author xiongshun
 * create time: 2020/7/13 16:01
 */
public class DecimalAdd {

    public String add(String a, String b) {
        NumHolder holder = new NumHolder(a, b);
        String little = doAdd(holder.aLittle, holder.bLittle, -1);
        int geWeiAdd = 0;
        if (little.startsWith("!")) {
            little = little.substring(1);
            geWeiAdd = 1;
        }
        String up = doAdd(holder.aUp, holder.bUp, geWeiAdd);
        return mix(up, little);
    }

    private String doAdd(String a, String b, final int geWeiAdd) {
        StringBuilder result = new StringBuilder();
        int maxLength = Math.max(a.length(), b.length());
        int aCursor = a.length()-1;
        int bCursor = b.length()-1;
        int addWei = geWeiAdd > 0 ? geWeiAdd : 0;
        for (int i=0; i<maxLength;i++) {
            int aValue = aCursor < 0 ? 0 : a.charAt(aCursor) - 48;
            int bValue = bCursor < 0 ? 0 : b.charAt(bCursor) - 48;
            aCursor--;bCursor--;
            int value = aValue + bValue + addWei;
            addWei = value / 10;
            result.append(value % 10);
        }
        if (addWei > 0) {
            result.append(addWei);
        }
        result.reverse();
        if (geWeiAdd < 0 && result.length() > maxLength) {
            // 说明是小数，首位设置为'!'
            result.replace(0, 1, "!");
        }
        return result.toString();
    }

    private String mix(String up, String little) {
        int littleLastZeroIndex = 0;
        for (int i=little.length() - 1; i>=0; i--) {
            if (little.charAt(i) != '0') {
                littleLastZeroIndex = i + 1;
                break;
            }
        }
        if (littleLastZeroIndex <= 0) {
            return up;
        } else {
            return up + "." + (littleLastZeroIndex >= little.length()?little : little.substring(0, littleLastZeroIndex));
        }
    }

    public static void main(String[] args) {
        DecimalAdd a = new DecimalAdd();
        System.out.println(a.add("99999999.01001", "88888888.99999"));
    }
}

class NumHolder {
    String aUp;
    String aLittle;
    String bUp;
    String bLittle;

    public NumHolder(String a, String b) {
        String[] tmp = a.split("\\.");
        aUp = tmp[0];
        if (tmp.length > 1) {
            aLittle = tmp[1];
        } else {
            aLittle = "0";
        }
        tmp = b.split("\\.");
        bUp = tmp[0];
        if (tmp.length > 1) {
            bLittle = tmp[1];
        } else {
            bLittle = "0";
        }
        int needAddLength = aLittle.length() - bLittle.length();
        if (needAddLength != 0) {
            if (needAddLength < 0) {
                // aLittle短
                for (int i=0; i<0-needAddLength; i++ ) {
                    aLittle += "0";
                }
            } else {
                for (int i=0; i<needAddLength; i++ ) {
                    bLittle += "0";
                }
            }
        }
     }
}