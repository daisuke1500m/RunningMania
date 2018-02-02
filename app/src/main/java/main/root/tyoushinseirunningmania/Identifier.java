package main.root.tyoushinseirunningmania;

/**
 * Created by daisuke on 2018/02/02.
 */

public enum Identifier {
    DESCRIPTION1(0);
    private int index;

    private Identifier(int index){
        this.index = index;
    }

    public int get(){
        return index;
    }
}
