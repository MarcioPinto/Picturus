package org.academiadecodigo.haltistas.server;

public class MainTestDecoder {

    public static void main(String[] args) {

    Decoder decoder = new Decoder("/DRAW/ /NAME/ thisisthemessage");
    decoder.decoder();
    }
}
