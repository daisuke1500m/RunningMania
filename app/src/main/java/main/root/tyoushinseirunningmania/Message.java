package main.root.tyoushinseirunningmania;

/**
 * Created by daisuke on 2018/02/02.
 */

public class Message {
    private static final String crlf = System.getProperty("line.separator");
    private int language = -1;
    private static final String[][] message= {
            {"1.このアプリは距離[km]とタイム[h/m/s]を入力することで1"+
             "kmの平均ペース[m/km]を計算できます。",
             "this app can calculate average pace per kilometer by"+
             "inputting the distance and the time which you ran."},
            {"2.「ランニングマニア」の機能2"+
                    "また、そのペースでメジャーな距離を走り続けると何分で走れるのかも同時に計算でき"+
                    "ます。さらに、結果画面下側に距離を入力すると、その距離を何分で走れるのかも計算できます。",
            },
            {""}
    };

    public void setLanguage(int language) {
        if(language == -1)
            this.language = language;
    }

    public int getLanguage() {
        return language;
    }

    public String get(Identifier identifier){
        return message[identifier.get()][language];
    }




}
