import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import java.util.Scanner;
import java.lang.*;


public aspect TestNumberSecret{
    pointcut callDeposit(int ammount) : call(* Account.deposit(int)) && args(ammount);
    pointcut callWithDraw(int ammount) : call(* Account.withdraw(int)) && args(ammount);
    

    before(int ammount) : callDeposit(ammount) {

        Account account = (Account) thisJoinPoint.getTarget();

        Scanner s = new Scanner(System.in);

        boolean incorrecto = false;
        int i = 0;
        
        do{
            i++;
            System.out.print("\nSe esta intentando hacer un deposito de: " + ammount
                        + "\nPara poder realizar la operacion debe introducir el numero secreto de la tarjeta de credito: ");
            
            int secret = s.nextInt();
            if(secret != account.getSecretNumber())
            {
                System.out.println("Numero secreto incorrecto...(" + i + "/3)");
            }else{
                incorrecto = true;
            }
        }while(!incorrecto && i < 3);

        s.nextLine();//limpiamos el buffer de


        if(!incorrecto)
            throw new RuntimeException("Invalid deposit amount!");
        else{
            System.out.println("Numero secreto correcto..."
                            + "\n¿Desea comprobante?(S/n): ");
            char op = s.next().charAt(0);

            if(op == 's' || op == 'S')
                System.out.println("Se ha realizado el deposito de " + ammount + " euros a la cuenta de " + account.getOwner());

        }
    }

    before(int ammount) : callWithDraw(ammount) {

        Account account = (Account) thisJoinPoint.getTarget();

        Scanner s = new Scanner(System.in);

        boolean incorrecto = false;
        int i = 0;
        
        do{
            i++;
            System.out.print("\nSe esta intentando hacer un reintegro de: " + ammount
                        + "\nPara poder realizar la operacion debe introducir el numero secreto de la tarjeta de credito: ");
            
            int secret = s.nextInt();
            if(secret != account.getSecretNumber())
            {
                System.out.println("Numero secreto incorrecto...(" + i + "/3)");
            }else{
                incorrecto = true;
            }
        }while(!incorrecto && i < 3);

        s.nextLine();//limpiamos el buffer de


        if(!incorrecto)
            throw new RuntimeException("Invalid deposit amount!");
        else{
            System.out.println("Numero secreto correcto..."
                            + "\n¿Desea comprobante?(S/n): ");
            char op = s.next().charAt(0);
            if(op == 's' || op == 'S')
                System.out.println("Se ha realizado el reintegro de " + ammount + " euros a la cuenta de " + account.getOwner());

        }
    }
}