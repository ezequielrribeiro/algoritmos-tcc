/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import static javax.json.stream.JsonParser.Event.KEY_NAME;

/**
 * Classe auxiliar para obter informações sobre elementos em um arquivo JSON
 *
 * @author ezequielrr
 */
public class InfoJSON {
    
    public static final int T_CAMPO = 0;
    public static final int T_OBJETO = 1;
    public static final int T_ARRAY = 2;
    public static final int T_ARRAY_OBJETO = 3;
    public static final int T_NADA = 4;

    private File f;
    private FileInputStream fi;
    private JsonParser parser;
    private String nomeArquivo;

    public InfoJSON(String nomeArquivo) throws FileNotFoundException {
        init(nomeArquivo);
    }

    private void finaliza() throws IOException {
        if (fi != null) {
            fi.close();
            parser.close();
            fi = null;
            parser = null;
        }
    }

    private void init(String nomeArquivo) throws FileNotFoundException {
        this.nomeArquivo = nomeArquivo;
        f = new File(nomeArquivo);
        fi = new FileInputStream(f);
        parser = Json.createParser(fi);
    }

    public void setArquivo(String nomeArquivo) throws
            FileNotFoundException, IOException {
        finaliza();
        init(nomeArquivo);
    }

    public void getJSONPathElemento(String elemento) {

        while (parser.hasNext()) {
            Event event = parser.next();

            switch (event) {
                case KEY_NAME: {
                    System.out.print(parser.getString() + "=");
                    break;
                }
                case VALUE_STRING: {
                    System.out.println(parser.getString());
                    break;
                }
                case VALUE_NUMBER: {
                    System.out.println(parser.getString());
                    break;
                }
            }
            //return null;
        }
    }
    
    public int getTipoElemento(String nomeElemento) throws IOException {
        if (isCampo(nomeElemento)) {
            return T_CAMPO;
        } else if (isObjeto(nomeElemento)) {
            return T_OBJETO;
        } else if (isArray(nomeElemento)) {
            return T_ARRAY;
        } else if (isArrayObject(nomeElemento)) {
            return T_ARRAY_OBJETO;
        }
        return T_NADA;
    }

    public boolean isObjeto(String elemento) throws
            FileNotFoundException, IOException {
        finaliza();
        init(nomeArquivo);
        while (parser.hasNext()) {
            Event event = parser.next();
            if (event == KEY_NAME) {
                if (parser.getString().equals(elemento)) {
                    if (parser.hasNext()) {
                        Event proxEv = parser.next();
                        if (proxEv == Event.START_OBJECT) {
                            finaliza();
                            return true;
                        }
                    }
                }
            }
        }
        finaliza();
        return false;
    }

    public boolean isArray(String elemento) throws
            FileNotFoundException, IOException {
        finaliza();
        init(nomeArquivo);
        while (parser.hasNext()) {
            Event event = parser.next();
            if (event == Event.KEY_NAME) {
                if (parser.getString().equals(elemento)) {
                    if (parser.hasNext()) {
                        Event proxEv = parser.next();
                        if (proxEv == Event.START_ARRAY) {
                            if (parser.hasNext()) {
                                proxEv = parser.next();
                                if (proxEv != Event.START_OBJECT) {
                                    finaliza();
                                    return true;
                                } else {
                                    finaliza();
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        finaliza();
        return false;
    }
    
    public boolean isArrayObject(String elemento) throws
            FileNotFoundException, IOException {
        finaliza();
        init(nomeArquivo);
        while (parser.hasNext()) {
            Event event = parser.next();
            if (event == Event.KEY_NAME) {
                if (parser.getString().equals(elemento)) {
                    if (parser.hasNext()) {
                        Event proxEv = parser.next();
                        if (proxEv == Event.START_ARRAY) {
                            if (parser.hasNext()) {
                                proxEv = parser.next();
                                if (proxEv == Event.START_OBJECT) {
                                    finaliza();
                                    return true;
                                } else {
                                    finaliza();
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        finaliza();
        return false;
    }
    

    public boolean isCampo(String elemento) throws
            FileNotFoundException, IOException {
        finaliza();
        init(nomeArquivo);
        while (parser.hasNext()) {
            Event event = parser.next();
            if (event == KEY_NAME) {
                if (parser.getString().equals(elemento)) {
                    if (parser.hasNext()) {
                        Event proxEv = parser.next();
                        if (proxEv == Event.VALUE_STRING
                                || proxEv == Event.VALUE_NUMBER
                                || proxEv == Event.VALUE_FALSE
                                || proxEv == Event.VALUE_NULL
                                || proxEv == Event.VALUE_TRUE) {
                            finaliza();
                            return true;
                        }
                    }
                }
            }
        }
        finaliza();
        return false;
    }

    public boolean localizarElemento(String elemento)
            throws FileNotFoundException, IOException {
        finaliza();
        init(nomeArquivo);
        while (parser.hasNext()) {
            Event event = parser.next();
            if (event == KEY_NAME) {
                if (parser.getString().equals(elemento)) {
                    finaliza();
                    return true;
                }
            }
        }
        finaliza();
        return false;
    }

}
