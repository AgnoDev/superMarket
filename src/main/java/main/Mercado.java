package main;

import jdk.jshell.execution.Util;
import modelo.Produto;
import utils.Utils;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.awt.SystemColor.menu;

public class Mercado {
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<Produto> produtos;
    private static Map<Produto, Integer> carrinho;

    public static void main(String[] args) {
        produtos = new ArrayList<>();
        carrinho = new HashMap<>(); // classe que implementa o Map
        menu();
    }

    private static void menu() {

        System.out.println("----------------------------------------------------------------");
        System.out.println("--------------------Welcome SuperMarket--------------------------");
        System.out.println("----------------------------------------------------------------");
        System.out.println("----------------Selecione a operação desejada-------------------");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Cadastrar produto:      tecle 1---------------------------------");
        System.out.println("Listar produtos:        tecle 2---------------------------------");
        System.out.println("Comprar produtos:       tecle 3---------------------------------");
        System.out.println("Carrinho de compras:    tecle 4---------------------------------");
        System.out.println("Se deseja sair:         tecle 5---------------------------------");

        int option = input.nextInt();

        switch (option) {
            case 1:
                cadastrarProdutos();
                break;
            case 2:
                listarProdutos();
                break;
            case 3:
                comprarProdutos();
                break;
            case 4:
                verCarrinho();
                break;
            case 5:
                System.out.println("Obrigado pela preferência! Volte sempre!");
                System.exit(0);
                break;
            default:
                System.out.println("Opção Inválida!");
                menu();
                break;
        }
    }

    private static void cadastrarProdutos() {
        System.out.println("Nome do Produto");
        String nome = input.next(); // recebe uma string

        System.out.println("Preco do Produto");
        Double preco = input.nextDouble(); // recebe um double

        Produto produto = new Produto(nome, preco);
        produtos.add(produto);

        System.out.println(produto.getNome() + " cadastrado com sucesso!");
        menu();
    }

    private static void listarProdutos() {
        if (produtos.size() > 0) {
            System.out.println("Lista de Produtos: \n");

            for (Produto p : produtos) {
                System.out.println(p);
            }
        } else {
            System.out.println("Nenhum produto cadastrado!");
        }
        menu();
    }

    private static void comprarProdutos() {
        if (produtos.size() > 0) {
            System.out.println("###  Produtos disponíveis  ###");
            for (Produto p : produtos) {
                System.out.println(p + "\n");
            }
            System.out.println("Informe o código do produto: \n");

            int id = Integer.parseInt(input.next());    // converte string passada pelo usuario para int
            boolean isPresent = false;

            for (Produto p : produtos) {
                if (p.getId() == id) {
                    int qtd = 0;
                    try {                   // checa se o produto já está no carrinho, se sim, incrementa qtd
                        qtd = carrinho.get(p);
                        carrinho.put(p, qtd + 1);
                    } catch (NullPointerException e) {
                        carrinho.put(p, 1); //primeira unidade do produto no carrinho
                    }

                    System.out.println(p.getNome() + " adicionado ao carrinho!");
                    isPresent = true;

                    if (isPresent) {
                        System.out.println("Deseja continuar comprando?");
                        System.out.println("Digite 1 para continuar ou 0 para finalizar compra");
                        int option = Integer.parseInt(input.next());

                        if (option == 1) {
                            comprarProdutos();
                        } else {
                            finalizarCompra();
                        }
                    }
                } else {
                    System.out.println("Produto não encontrado!");
                    menu();
                }
            }
        } else {
            System.out.println("Nenhum produto cadastrado!");
            menu();
        }
    }

    private static void verCarrinho() {
        System.out.println("Produtos neste Carrinho:");
        if (carrinho.size() > 0) {
            for (Produto p : carrinho.keySet()) {
                System.out.println("Produto: " + p + "\nQuantidade: " + carrinho.get(p));
            }
        } else {
            System.out.println("O carrinho está vazio!");
        }
        menu();
    }

    private static void finalizarCompra() {
        Double valorDaCompra = 0.0;
        System.out.println("Seus produtos:");

        for (Produto p : carrinho.keySet()) {
            int qtd = carrinho.get(p);
            valorDaCompra += p.getPreco() * qtd;
            System.out.println(p);
            System.out.println("Quabtidade: " + qtd);
            System.out.println("-----------------------------");
        }
        System.out.println("O valor total da sua compra é: " + Utils.doubleToString(valorDaCompra));
        carrinho.clear();
        System.out.println("Obrigado pela preferencia");
        menu();
    }
}