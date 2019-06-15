package br.ufms.ed.projeto;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        Memoria memoria = new Memoria();

        int comando = 0;
        do {
           /* System.out.println("Entre com um comando: ");
            System.out.println("1 - Solicitar Memoria");
            System.out.println("2 - Devolução de Memoria");
            System.out.println("3 - Impressão dos Blocos Livres");
            System.out.println("4 - Impressão dos Blocos de Memória Ocupados");
            System.out.println("0 - Sair");*/
            try {
                comando = entrada.nextInt();

                if(comando==1) {
                    int tamanho;
                    tamanho = entrada.nextInt();
                    try {
                        memoria.reservarMemoria(tamanho);
                    } catch (IllegalArgumentException error){
                        System.out.println(error.getMessage());
                    }

                } else if(comando==2) {
                    int tamanho;
                    String endHex;
                    int endereco;
                    tamanho = entrada.nextInt();
                    endHex = entrada.next();
                    if(endHex.endsWith("h"))
                        endHex = endHex.substring(0, endHex.length()-1);
                    endereco=Integer.parseInt(endHex,16);
                    try {
                        memoria.devolveMemoria(tamanho, endereco);
                    } catch (IllegalArgumentException error){
                        System.out.println(error.getMessage());
                    }

                } else if(comando==3){
                    System.out.println("Memória Livre");
                    System.out.println(memoria.toString());
                } else if(comando==4){
                    System.out.println("Memória Ocupada");
                    System.out.println(memoria.imprimirMemoriaOcupada());
                }
            } catch (InputMismatchException erro) {
                System.out.println("Entrada Invalida!");
                break;
            }
        } while(comando != 0);
    }
}
