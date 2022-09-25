import java.util.Scanner;

public class L4E1 {
    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
    
        Scanner ler;

        ler = new Scanner(System.in);

        String mensagemBoasVindas = "Seja bem vindo ao programa do caixa eletrônico\n\n";
        String CPF = "123.456.789-00";
        String senha = "01020304";
        double saldo = 1000;
        double deposito = 0;
        double saque = 0;
        String menu = "1 — Saldo\n2 — Depósito\n3 — Saque\n4 — Transferencia\n0 — Sair";
        int opcao = 5;

        // Desafio (não obrigatório)
        double transferencia = 0;
        String contaBanco = "12345678-9";
        int agenciaBanco = 0001;
        int numeroBanco = 0260;

        // Extra
        boolean loggedIn = false;
        boolean on = true;
        boolean dentroMenu = false;

        String stringCaixaEletronico = "Caixa Eletrônico";
        String stringSaldo = "Saldo";
        String stringDeposito = "Depósito";
        String stringSaque = "Saque";
        String stringTransferencia = "Transferência Bancária";
        String stringMenu = "Menu";
        String menuCaixaEletronico = stringCaixaEletronico + " > " + stringMenu + "\n\n" + menu + "\n\n";
        String menuSaldo = stringCaixaEletronico + " > " + stringMenu + " > " + stringSaldo + "\n\n";
        String menuDeposito = stringCaixaEletronico + " > " + stringMenu + " > " + stringDeposito + "\n\n";
        String menuSaque = stringCaixaEletronico + " > " + stringMenu + " > " + stringSaque + "\n\n";
        String menuTransferencia = stringCaixaEletronico + " > " + stringMenu + " > " + stringTransferencia + "\n\n";
        String comandoCPF = "Digite seu CPF\n";
        String comandoSenha = "\nDigite sua senha\n";
        String comandoDeposito = "\n\nDigite o valor a depositar\n";
        String comandoSaque = "\n\nDigite o valor a sacar\n";
        String comandoTranferencia = "\n\nDigite o valor a transferir\n";
        String comandoNumeroConta = "\n\nDigite o número da conta aonde o dinheiro será transferido\n";
        String comandoNumeroAgencia = "\n\nDigite o número da agência do banco\n";
        String comandoNumeroBanco = "\n\nDigite o número do banco\n";
        String limparTela = "\033[H\033[2J";
        String linhaEscolha = "Escolha uma opção acima\n";

        System.out.print(mensagemBoasVindas);

        while (on == true) {
            System.out.print(comandoCPF);
            CPF = ler.nextLine();
            System.out.print(comandoSenha);
            senha = ler.nextLine();
            System.out.println();
            loggedIn = true;
            while (loggedIn == true) {
                System.out.print(limparTela);
                System.out.print(menuCaixaEletronico);
                System.out.print(linhaEscolha);
                opcao = ler.nextInt();
                if (opcao != 5) {
                    dentroMenu = true;
                }
                while (dentroMenu == true) {
                    if (opcao == 1) {
                        // Limpar Console
                        System.out.print(limparTela);
                        System.out.print(menuSaldo + "R$ " + saldo + "\n\n");
                        opcao = 5;
                        dentroMenu = false;
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    } else if (opcao == 2) {
                        // Limpar Console
                        System.out.print(limparTela);
                        System.out.print(menuDeposito);
                        System.out.print("R$ " + saldo + "\n\n");
                        System.out.print(comandoDeposito);
                        deposito = ler.nextDouble();
                        saldo = saldo + deposito;
                        deposito = 0;
                        opcao = 5;
                        dentroMenu = false;
                    } else if (opcao == 3) {
                        // Limpar Console
                        System.out.print(limparTela);
                        System.out.print(menuSaque);
                        System.out.print("R$ " + saldo + "\n\n");
                        System.out.print(comandoSaque);
                        saque = ler.nextDouble();
                        saldo = saldo - saque;
                        deposito = 0;
                        opcao = 5;
                        dentroMenu = false;
                    } else if (opcao == 4) { // Imcompleto
                        // Limpar Console
                        System.out.print(limparTela);
                        System.out.print(menuTransferencia);
                        System.out.print("R$ " + saldo + "\n\n");
                        System.out.print(comandoTranferencia);
                        transferencia = ler.nextDouble();
                        System.out.print(comandoNumeroConta);
                        contaBanco = ler.nextLine();
                        System.out.print(comandoNumeroAgencia);
                        agenciaBanco = ler.nextInt();
                        System.out.print(comandoNumeroBanco);
                        numeroBanco = ler.nextInt();
                        saldo = saldo - transferencia;
                        opcao = 5;
                        dentroMenu = false;
                    } else if (opcao == 0) {
                        loggedIn = false;
                        on = false;
                    } else {
                        opcao = 5;
                    }
                }
            }
        }
        ler.close();    
    }   
}