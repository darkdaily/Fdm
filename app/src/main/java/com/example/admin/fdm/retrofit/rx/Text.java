package com.example.admin.fdm.retrofit.rx;

/**
 * Created by fushuang on 2017/11/29.
 */

public class Text {

    public void haha(){

    }

    public static class N{

        private final Text text;

        N(Text text) {
            this.text = text;
        }
        public void xixi(){
            text.haha();
        }
    }

}
