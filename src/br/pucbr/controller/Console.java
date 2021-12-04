package br.pucbr.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * <b>Classe Console</b>
 * <br><br>
 * <u>Hist�rico</u>: <br>&nbsp;&nbsp;
 *     16/02/2007 cria��o da classe
 *
 */
public class Console
{

    /**
     * Objeto que representa o teclado
     */
    private static BufferedReader teclado =
            new BufferedReader (
                    new InputStreamReader(System.in));

    /**
     * L� uma string do teclado
     *
     * @param texto texto que ser� exibido para o usu�rio
     *
     * @return string digitada
     */
    public static String lerString(String texto)
    {
        try
        {
            // Mostra o texto
            System.out.println(texto);

            // L� a linha
            return teclado.readLine();
        }
        catch(IOException e)
        {
            return null;
        }
    }

    /**
     * L� um inteiro do teclado
     *
     * @param texto texto que ser� exibido para o usu�rio
     *
     * @return inteiro digitado
     */
    public static int lerInt(String texto)
    {
        // Chama o m�todo lerString e converte o resultado para inteiro
        return Integer.parseInt(lerString(texto));
    }

    /**
     * L� um double do teclado
     *
     * @param texto texto que ser� exibido para o usu�rio
     *
     * @return double digitado
     */
    public static double lerDouble(String texto)
    {
        // Chama o m�todo lerString e converte o resultado para double
        return Double.parseDouble(lerString(texto));
    }

}
