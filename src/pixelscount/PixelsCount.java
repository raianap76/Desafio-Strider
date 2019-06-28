package pixelscount;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Raiana
 */
public class PixelsCount {

    /**
     * Metodo que calcula a quantidade de pontos vermelhos em uma imagem
     *
     * @return cnt
     * @throws IOException
     */
    public static int countPixels() throws IOException {

        File arq = null;
        BufferedImage img = null;

        //
        //Lê a imagem
        try {
            arq = new File("./challenge_strider.png");
            img = ImageIO.read(arq);
        } catch (IOException e) { //Caso imagem não possa ser lida, dispara exceção
            System.out.println(e);
        }

        //Identifica dimensões da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        int cnt = 0; //inicializa contador dos pontos vermelhos

        //Itera a imagem pixel a pixel
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                Color c = new Color(img.getRGB(x, y)); //pega os valores RGB de cada pixel na imagem
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                //Verifica se o pixel é vermelho
                if (r == 255 && g == 0 && b == 0) {
                    cnt++;
                }
            }
        }
        return cnt;
    }//end countPixels

    /**
     * Metodo que decifra a mensagem contida nos pixels
     *
     * @throws IOException
     */
    public static void decipherMessage() throws IOException {
        File arq = null;
        BufferedImage img = null;

        FileWriter writer = new FileWriter("./pixelmap.txt"); //arquivo onde vou salvar os bits 0 ou 1
        PrintWriter gravarArq = new PrintWriter(writer);

        //Lê a imagem
        try {
            arq = new File("./challenge_strider.png");
            img = ImageIO.read(arq);
        } catch (IOException e) { //Caso imagem não possa ser lida, dispara exceção
            System.out.println(e);
        }
        //Identifica dimensões da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        int flag = 0; // flag para sinalizar se encontrei ponto vermelho na coluna - 0 se nao, 1 se sim
        int aux = 1;

        //Itera a imagem pixel a pixel
        // Grava 0 ou 1 no arquivo txt, dependendo se há um pixel vermelho na coluna
        for (int y = 0; y < largura; y++) {
            flag = 0;
            for (int x = 0; x < altura; x++) {
                Color c = new Color(img.getRGB(y, x)); //pega os valores RGB de cada pixel na imagem
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                //Verifica se o pixel é vermelho
                if (r == 255 && g == 0 && b == 0) {
                    gravarArq.printf("1");
                    flag = 1;
                    break;
                }
            }
            if (flag != 1) {
                gravarArq.printf("0");
            }
            if (aux % 8 == 0) { // separa string de 8 em 8
                gravarArq.printf("\n");
            }
            aux++;
        }

        writer.close();

        File file = new File("./pixelmap.txt");
        Scanner sc = new Scanner(file);
        String data, str;
        int charCode;

        while (sc.hasNextLine()) { //lê arquivo txt
            data = sc.nextLine();
            charCode = Integer.parseInt(data, 2); //Converter String com representação binária (Base 2) para Inteiro
            str = new Character((char) charCode).toString();  //transforma integer em caractere

            System.out.print(str);
        }
        System.out.print("\n");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        //imprime resultado
        System.out.println("A imagem possui " + countPixels() + " pontos vermelhos!");
        decipherMessage();

    }//end main

}//end class PixelsCou
